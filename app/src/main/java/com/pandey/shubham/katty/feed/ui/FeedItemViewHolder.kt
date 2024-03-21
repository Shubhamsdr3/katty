package com.pandey.shubham.katty.feed.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pandey.shubham.katty.databinding.ItemFeedViewBinding
import com.pandey.shubham.katty.feed.data.FeedItem

/**
 * Created by shubhampandey
 */
class FeedItemViewHolder(private val binding: ItemFeedViewBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(feedItem: FeedItem) {
        feedItem.run {
            Glide.with(binding.root).load(imageUrl).into(binding.ivFeed)
        }
    }
}