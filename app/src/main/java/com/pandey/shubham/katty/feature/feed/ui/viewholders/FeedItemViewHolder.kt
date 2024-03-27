package com.pandey.shubham.katty.feature.feed.ui.viewholders

import com.bumptech.glide.Glide
import com.pandey.shubham.katty.R
import com.pandey.shubham.katty.core.base.BaseViewHolder
import com.pandey.shubham.katty.core.utils.setDrawable
import com.pandey.shubham.katty.databinding.ItemFeedViewBinding
import com.pandey.shubham.katty.feature.feed.domain.model.CatBreedItemInfo

/**
 * Created by shubhampandey
 */
class FeedItemViewHolder(override val binding: ItemFeedViewBinding): BaseViewHolder<CatBreedItemInfo, ItemFeedViewBinding>(binding) {

    override fun bind(data: CatBreedItemInfo) {
        data.run {
            Glide.with(binding.root).load(imageUrl).into(binding.ivFeed)
            binding.tvName.text = context.getString(R.string.txt_name, name ?: "")
            binding.tvOrigin.text = context.getString(R.string.txt_origin, origin)
            binding.tvTemperament.text = context.getString(R.string.txt_temperament, temperament)
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