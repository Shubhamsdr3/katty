package com.pandey.shubham.katty.feed.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.pandey.shubham.katty.databinding.ItemNetworkStateBinding

/**
 * Created by shubhampandey
 */
class FeedLoadingAdapter(
    private val retryCallback: () -> Unit
) : LoadStateAdapter<NetworkStateItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        NetworkStateItemViewHolder(
            ItemNetworkStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        ) { retryCallback() }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) = holder.bind(loadState)
}