package com.pandey.shubham.katty.core.utils

import android.content.Context
import com.pandey.shubham.katty.KattyApp

/**
 * Created by shubhampandey
 */
fun getAppContext(block: (Context) -> Unit) {
    KattyApp.getAppContext()?.let {
        block(it)
    }
}