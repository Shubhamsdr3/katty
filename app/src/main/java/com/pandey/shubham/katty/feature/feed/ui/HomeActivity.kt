package com.pandey.shubham.katty.feature.feed.ui

import android.os.Bundle
import com.pandey.shubham.katty.R
import com.pandey.shubham.katty.core.base.NetworkLoaderActivity
import com.pandey.shubham.katty.feature.detail.ui.FeedItemDetailFragment
import com.pandey.shubham.katty.feature.feed.ui.callbacks.HomeFeedFragmentCallback
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : NetworkLoaderActivity(), HomeFeedFragmentCallback {

    private var backStackCount = 0
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
        backStackCount = supportFragmentManager.backStackEntryCount
    }

    override fun onBackStackUpdate() {
        if (supportFragmentManager.backStackEntryCount == backStackCount - 1) {
            val homeFragment = supportFragmentManager.findFragmentByTag(HomeFeedFragment::class.java.canonicalName)
            if (homeFragment != null && homeFragment is HomeFeedFragment) {
                homeFragment.updateFavorite()
            }
        }
        backStackCount = supportFragmentManager.backStackEntryCount
    }
}