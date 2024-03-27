package com.pandey.shubham.katty.feature.search.ui

import android.view.ViewGroup
import com.pandey.shubham.katty.core.base.BaseAdapter
import com.pandey.shubham.katty.core.base.BaseViewHolder
import com.pandey.shubham.katty.core.utils.inflater
import com.pandey.shubham.katty.databinding.ItemSearchResultBinding
import com.pandey.shubham.katty.feature.search.data.SearchSuggestion

/**
 * Created by shubhampandey
 */
class SearchAdapter(
    private val dataset: List<SearchSuggestion>
) : BaseAdapter<SearchSuggestion, ItemSearchResultBinding>(dataset) {

    override fun viewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SearchSuggestion, ItemSearchResultBinding> {
        val itemBinding = ItemSearchResultBinding.inflate(parent.inflater(), parent, false)
        return SearchItemViewHolder(itemBinding)
    }
}