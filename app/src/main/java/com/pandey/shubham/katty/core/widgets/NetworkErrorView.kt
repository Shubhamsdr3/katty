package com.pandey.shubham.katty.core.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.pandey.shubham.katty.core.base.BaseCustomView
import com.pandey.shubham.katty.databinding.LayoutNetworkViewBinding
import com.pandey.shubham.katty.core.failure.model.ErrorMessage

/**
 * Created by shubhampandey
 */
class NetworkErrorView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defyStyle: Int = 0
) : BaseCustomView<LayoutNetworkViewBinding>(context, attributeSet, defyStyle) {

    override fun viewBinding() = LayoutNetworkViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setData(error: ErrorMessage, retryCallback: () -> Unit) {
        binding.tvErrorMessage.text = error.errorMessage
        binding.btnRetry.setOnClickListener { retryCallback() }
    }
}