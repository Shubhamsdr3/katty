package com.pandey.shubham.katty.feature.feed.ui

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pandey.shubham.katty.R
import com.pandey.shubham.katty.core.base.BaseFragment
import com.pandey.shubham.katty.core.database.CatBreedInfoEntity
import com.pandey.shubham.katty.core.failure.model.ErrorMessage
import com.pandey.shubham.katty.core.utils.SpaceItemDecoration
import com.pandey.shubham.katty.core.utils.Utility
import com.pandey.shubham.katty.core.utils.gone
import com.pandey.shubham.katty.core.utils.visible
import com.pandey.shubham.katty.databinding.FragmentHomeFeedBinding
import com.pandey.shubham.katty.feature.feed.domain.model.CatBreedItemInfo
import com.pandey.shubham.katty.feature.feed.ui.adapter.FeedLoadingAdapter
import com.pandey.shubham.katty.feature.feed.ui.adapter.HomeFeedAdapter
import com.pandey.shubham.katty.feature.feed.ui.callbacks.HomeFeedFragmentCallback
import com.pandey.shubham.katty.feature.search.ui.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFeedFragment : BaseFragment<FragmentHomeFeedBinding, HomeFeedFragmentCallback>() {

    private val viewModel: HomeFeedViewModel by viewModels()

    private var currentDetailPosition: Int = 0
    private var currentDetailId : String? = null

    private val feedAdapter: HomeFeedAdapter by lazy {
        HomeFeedAdapter(
            { position, item -> openDetailFragment(position, item) },
            { item -> onFavouriteClicked(item) }
        )
    }

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeFeedBinding {
        return FragmentHomeFeedBinding.inflate(inflater, container, false)
    }

    override fun populateViews() {
        setupOptionMenu()
        setFeedAdapter()
        attachObserver()
    }

    private fun setupOptionMenu() {
        with(requireActivity() as AppCompatActivity) {
            setSupportActionBar(binding.toolbar)
            addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.main_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when(menuItem.itemId) {
                        R.id.action_search -> SearchActivity.start(this@with)
                        R.id.action_refresh -> feedAdapter.refresh()
                    }
                    return true
                }
            }, viewLifecycleOwner)
        }
    }

    override fun attachListeners() {
        binding.swipeRefresh.setOnRefreshListener { feedAdapter.refresh() }
        if (!Utility.isNetworkAvailable()) {
            showActionSnackBar(getString(R.string.internet_error), getString(R.string.try_again)) {
                feedAdapter.retry()
            }
        }
    }

    private fun attachObserver() {
        viewModel.fetchFeedDataPaginated.observe(viewLifecycleOwner) {
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
        if (catBreedItemInfo == null) {
            feedAdapter.updateItem(currentDetailPosition, false) // removed from favorite
        } else {
            feedAdapter.updateItem(currentDetailPosition, catBreedItemInfo.isFavourite)
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
            adapter = feedAdapter.withLoadStateFooter(FeedLoadingAdapter { feedAdapter.retry() })
            addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelOffset(R.dimen.dimen32dp), RecyclerView.VERTICAL))
        }
    }

    private fun onFavouriteClicked(favourite: CatBreedItemInfo) {
        viewModel.addToFavourite(favourite)
    }

    /**
     * Update the favorite state when user navigate back from detail.
     */
    fun updateFavorite() {
        if (currentDetailId.isNullOrBlank()) return
        viewModel.getFavoriteBreed(currentDetailId!!)
    }

    private fun openDetailFragment(position: Int, feedItem: CatBreedItemInfo) {
        this.currentDetailPosition = position
        currentDetailId = feedItem.breedId
        callback?.openDetailFragment(feedItem.breedId, feedItem.isFavourite)
    }

    companion object {

        fun newInstance() = HomeFeedFragment()
    }
}