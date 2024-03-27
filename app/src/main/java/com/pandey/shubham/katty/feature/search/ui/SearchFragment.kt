package com.pandey.shubham.katty.feature.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.pandey.shubham.katty.core.base.BaseFragment
import com.pandey.shubham.katty.core.utils.Callback
import com.pandey.shubham.katty.databinding.FragmentSearchBinding

/**
 * Created by shubhampandey
 */
class SearchFragment: BaseFragment<FragmentSearchBinding, Callback>() {
    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun populateViews() {
        binding.searchToolbar.setNavigationOnClickListener { onBackPressed() }
        binding.etSearch.onTextChanged(lifecycle) {
            //
        }
    }

    override fun attachListeners() {
        // TODO
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}