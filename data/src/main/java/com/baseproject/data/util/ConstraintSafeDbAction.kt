package com.baseproject.data.util

import android.database.sqlite.SQLiteConstraintException
import com.baseproject.domain.error.ApplicationError
import com.baseproject.domain.model.result.Result

inline fun <T> constraintSafeDbAction(dbAction: () -> T): Result<T> {
    return try {
        Result.Success(dbAction())
    } catch (throwable: Throwable) {
        when (throwable) {
            is SQLiteConstraintException -> Result.Error(ApplicationError.Generic(throwable))
            else                         -> {
                throw throwable
            }
        }
    }
}