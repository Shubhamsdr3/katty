package com.pandey.shubham.katty.core.base

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by shubhampandey
 */
abstract class BasePagingAdapter<T : Any, VH : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>) : PagingDataAdapter<T, VH>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return viewHolder(parent, viewType)
    }
    override fun onBindViewHolder(holder: VH, position: Int) {

    }
   abstract fun viewHolder(parent: ViewGroup, viewType: Int): VH
}