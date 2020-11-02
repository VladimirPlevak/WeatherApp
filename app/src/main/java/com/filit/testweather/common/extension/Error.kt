package com.filit.testweather.common.extension

import android.content.Context
import android.widget.Toast
import com.filit.domain.ApiException
import com.filit.domain.NetworkException
import com.filit.testweather.R

fun Throwable.getContextMessage(context: Context) =
    when (this) {
        is ApiException -> errorMessage ?: context.getString(R.string.common_error_server)
        is NetworkException -> context.getString(R.string.common_error_network)
        else -> context.getString(R.string.common_error_unknown)
    }

fun Context.showToastError(error: Throwable): Toast = showToastMessage(error.getContextMessage(this))