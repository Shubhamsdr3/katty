package com.pandey.shubham.katty.model

import androidx.annotation.Keep

/**
 * Created by shubhampandey
 */

@Keep
data class ErrorMessage(
    val errorCode: Int,
    val errorMessage: String?
)