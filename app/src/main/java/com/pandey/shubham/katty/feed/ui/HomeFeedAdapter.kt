package com.pandey.shubham.katty.feed.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pandey.shubham.katty.databinding.ItemFeedViewBinding
import com.pandey.shubham.katty.feed.data.FeedItem

/**
 * Created by shubhampandey
 */
class HomeFeedAdapter(private val dataset: List<FeedItem>, private val onItemClick: (FeedItem) -> Unit): RecyclerView.Adapter<FeedItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedItemViewHolder {
        val itemBinding = ItemFeedViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = FeedItemViewHolder(itemBinding)
        viewHolder.itemView.setOnClickListener {
            onItemClick(dataset[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int = dataset.count()

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            holder.bind(dataset[position])
        }
    }
}