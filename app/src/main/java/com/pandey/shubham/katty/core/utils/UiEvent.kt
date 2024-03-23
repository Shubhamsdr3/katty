package com.pandey.shubham.katty.core.utils

/**
 * Created by shubhampandey
 */
open class UiEvent<out T>(private val content: T) {

    private var hasBeenHandled = false
        private set

    fun getContent(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}