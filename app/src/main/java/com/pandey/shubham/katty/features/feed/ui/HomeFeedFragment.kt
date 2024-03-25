package com.pandey.shubham.katty.features.feed.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pandey.shubham.katty.R
import com.pandey.shubham.katty.core.base.BaseFragment
import com.pandey.shubham.katty.core.database.CatBreedInfoEntity
import com.pandey.shubham.katty.core.model.ErrorMessage
import com.pandey.shubham.katty.core.utils.SpaceItemDecoration
import com.pandey.shubham.katty.core.utils.Utility
import com.pandey.shubham.katty.core.utils.gone
import com.pandey.shubham.katty.core.utils.visible
import com.pandey.shubham.katty.databinding.FragmentHomeFeedBinding
import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo
import com.pandey.shubham.katty.features.feed.ui.adapter.FeedLoadingAdapter
import com.pandey.shubham.katty.features.feed.ui.adapter.HomeFeedAdapter
import com.pandey.shubham.katty.features.feed.ui.callbacks.HomeFeedFragmentCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
open class HomeFeedFragment : BaseFragment<FragmentHomeFeedBinding, HomeFeedFragmentCallback>() {

    private val viewModel: HomeFeedViewModel by viewModels()

    private var currentDetailId: String? = null

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
        if (!Utility.isNetworkAvailable()) {
            showActionSnackBar(getString(R.string.internet_error), getString(R.string.try_again)) {
                feedAdapter.retry()
            }
        }
    }

    private fun attachObserver() {
        viewModel.fetchFeedDataPaginated().observe(viewLifecycleOwner) {
            feedAdapter.submitData(lifecycle, it)
        }

        lifecycleScope.launch {
            feedAdapter.loadStateFlow.collectLatest { loadStates ->
                when(loadStates.refresh) {
                    is LoadState.Loading -> handleLoader()
                    is LoadState.Error -> handleError(ErrorMessage(errorMessage = (loadStates.refresh as LoadState.Error).error.message))
                    is LoadState.NotLoading -> handleIdleState()
                }
            }
        }
        viewModel.homeUiState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is HomeUiState.OnFavoriteEvent -> updateFavouriteItem(state.catBreedItemEntity)
                else -> {
                    // do nothing
                }
            }
        }
    }

    private fun updateFavouriteItem(catBreedItemInfo: CatBreedInfoEntity?) {
        catBreedItemInfo?.let {
            feedAdapter.updateItem(it.id, catBreedItemInfo.isFavourite)
        }
    }

    private fun handleLoader() {
        if (feedAdapter.itemCount == 0) {
            binding.swipeRefresh.isRefreshing = false
            showLoader()
        } else {
            hideLoader()
            binding.swipeRefresh.isRefreshing = true
        }
    }

    private fun handleError(error: ErrorMessage) {
        hideLoader()
        binding.swipeRefresh.isRefreshing = false
        if (feedAdapter.itemCount == 0) {
            onInitialLoadingError(error)
        } else {
            onRefreshError(error)
        }
    }

    private fun onRefreshError(error: ErrorMessage) {
        val errorMessage = error.errorMessage ?: getString(R.string.something_went_wrong)
        showActionSnackBar(errorMessage, getString(R.string.try_again)) { feedAdapter.retry() }
    }

    private fun onInitialLoadingError(error: ErrorMessage) {
        binding.rvFeed.gone()
        showError(error) { feedAdapter.retry() }
    }

    private fun handleIdleState() {
        hideLoader()
        hideError()
        binding.rvFeed.visible()
        binding.swipeRefresh.isRefreshing = false
    }

    private fun setFeedAdapter() {
        with(binding.rvFeed) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            val loadingStateAdapter = FeedLoadingAdapter { feedAdapter.retry() }
            feedAdapter.addLoadStateListener { loadStates ->
                loadingStateAdapter.loadState = loadStates.refresh
                loadingStateAdapter.loadState = loadStates.append
            }
            adapter = ConcatAdapter(loadingStateAdapter, feedAdapter, loadingStateAdapter)
            addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelOffset(R.dimen.dimen32dp), RecyclerView.VERTICAL))
        }
    }

    private fun onFavouriteClicked(favourite: CatBreedItemInfo) {
        viewModel.addToFavourite(favourite)
    }

    private fun openDetailFragment(feedItem: CatBreedItemInfo) {
        currentDetailId = feedItem.breedId
        callback?.openDetailFragment(feedItem.breedId, feedItem.isFavourite)
    }

    override fun handleBackPressed() {
        if (currentDetailId.isNullOrBlank()) return
        viewModel.getFavoriteBreed(currentDetailId!!)
    }

    companion object {

        fun newInstance() = HomeFeedFragment()
    }
}