package com.pandey.shubham.katty.core.widgets

import android.view.ViewGroup
import com.pandey.shubham.katty.R
import com.pandey.shubham.katty.core.base.BaseAdapter
import com.pandey.shubham.katty.core.base.BaseViewHolder
import com.pandey.shubham.katty.core.utils.inflater
import com.pandey.shubham.katty.core.utils.setNetworkImage
import com.pandey.shubham.katty.databinding.ItemCarouselImageBinding

/**
 * Created by shubhampandey
 */
class ImageCarouselAdapter(dataset: List<String>) : BaseAdapter<String, ItemCarouselImageBinding>(dataset) {
    override fun viewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String, ItemCarouselImageBinding> {
        val itemBinding = ItemCarouselImageBinding.inflate(parent.inflater(), parent, false)
        return ItemImageViewHolder(itemBinding)
    }

    class ItemImageViewHolder(
        override val binding: ItemCarouselImageBinding
    ) : BaseViewHolder<String, ItemCarouselImageBinding>(binding) {
        override fun bind(data: String) {
            binding.ivCarousel.setNetworkImage(data, R.drawable.image_plaholder)
        }
    }
}