package com.pandey.shubham.katty.feature.detail.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.pandey.shubham.katty.R
import com.pandey.shubham.katty.core.base.BaseFragment
import com.pandey.shubham.katty.core.failure.model.ErrorMessage
import com.pandey.shubham.katty.core.utils.Callback
import com.pandey.shubham.katty.core.utils.ERROR_ADDING_FAVOURITE
import com.pandey.shubham.katty.core.utils.setDrawable
import com.pandey.shubham.katty.databinding.FragmentFeedItemDetailBinding
import com.pandey.shubham.katty.feature.detail.data.CatImageResponseItem
import com.pandey.shubham.katty.feature.detail.domain.model.CatDetailInfo
import com.pandey.shubham.katty.feature.feed.domain.model.CatBreedItemInfo
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by shubhampandey
 */

@AndroidEntryPoint
class FeedItemDetailFragment : BaseFragment<FragmentFeedItemDetailBinding, Callback>() {

    private val viewModel: FeedDetailViewModel by viewModels()

    private val cateBreedId: String? by lazy { arguments?.getString(CAT_BREED_ID) }

    private var catBreedItemInfo: CatBreedItemInfo? = null

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFeedItemDetailBinding {
        return FragmentFeedItemDetailBinding.inflate(inflater, container, false)
    }

    override fun populateViews() {
        attachObserver()
    }

    override fun attachListeners() {
        binding.feedDetailToolbar.setNavigationOnClickListener { onBackPressed() }
        binding.ivFavourite.setOnClickListener { onFavoriteClicked() }
    }

    private fun onFavoriteClicked() {
        catBreedItemInfo?.let {
            it.isFavourite = !it.isFavourite
            viewModel.addToFavourite(it)
            toggleFavourite(it.isFavourite)
        }
    }

    private fun attachObserver() {
        viewModel.feedDetailUiState.observe(viewLifecycleOwner) { onFeedDetailUiStateChange(it) }
    }

    private fun onFeedDetailUiStateChange(state: FeedDetailUiState) {
        when(state) {
            is FeedDetailUiState.ShowLoader -> showLoader()
            is FeedDetailUiState.ShowError -> handleError(state.throwable)
            is FeedDetailUiState.ShowFeedDetail -> showFeedDetail(state.detailInfo)
            is FeedDetailUiState.OnFavouriteEvent -> onFavoriteEvent(state.isSuccess, state.error)
        }
    }

    private fun onFavoriteEvent(success: Boolean, error: String?) {
        if (!success) { showSnackBar(error ?: ERROR_ADDING_FAVOURITE) }
    }

    private fun handleError(throwable: Throwable?) {
        hideLoader()
        showError(ErrorMessage(errorMessage = throwable?.message)) {
            viewModel.getCateDetail(cateBreedId)
        }
    }

    private fun showFeedDetail(detail: CatDetailInfo?) {
        hideLoader()
        val catInfo = detail?.breedItemInfo
        catInfo?.run {
            catBreedItemInfo = catInfo
            binding.tvName.text = requireContext().getString(R.string.txt_name, name ?: "")
            binding.tvOrigin.text = requireContext().getString(R.string.txt_origin, origin)
            binding.tvTemperament.text = requireContext().getString(R.string.txt_temperament, temperament)
            binding.tvDescription.text = requireContext().getString(R.string.txt_description, description)
            toggleFavourite(isFavourite)
            showImages(detail.images)
        }
    }

    private fun showImages(images: List<CatImageResponseItem>?) {
        if (!images.isNullOrEmpty()) {
            val imageUrls = images.mapNotNull { it.url }
            binding.imageCarousel.setData(imageUrls)
        }
    }

    private fun toggleFavourite(isFavourite: Boolean) {
        if (isFavourite) {
            binding.ivFavourite.setDrawable(R.drawable.icon_heart_filled_red_24)
        } else {
            binding.ivFavourite.setDrawable(R.drawable.icon_heart_outlined_24)
        }
    }

    companion object {

        const val CAT_BREED_ID = "cat_breed_id"
        private const val IS_FAVOURITE = "is_favourite"

        fun newInstance(catBreedId: String?, isFavourite: Boolean) = FeedItemDetailFragment().apply {
            arguments = bundleOf(
                Pair(CAT_BREED_ID, catBreedId),
                Pair(IS_FAVOURITE, isFavourite)
            )
        }
    }
}