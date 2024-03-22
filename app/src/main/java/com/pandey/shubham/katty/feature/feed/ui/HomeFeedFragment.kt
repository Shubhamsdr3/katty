package com.pandey.shubham.katty.feature.feed.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pandey.shubham.katty.R
import com.pandey.shubham.katty.core.base.BaseFragment
import com.pandey.shubham.katty.databinding.FragmentHomeFeedBinding
import com.pandey.shubham.katty.feature.feed.ui.callbacks.HomeFeedFragmentCallback
import com.pandey.shubham.katty.feature.feed.domain.model.CatBreedItemInfo
import com.pandey.shubham.katty.feature.feed.ui.adapter.FeedLoadingAdapter
import com.pandey.shubham.katty.feature.feed.ui.adapter.HomeFeedAdapter
import com.pandey.shubham.katty.core.model.ErrorMessage
import com.pandey.shubham.katty.core.utils.SpaceItemDecoration
import com.pandey.shubham.katty.core.utils.gone
import com.pandey.shubham.katty.core.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFeedFragment : BaseFragment<FragmentHomeFeedBinding, HomeFeedFragmentCallback>() {

    private val viewModel: HomeFeedViewModel by viewModels()

    private val feedAdapter: HomeFeedAdapter by lazy {
        HomeFeedAdapter(
            { openDetailFragment(it) },
            { onFavouriteClicked(it) }
        )
    }

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeFeedBinding {
        return FragmentHomeFeedBinding.inflate(inflater, container, false)
    }

    override fun populateViews() {
        attachListener()
        setFeedAdapter()
        attachObserver()
    }

    private fun attachListener() {
        binding.swipeRefresh.setOnRefreshListener { feedAdapter.refresh() }
    }

    private fun attachObserver() {
        viewModel.fetchFeedDataPaginated().observe(viewLifecycleOwner) {
            hideLoader()
            feedAdapter.submitData(lifecycle, it)
        }
        lifecycleScope.launch {
            feedAdapter.loadStateFlow.collectLatest { loadStates ->
                when(loadStates.refresh) {
                    is LoadState.Loading -> showLoader()
                    is LoadState.Error -> handleError((loadStates.refresh as LoadState.Error).error)
                    else -> {
                        // do nothing
                    }
                }
            }
        }
    }

    private fun handleError(error: Throwable) {
        showError(ErrorMessage(500, errorMessage = error.message)) {
            feedAdapter.retry()
        }
    }

    private fun setFeedAdapter() {
        with(binding.rvFeed) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = feedAdapter.run {
                withLoadStateFooter(FeedLoadingAdapter { retry() })
            }
            addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelOffset(R.dimen.dimen32dp), RecyclerView.VERTICAL))
        }
    }

    private fun onFavouriteClicked(favourite: CatBreedItemInfo) {
        viewModel.addToFavourite(favourite)
    }

    private fun openDetailFragment(feedItem: CatBreedItemInfo) {
        callback?.openDetailFragment(feedItem.breedId)
    }

    companion object {

        fun newInstance() = HomeFeedFragment()
    }
}