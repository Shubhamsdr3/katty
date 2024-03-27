package com.pandey.shubham.katty.core.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VB : ViewBinding>(
    private val dataset: List<T>
) : RecyclerView.Adapter<BaseViewHolder<T, VB>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T, VB> {
        return viewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T, VB>, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            holder.bind(data = dataset[position])
        }
    }

    protected abstract fun viewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T, VB>

    override fun getItemCount() = dataset.count()
}