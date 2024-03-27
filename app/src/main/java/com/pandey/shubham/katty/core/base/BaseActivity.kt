package com.pandey.shubham.katty.core.base

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.pandey.shubham.katty.R
import com.pandey.shubham.katty.core.utils.shouldInterceptBackPress
import com.pandey.shubham.katty.core.utils.updateFragment


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
        window.statusBarColor = ContextCompat.getColor(this, R.color.color_tertiary)
        super.onCreate(savedInstanceState)
        _binding = viewBinding()
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(onBackCallback)
        attachBackStackListener()
    }

    private fun attachBackStackListener() {
        supportFragmentManager.run {
            addOnBackStackChangedListener {
                onBackStackUpdate()
            }
        }
    }

    fun openFragment(
        @IdRes containerId: Int,
        fragment: Fragment,
        addToBackStack: Boolean = false,
        allowStateLoss: Boolean = true
    ) {
        supportFragmentManager.updateFragment(fragment, allowStateLoss) {
            add(containerId, fragment, fragment::class.java.canonicalName)
            if (addToBackStack) {
                addToBackStack(fragment::class.java.canonicalName)
            }
        }
    }

    protected abstract fun viewBinding(): VB

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected abstract fun onBackStackUpdate()
}