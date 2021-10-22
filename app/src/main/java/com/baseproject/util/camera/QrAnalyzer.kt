@file:Suppress("UnsafeExperimentalUsageError")

package com.baseproject.util.camera

import android.media.Image
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.baseproject.common.constant.SNACKBAR_DURATION
import com.baseproject.util.time.handleMultipleCallsWithDelay
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class QrAnalyzer(private val listener: BarcodeListener) : ImageAnalysis.Analyzer {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build()

    private val scanner = BarcodeScanning.getClient(options)

    override fun analyze(imageProxy: ImageProxy) {

        val mediaImage: Image? = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (!barcodes.isNullOrEmpty()) {
                        handleMultipleCallsWithDelay(SNACKBAR_DURATION) {
                            listener.onScanned(barcodes.first())
                        }
                    }
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }

    fun interface BarcodeListener {
        fun onScanned(result: Barcode)
    }
}