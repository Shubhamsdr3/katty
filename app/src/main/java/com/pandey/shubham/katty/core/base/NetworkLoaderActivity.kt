package com.pandey.shubham.katty.core.base

import com.pandey.shubham.katty.core.failure.model.ErrorMessage
import com.pandey.shubham.katty.core.utils.gone
import com.pandey.shubham.katty.core.utils.visible
import com.pandey.shubham.katty.databinding.ActivityMainBinding

/**
 * Created by shubhampandey
 */
abstract class NetworkLoaderActivity: BaseActivity<ActivityMainBinding>() {
    override fun viewBinding() = ActivityMainBinding.inflate(layoutInflater)

    internal fun showLoader() {
        binding.nwLoader.visible()
    }

    fun hideLoader() {
        binding.nwLoader.gone()
    }

    fun showError(error: ErrorMessage, retryCallback: () -> Unit) {
        binding.networkError.visible()
        binding.networkError.setData(error, retryCallback)
    }

    fun hideError() {
        binding.networkError.gone()
    }
}