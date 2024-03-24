package com.pandey.shubham.katty.core.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pandey.shubham.katty.KattyApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Created by shubhampandey
 */
fun getAppContext(block: (Context) -> Unit) {
    KattyApp.getAppContext()?.let {
        block(it)
    }
}

inline fun <reified T> Gson.fromJson(json: String?): T? {
    return fromJson(json, object : TypeToken<T>() {}.type)
}

// coroutines
fun IOScope() = CoroutineScope(Dispatchers.IO + SupervisorJob())
