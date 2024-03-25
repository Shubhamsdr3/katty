package com.pandey.shubham.katty.core.base

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by shubhampandey
 */
abstract class BaseViewHolder<T, VB: ViewBinding>(open val binding: VB): RecyclerView.ViewHolder(binding.root) {

    protected val context: Context by lazy { binding.root.context }
    abstract fun bind(data: T)
}