package com.pandey.shubham.katty.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.pandey.shubham.katty.base.BaseCustomView
import com.pandey.shubham.katty.databinding.LayoutNetworkViewBinding
import com.pandey.shubham.katty.model.ErrorMessage

/**
 * Created by shubhampandey
 */
class NetworkErrorView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defyStyle: Int = 0
) : BaseCustomView<LayoutNetworkViewBinding>(context, attributeSet, defyStyle) {

    override fun viewBinding() = LayoutNetworkViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setData(error: ErrorMessage) {
        binding.tvErrorMessage.text = error.errorMessage
        showErrorByCode(error.errorCode)
    }

    private fun showErrorByCode(errorCode: Int) {
        when(errorCode) {
            500 -> showServerError()
            else -> {
                // do nothing
            }
        }
    }

    private fun showServerError() {

    }
}