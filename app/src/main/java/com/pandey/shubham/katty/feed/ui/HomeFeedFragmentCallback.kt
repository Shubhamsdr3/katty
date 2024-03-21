package com.pandey.shubham.katty.feed.ui

import com.pandey.shubham.katty.utils.Callback

/**
 * Created by shubhampandey
 */
interface HomeFeedFragmentCallback : Callback {

    fun openDetailFragment(catId: String)
}