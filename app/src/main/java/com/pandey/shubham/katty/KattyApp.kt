package com.pandey.shubham.katty

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import java.lang.ref.WeakReference

/**
 * Created by shubhampandey
 */

@HiltAndroidApp
class KattyApp: Application() {

    companion object {
        private lateinit var contextWeakReference: WeakReference<Context>

        @JvmStatic
        fun init(context: Context) {
            this.contextWeakReference = WeakReference(context)
        }

        @JvmStatic
        fun getAppContext() = contextWeakReference.get()
    }

    override fun onCreate() {
        super.onCreate()
        init(this)
    }
}