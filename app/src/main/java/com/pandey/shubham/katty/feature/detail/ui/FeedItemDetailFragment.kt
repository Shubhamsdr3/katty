package com.pandey.shubham.katty.feature.detail.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.pandey.shubham.katty.core.base.BaseFragment
import com.pandey.shubham.katty.core.model.ErrorMessage
import com.pandey.shubham.katty.databinding.FragmentFeedItemDetailBinding
import com.pandey.shubham.katty.feature.feed.data.dtos.CatImageResponse
import com.pandey.shubham.katty.core.utils.Callback
import com.pandey.shubham.katty.core.utils.setImage
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
            is FeedDetailUiState.ShowError -> handleError(state.throwable)
            is FeedDetailUiState.ShowFeedDetail -> showFeedDetail(state.feedItem)
            else -> {
                // do nothing
            }
        }
    }

    private fun handleError(throwable: Throwable?) {
        showError(ErrorMessage(500, throwable?.message)) {
            // retry
        }
    }

    private fun showFeedDetail(feedItem: CatImageResponse?) {
        feedItem?.run {
            binding.ivFeedDetail.setImage(imageUrl)
        }
    }

    companion object {

        private const val CAT_ID = "cat_id"

        fun newInstance(catId: String?) = FeedItemDetailFragment().apply {
            arguments = bundleOf(Pair(CAT_ID, catId))
        }
    }
}