package com.pandey.shubham.katty.core.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.pandey.shubham.katty.core.base.BaseCustomView
import com.pandey.shubham.katty.core.utils.onAfterTextChange
import com.pandey.shubham.katty.databinding.LayoutSearchWidgetBinding

/**
 * Created by shubhampandey
 */
class SearchWidget @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defyStyle: Int = 0
) : BaseCustomView<LayoutSearchWidgetBinding>(context, attributeSet, defyStyle), DefaultLifecycleObserver {
    override fun viewBinding() = LayoutSearchWidgetBinding.inflate(LayoutInflater.from(context), this, true)

    private var onTextChanged: ((CharSequence) -> Unit?)? = null

    private val textWatcher = onAfterTextChange { text ->
        if (!text.isNullOrBlank()) {
            onTextChanged?.let { it(text) }
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        binding.etSearch.addTextChangedListener(textWatcher)
    }

    fun onTextChanged(lifecycle: Lifecycle, onTextChanged: (CharSequence) -> Unit) {
        lifecycle.addObserver(this)
        this.onTextChanged = onTextChanged
    }

    override fun onDestroy(owner: LifecycleOwner) {
        binding.etSearch.removeTextChangedListener(textWatcher)
    }
}