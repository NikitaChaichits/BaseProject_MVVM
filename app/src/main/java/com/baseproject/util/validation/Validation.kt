package com.baseproject.util.validation

import android.content.Context
import com.baseproject.domain.error.ApplicationError
import com.baseproject.domain.model.result.Result
import com.baseproject.domain.model.result.toSuccess
import com.baseproject.util.numbers.toWeight
import java.text.ParseException

fun String.startCheck() = this.toSuccess()

fun Float.startCheck() = this.toSuccess()

fun Result<String>.checkEmpty(emptyError: ApplicationError = ApplicationError.EmptyFiled): Result<String> {
    return if (this is Result.Success) {
        if (value.isNotEmpty()) Result.Success(value)
        else Result.Error(emptyError)
    } else this
}

fun Result<String>.checkLoginFormat(): Result<String> {
    return if (this is Result.Success) {
        if (value.contains('@')) {
            if (value.isEmailValid()) Result.Success(value)
            else Result.Error(ApplicationError.InvalidLogin)
        } else Result.Success(value)
    } else this
}

fun Result<String>.checkPasswordComplexity(): Result<String> {
    return if (this is Result.Success) {
        if (value.isPasswordStrong()) Result.Success(value)
        else Result.Error(ApplicationError.WeakPassword)
    } else this
}

fun Result<String>.checkEquals(anotherPassword: String): Result<String> {
    return if (this is Result.Success) {
        if (value == anotherPassword) Result.Success(value)
        else Result.Error(ApplicationError.PasswordsDoesNotMatch)
    } else this
}

private fun String.matchTo(regExp: String) = matches(regExp.toRegex())

private fun String.isEmailValid() = this.matchTo(EMAIL)

private fun String.isPasswordStrong() = this.matchTo(PASSWORD)

fun Result<String>.checkWeightValue(context: Context): Result<Float> {
    return when (this) {
        is Result.Success -> {
            return try {
                val weight = value.toWeight(context)
                if (weight > 0) {
                    Result.Success(weight)
                } else {
                    Result.Error(ApplicationError.InvalidWeightValue)
                }
            } catch (nfe: ParseException) {
                Result.Error(ApplicationError.InvalidWeightValue)
            }
        }
        is Result.Error   -> {
            Result.Error(this.error)
        }
    }
}

fun Result<Float>.checkWeightMax(maxValue: Float = MAX_WEIGHT): Result<Float> {
    return if (this is Result.Success) {
        if (value <= maxValue) Result.Success(value)
        else Result.Error(ApplicationError.WeightOverflow)
    } else this
}