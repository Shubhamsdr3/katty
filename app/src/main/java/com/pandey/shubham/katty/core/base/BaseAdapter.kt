package com.pandey.shubham.katty.core.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VB: ViewBinding> : RecyclerView.Adapter<BaseViewHolder<T, VB>>() {

    protected val models = mutableListOf<T>()

    fun updateData(list: List<T>) {
        models.clear()
        models.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T, VB>, position: Int) {
        holder.bind(models[position])
    }

    override fun getItemCount() = models.size
}