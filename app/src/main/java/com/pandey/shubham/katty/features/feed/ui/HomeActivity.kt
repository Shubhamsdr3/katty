package com.pandey.shubham.katty.features.feed.ui

import android.os.Bundle
import com.pandey.shubham.katty.R
import com.pandey.shubham.katty.core.base.NetworkLoaderActivity
import com.pandey.shubham.katty.features.detail.ui.FeedItemDetailFragment
import com.pandey.shubham.katty.features.feed.ui.callbacks.HomeFeedFragmentCallback
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

    override fun openDetailFragment(catBreedId: String?, isFavorite: Boolean) {
        openFragment(R.id.feed_container, FeedItemDetailFragment.newInstance(catBreedId, isFavorite), addToBackStack = true)
    }

    override fun handleBackPressed() {
        // do nothing
    }
}