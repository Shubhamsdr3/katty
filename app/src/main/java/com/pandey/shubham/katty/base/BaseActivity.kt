package com.pandey.shubham.katty.base

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.viewbinding.ViewBinding
import com.pandey.shubham.katty.utils.shouldInterceptBackPress

/**
 * Created by shubhampandey
 */
abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity() {

    private var _binding: VB? = null

    protected val binding: VB get() = _binding!!

    private val onBackCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (shouldInterceptBackPress()) {
                supportFragmentManager.popBackStackImmediate()
            } else {
                isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = viewBinding()
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(onBackCallback)
    }

    fun openFragment(
        @IdRes containerId: Int,
        fragment: Fragment,
        addToBackStack: Boolean = false,
        allowStateLoss: Boolean = false
    ) {
        supportFragmentManager.commit(allowStateLoss) {
            replace(containerId, fragment, fragment::class.java.simpleName)
            if (addToBackStack) {
                addToBackStack(fragment::class.java.simpleName)
            }
        }
    }

    protected abstract fun viewBinding(): VB

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}