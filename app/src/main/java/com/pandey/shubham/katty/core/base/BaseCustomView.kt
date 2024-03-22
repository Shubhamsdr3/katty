package com.pandey.shubham.katty.core.base

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding

/**
 * Created by shubhampandey
 */
abstract class BaseCustomView<T : ViewBinding> @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defyStyle: Int = 0
) : FrameLayout(context, attributeSet, defyStyle) {

    private val _binding: T by lazy { viewBinding() }

    protected val binding: T get() = _binding

    abstract fun viewBinding(): T
}