package com.pandey.shubham.katty.feature.feed.ui

import android.os.Bundle
import com.pandey.shubham.katty.R
import com.pandey.shubham.katty.core.base.BaseActivity
import com.pandey.shubham.katty.core.base.NetworkLoaderActivity
import com.pandey.shubham.katty.databinding.ActivityMainBinding
import com.pandey.shubham.katty.feature.detail.ui.FeedItemDetailFragment
import com.pandey.shubham.katty.feature.feed.ui.callbacks.HomeFeedFragmentCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : NetworkLoaderActivity(), HomeFeedFragmentCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadFragment(savedInstanceState)
    }

    private fun loadFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            openFragment(R.id.feed_container, HomeFeedFragment.newInstance())
        }
    }

    override fun openDetailFragment(catBreedId: String?) {
        openFragment(R.id.feed_container, FeedItemDetailFragment.newInstance(catBreedId), addToBackStack = true)
    }
}