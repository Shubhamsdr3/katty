package com.pandey.shubham.katty.features.feed.ui.callbacks

import com.pandey.shubham.katty.core.utils.Callback

/**
 * Created by shubhampandey
 */
interface HomeFeedFragmentCallback : Callback {

    fun openDetailFragment(catBreedId: String?, isFavorite: Boolean)
}