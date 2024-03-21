package com.pandey.shubham.katty.feed.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pandey.shubham.katty.R
import com.pandey.shubham.katty.databinding.ItemFeedViewBinding
import com.pandey.shubham.katty.feed.data.FeedItem

/**
 * Created by shubhampandey
 */
class HomeFeedAdapter(private val onItemClick: (FeedItem) -> Unit) : PagingDataAdapter<FeedItem, FeedItemViewHolder>(DIFF_UTILS) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedItemViewHolder {
        val itemBinding = ItemFeedViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = FeedItemViewHolder(itemBinding)
        val position = viewHolder.absoluteAdapterPosition
//        if (position != RecyclerView.NO_POSITION) { // TODO fixme
//            val item = getItem(position)
//            viewHolder.itemView.setOnClickListener {
//                item?.let { onItemClick(it) }
//            }
//        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val item = getItem(position)
            item?.let {
                holder.bind(item)
                holder.itemView.setOnClickListener { onItemClick(item) }
                holder.itemView.findViewById<View>(R.id.iv_favourite).setOnClickListener {
                    item.isFavourite = !item.isFavourite
                    notifyItemChanged(position, item.isFavourite)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            holder.toggleFavourite(payloads[0] as Boolean)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    companion object {
        private val DIFF_UTILS = object : DiffUtil.ItemCallback<FeedItem>() {
            override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}