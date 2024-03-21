package com.pandey.shubham.katty.feed.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pandey.shubham.katty.R
import com.pandey.shubham.katty.databinding.ItemFeedViewBinding
import com.pandey.shubham.katty.feed.data.FeedItem
import com.pandey.shubham.katty.utils.setDrawable

/**
 * Created by shubhampandey
 */
class FeedItemViewHolder(private val binding: ItemFeedViewBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(feedItem: FeedItem) {
        feedItem.run {
            Glide.with(binding.root).load(imageUrl).into(binding.ivFeed)
            toggleFavourite(isFavourite)
        }
    }

    fun toggleFavourite(isFavorite: Boolean) {
        if (isFavorite) {
            binding.ivFavourite.setDrawable(R.drawable.icon_heart_filled_red_24)
        } else {
            binding.ivFavourite.setDrawable(R.drawable.icon_heart_outlined_24)
        }
    }
}