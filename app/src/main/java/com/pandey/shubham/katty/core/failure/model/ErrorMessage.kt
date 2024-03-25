package com.pandey.shubham.katty.core.failure.model

import androidx.annotation.Keep

/**
 * Created by shubhampandey
 */

@Keep
data class ErrorMessage(
    val errorCode: Int? = null,
    val errorMessage: String?
)