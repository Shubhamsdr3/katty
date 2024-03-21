package com.pandey.shubham.katty.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.viewbinding.ViewBinding

/**
 * Created by shubhampandey
 */
abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity() {

    private var _binding: VB? = null

    protected val binding: VB get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = viewBinding()
        setContentView(binding.root)
    }

    fun openFragment(@IdRes containerId: Int, fragment: Fragment, allowStateLoss: Boolean = false) {
        supportFragmentManager.commit(allowStateLoss) {
            replace(containerId, fragment, fragment::class.java.simpleName)
        }
    }

    protected abstract fun viewBinding(): VB

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}