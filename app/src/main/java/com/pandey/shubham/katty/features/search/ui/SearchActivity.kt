package com.pandey.shubham.katty.features.search.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.pandey.shubham.katty.R
import com.pandey.shubham.katty.core.base.NetworkLoaderActivity

/**
 * Created by shubhampandey
 */
class SearchActivity: NetworkLoaderActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadFragment(savedInstanceState)
    }

    private fun loadFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            openFragment(R.id.feed_container, SearchFragment.newInstance())
        }
    }

    override fun onBackStackUpdate() {
        // do nothing
    }

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, SearchActivity::class.java))
        }
    }
}