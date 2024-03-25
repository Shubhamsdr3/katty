package com.pandey.shubham.katty.features.feed.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pandey.shubham.katty.R
import com.pandey.shubham.katty.core.database.CatBreedInfoEntity
import com.pandey.shubham.katty.databinding.ItemFeedViewBinding
import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo
import com.pandey.shubham.katty.features.feed.ui.viewholders.FeedItemViewHolder
import com.pandey.shubham.katty.core.utils.absoluteAdapterPosition

/**
 * Created by shubhampandey
 */
class HomeFeedAdapter(
    private val onItemClick: (CatBreedItemInfo) -> Unit,
    private val onFavoriteClicked: (CatBreedItemInfo) -> Unit
) : PagingDataAdapter<CatBreedItemInfo, FeedItemViewHolder>(DIFF_UTILS) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedItemViewHolder {
        val itemBinding = ItemFeedViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = FeedItemViewHolder(itemBinding)
        setItemClickListener(viewHolder)
        return viewHolder
    }

    private fun setItemClickListener(viewHolder: FeedItemViewHolder) {
        with(viewHolder) {
            itemView.setOnClickListener {
                absoluteAdapterPosition { position ->
                    getItem(position) { onItemClick(it) }
                }
            }
            itemView.findViewById<View>(R.id.iv_favourite).setOnClickListener {
                absoluteAdapterPosition { position ->
                    getItem(position) { item ->
                        item.isFavourite = !item.isFavourite
                        onFavoriteClicked(item)
                        notifyItemChanged(position, item.isFavourite)
                    }
                }
            }
        }
    }

    private inline fun getItem(position: Int, block: (CatBreedItemInfo) -> Unit) {
        getItem(position)?.let(block)
    }

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val item = getItem(position)
            item?.let { holder.bind(item) }
        }
    }

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            holder.toggleFavourite(payloads[0] as Boolean)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    fun updateItem(position: Int, isFavorite: Boolean) {
        notifyItemChanged(position, isFavorite)
    }

    companion object {
        private val DIFF_UTILS = object : DiffUtil.ItemCallback<CatBreedItemInfo>() {
            override fun areItemsTheSame(oldItem: CatBreedItemInfo, newItem: CatBreedItemInfo): Boolean {
                return oldItem.breedId == newItem.breedId
            }

            override fun areContentsTheSame(oldItem: CatBreedItemInfo, newItem: CatBreedItemInfo): Boolean {
                return oldItem == newItem
            }
        }
    }
}