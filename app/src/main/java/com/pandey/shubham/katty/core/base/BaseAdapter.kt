package com.pandey.shubham.katty.core.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseAdapter<T>.BaseViewHolder>() {

    protected val models = mutableListOf<T>()

    fun updateData(list: List<T>) {
        models.clear()
        models.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(models[position])
    }

    override fun getItemCount() = models.size

    abstract inner class BaseViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(model: T)
    }
}