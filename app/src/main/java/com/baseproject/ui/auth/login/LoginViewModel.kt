package com.baseproject.ui.auth.login

import androidx.lifecycle.MutableLiveData
import com.baseproject.common.base.BaseViewModel
import com.baseproject.domain.error.ApplicationError
import com.baseproject.domain.model.result.onError
import com.baseproject.util.livedata.Event
import com.baseproject.util.livedata.setEventValue
import com.baseproject.util.validation.checkEmpty
import com.baseproject.util.validation.checkLoginFormat
import com.baseproject.util.validation.checkPasswordComplexity
import com.baseproject.util.validation.startCheck
import javax.inject.Inject

class LoginViewModel @Inject constructor(
//    private val interactor: AuthInteractor
) : BaseViewModel() {

    val success = MutableLiveData<Event<Unit>>()
    val loginError = MutableLiveData<Event<ApplicationError>>()
    val passwordError = MutableLiveData<Event<ApplicationError>>()

//    fun login(companyCode: String, login: String, password: String) {
//
//        if (!isFieldsValid(login, password)) return
//
//        viewModelScope.launchWithLoading {
//
//            interactor.login(login, password)
//                .onSuccess { success.postEventValue(Unit) }
//                .onError { error.postEventValue(it) }
//        }
//    }

    private fun isFieldsValid(login: String, password: String): Boolean {

        var isValid = true

        login.startCheck().checkEmpty().checkLoginFormat().onError {
            loginError.setEventValue(it)
            isValid = false
        }
        password.startCheck().checkEmpty().checkPasswordComplexity().onError {
            passwordError.setEventValue(it)
            isValid = false
        }

        return isValid
    }
}