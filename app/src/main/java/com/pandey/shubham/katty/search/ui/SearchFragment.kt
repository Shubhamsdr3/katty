package com.pandey.shubham.katty.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.pandey.shubham.katty.base.BaseFragment
import com.pandey.shubham.katty.databinding.FragmentSearchBinding
import com.pandey.shubham.katty.feed.ui.HomeFeedFragmentCallback

/**
 * Created by shubhampandey
 */
class SearchFragment: BaseFragment<FragmentSearchBinding, HomeFeedFragmentCallback>() {


    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun populateViews() {

    }
}