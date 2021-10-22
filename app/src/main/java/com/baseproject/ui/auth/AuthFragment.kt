package com.baseproject.ui.auth

import com.baseproject.App
import com.baseproject.common.base.BaseFragment
import com.baseproject.R
import com.baseproject.ui.auth.AuthViewModel
import com.fahrer.util.dagger.viewModel

class AuthFragment : BaseFragment(R.layout.fragment_auth) {

    override val viewModel by viewModel { App.component.loginViewModel }

    override fun setupInsets() {
        TODO("Not yet implemented")
    }


//    private fun isUserLoggedIn() {
//        if (viewModel.isUserLoggedIn()) navigate(R.id.mainFragment)
//    }
}
