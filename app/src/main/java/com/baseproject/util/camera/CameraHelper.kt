@file:Suppress("CanBeParameter", "unused")

package com.baseproject.util.camera

import android.content.Context
import android.util.DisplayMetrics
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.TorchState
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * CameraX helper.
 *
 * @param handleQrData The block that will be executed after the image is received. Note that the [handleQrData]
 * will be executed on the worker thread.
 * */
class CameraHelper(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val previewView: PreviewView,
    private val handleQrData: (data: String) -> Unit
) : LifecycleObserver {

    private lateinit var camera: Camera
    private lateinit var preview: Preview
    private lateinit var imageAnalyzer: ImageAnalysis

    private val lensFacing: Int = CameraSelector.LENS_FACING_BACK
    private val captureMode: Int = ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
    private val cameraExecutor: ExecutorService by lazy { Executors.newSingleThreadExecutor() }

    private val Camera.isTorchEnabled: Boolean
        get() = cameraInfo.torchState.value == TorchState.ON

    private val Camera.hasTorch: Boolean
        get() = cameraInfo.hasFlashUnit()

    private val _torchState = MutableLiveData<Boolean>()

    /**
     * Does not have initial value from the start. Meaning that the torch is not available for the moment.
     * If there is a value then the camera is initialized and has the torch. Otherwise the liveData is kept uninitialised.
     * TODO: most likely should be refactored to provide a 3-state value, like 'not available' (initial), 'off' and 'on'
     */
    val touchState: LiveData<Boolean>
        get() = _torchState

    init {
        setupCamera()
    }

    /** Initialize CameraX, and prepare to bind the camera use cases  */
    private fun setupCamera() {

        previewView.post {

            lifecycleOwner.lifecycle.addObserver(this)

            val mainExecutor = ContextCompat.getMainExecutor(context)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener(mainExecutor) {
                val cameraProvider = cameraProviderFuture.get()
                bindCameraUseCases(cameraProvider)
                linkUpTorchState()
            }
        }
    }

    /** Declare and bind [preview] and [imageAnalyzer] use cases */
    private fun bindCameraUseCases(cameraProvider: ProcessCameraProvider) {

        val metrics = DisplayMetrics().also { previewView.display.getRealMetrics(it) }
        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)

        val rotation = previewView.display.rotation
        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

        preview = Preview.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()

        imageAnalyzer = ImageAnalysis.Builder()
            .build()
            .also {
                it.setAnalyzer(cameraExecutor, QrAnalyzer { barcode ->
                    handleQrData(barcode.rawValue)
                })
            }

        cameraProvider.unbindAll()

        preview.setSurfaceProvider(previewView.surfaceProvider)
        camera = cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalyzer)
    }

    private fun linkUpTorchState() {
        if (camera.hasTorch) {
            camera.cameraInfo.torchState.observe(lifecycleOwner) {
                _torchState.postValue(camera.isTorchEnabled)
            }
        }
    }

    /** Switch the torch */
    fun switchTorch() {
        camera.cameraControl.enableTorch(!camera.isTorchEnabled)
    }

    @OnLifecycleEvent(ON_DESTROY)
    private fun destroy() {
        cameraExecutor.shutdown()
        lifecycleOwner.lifecycle.removeObserver(this)
    }
}