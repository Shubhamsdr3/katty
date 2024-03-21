package com.pandey.shubham.katty.feed.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.commit
import com.pandey.shubham.katty.R
import com.pandey.shubham.katty.base.BaseActivity
import com.pandey.shubham.katty.databinding.ActivityMainBinding
import com.pandey.shubham.katty.detail.ui.FeedItemDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityMainBinding>(), HomeFeedFragmentCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadFragment(savedInstanceState)
    }

    override fun viewBinding() = ActivityMainBinding.inflate(layoutInflater)

    private fun loadFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            openFragment(R.id.feed_container, HomeFeedFragment.newInstance())
        }
    }

    override fun openDetailFragment(catId: String) {
        openFragment(R.id.feed_container, FeedItemDetailFragment.newInstance(catId))
    }
}