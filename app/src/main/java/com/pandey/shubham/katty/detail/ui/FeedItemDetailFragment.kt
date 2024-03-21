package com.pandey.shubham.katty.detail.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.pandey.shubham.katty.base.BaseFragment
import com.pandey.shubham.katty.databinding.FragmentFeedItemDetailBinding
import com.pandey.shubham.katty.feed.data.FeedItem
import com.pandey.shubham.katty.utils.Callback
import com.pandey.shubham.katty.utils.setImage
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by shubhampandey
 */

@AndroidEntryPoint
class FeedItemDetailFragment : BaseFragment<FragmentFeedItemDetailBinding, Callback>() {

    private val viewModel: FeedDetailViewModel by viewModels()

    private val cateId: String? by lazy { arguments?.getString(CAT_ID) }

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFeedItemDetailBinding {
        return FragmentFeedItemDetailBinding.inflate(inflater, container, false)
    }

    override fun populateViews() {
        attachObserver()
        attachListener()
    }

    private fun attachListener() {
        binding.feedDetailToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun attachObserver() {
        viewModel.getCateDetail(cateId)
        viewModel.feedDetailUiState.observe(viewLifecycleOwner) { onFeedDetailUiStateChange(it) }
    }

    private fun onFeedDetailUiStateChange(state: FeedDetailUiState?) {
        when(state) {
            is FeedDetailUiState.ShowLoader -> showLoader()
            is FeedDetailUiState.ShowError -> showError()
            is FeedDetailUiState.ShowFeedDetail -> showFeedDetail(state.feedItem)
            else -> {
                // do nothing
            }
        }
    }

    private fun showFeedDetail(feedItem: FeedItem?) {
        feedItem?.run {
            binding.ivFeedDetail.setImage(imageUrl)
        }
    }

    private fun showLoader() {

    }

    private fun showError() {

    }

    companion object {

        private const val CAT_ID = "cat_id"

        fun newInstance(catId: String) = FeedItemDetailFragment().apply {
            arguments = bundleOf(Pair(CAT_ID, catId))
        }
    }
}