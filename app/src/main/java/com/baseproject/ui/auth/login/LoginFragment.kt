package com.baseproject.ui.auth.login

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.baseproject.App
import com.baseproject.R
import com.baseproject.common.base.BaseFragment
import com.baseproject.databinding.FragmentLoginBinding


class LoginFragment : BaseFragment (R.layout.fragment_login), View.OnClickListener {

    private val binding by viewBinding(FragmentLoginBinding::bind)

    override val viewModel by viewModel { App.component.loginViewModel }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    override fun setupInsets() {
        TODO("Not yet implemented")
    }


    private fun setupButtons() {
        binding.btnLogin.setOnClickListener(this)
    }

    private fun clearAllErrors() {
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.btnLogin.id                -> {
                navigate(R.id.action_global_mainFragment)

            }
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()

//        viewModel.success.observe {
//            if (checkPermission(ACCESS_FINE_LOCATION)) navigate(actionLoginFragmentToDashboardFragment())
//            else navigate(actionLoginFragmentToLocationPermissionFragment())
//        }
//        viewModel.loginError.observe { binding.tilLogin.error(it.message()) }
//        viewModel.passwordError.observe { binding.tilPassword.error(it.message()) }
//        viewModel.error.observe { snackbar(binding.root, it.message()) }
    }
}