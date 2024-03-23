package com.pandey.shubham.katty.features.feed.ui.viewholders

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.pandey.shubham.katty.databinding.ItemNetworkStateBinding

class NetworkStateItemViewHolder(
    private val binding: ItemNetworkStateBinding,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.btnRetry.setOnClickListener { retryCallback() }
    }

    fun bind(loadState: LoadState) {
        with(binding) {
            nwLoader.isVisible = loadState is LoadState.Loading
            btnRetry.isVisible = loadState is LoadState.Error
            tvError.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
            tvError.text = (loadState as? LoadState.Error)?.error?.message
        }
    }
}