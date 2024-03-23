package com.pandey.shubham.katty.features.detail.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.pandey.shubham.katty.R
import com.pandey.shubham.katty.core.base.BaseFragment
import com.pandey.shubham.katty.core.model.ErrorMessage
import com.pandey.shubham.katty.databinding.FragmentFeedItemDetailBinding
import com.pandey.shubham.katty.core.utils.Callback
import com.pandey.shubham.katty.core.utils.setDrawable
import com.pandey.shubham.katty.core.utils.setNetworkImage
import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo
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
        attachListener()
    }

    private fun attachListener() {
        binding.feedDetailToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.ivFavourite.setOnClickListener {
            catBreedItemInfo?.let {
                it.isFavourite = !it.isFavourite
                viewModel.addToFavourite(it)
                toggleFavourite(it.isFavourite)
            }
        }
    }

    private fun attachObserver() {
        viewModel.getCateDetail(cateBreedId)
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
        hideLoader()
        showError(ErrorMessage(500, throwable?.message)) {
            // retry
        }
    }

    private fun showFeedDetail(detail: CatBreedItemInfo?) {
        hideLoader()
        detail?.run {
            catBreedItemInfo = detail
            binding.ivFeedDetail.setNetworkImage(imageUrl)
            binding.tvName.text = requireContext().getString(R.string.txt_name, name ?: "")
            binding.tvOrigin.text = requireContext().getString(R.string.txt_origin, origin)
            binding.tvTemperament.text = requireContext().getString(R.string.txt_temperament, temperament)
            binding.tvDescription.text = requireContext().getString(R.string.txt_description, description)
            toggleFavourite(isFavourite)
        }
    }

    private fun toggleFavourite(isFavourite: Boolean) {
        if (isFavourite == true) {
            binding.ivFavourite.setDrawable(R.drawable.icon_heart_filled_red_24)
        } else {
            binding.ivFavourite.setDrawable(R.drawable.icon_heart_outlined_24)
        }
    }

    companion object {

        private const val CAT_BREED_ID = "cat_breed_id"
        private const val IS_FAVOURITE = "is_favourite"

        fun newInstance(catBreedId: String?, isFavourite: Boolean) = FeedItemDetailFragment().apply {
            arguments = bundleOf(
                Pair(CAT_BREED_ID, catBreedId),
                Pair(IS_FAVOURITE, isFavourite)
            )
        }
    }
}