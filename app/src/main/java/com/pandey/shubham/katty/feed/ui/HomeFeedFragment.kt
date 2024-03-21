package com.pandey.shubham.katty.feed.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pandey.shubham.katty.base.BaseFragment
import com.pandey.shubham.katty.databinding.FragmentHomeFeedBinding
import com.pandey.shubham.katty.feed.data.FeedItem
import com.pandey.shubham.katty.feed.data.FeedResponse
import com.pandey.shubham.katty.model.ErrorMessage
import com.pandey.shubham.katty.network.HomeFeedUiState
import com.pandey.shubham.katty.utils.SpaceItemDecoration
import com.pandey.shubham.katty.utils.gone
import com.pandey.shubham.katty.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFeedFragment : BaseFragment<FragmentHomeFeedBinding, HomeFeedFragmentCallback>() {

    private val viewModel: HomeFeedViewModel by viewModels()

    private val feedAdapter: HomeFeedAdapter by lazy {
        HomeFeedAdapter { openDetailFragment(it) }
    }

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeFeedBinding {
        return FragmentHomeFeedBinding.inflate(inflater, container, false)
    }

    override fun populateViews() {
        attachObserver()
    }

    private fun attachObserver() {
        viewModel.fetchFeedData()
        viewModel.homeFeedUiState.observe(viewLifecycleOwner) { onHomeUiStateChange(it) }
        viewModel.fetchFeedDataPaginated().observe(viewLifecycleOwner) {
            feedAdapter.submitData(lifecycle, it)
        }
    }

    private fun onHomeUiStateChange(state: HomeFeedUiState?) {
        when(state) {
            is HomeFeedUiState.ShowLoader -> showLoader()
            is HomeFeedUiState.ShowError -> showError(state.throwable)
            is HomeFeedUiState.ShowFeedData -> showFeedData(state.feedResponse)
            else -> {
                // do nothing
            }
        }
    }

    private fun showFeedData(feedResponse: FeedResponse?) {
        hideLoader()
        if (feedResponse.isNullOrEmpty()) {
            // show error
            return
        }
        with(binding.rvFeed) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = feedAdapter.run {
                withLoadStateFooter(FeedLoadingAdapter { retry() })
            }
            addItemDecoration(SpaceItemDecoration(16, RecyclerView.VERTICAL))
        }
    }

    private fun openDetailFragment(feedItem: FeedItem) {
        if (!feedItem.id.isNullOrEmpty()) {
            callback?.openDetailFragment(feedItem.id)
        } else {
            // TODO what if id is null ?
        }
    }

    private fun showError(throwable: Throwable?) {
        hideLoader()
        binding.rvFeed.gone()
        binding.networkError.visible()
        binding.networkError.setData(ErrorMessage(500, throwable?.message))//TODO fix harcoded value.
    }

    private fun hideLoader() {
        binding.feedProgress.gone()
    }

    private fun showLoader() {
        binding.feedProgress.visible()
    }

    companion object {

        fun newInstance() = HomeFeedFragment()
    }
}