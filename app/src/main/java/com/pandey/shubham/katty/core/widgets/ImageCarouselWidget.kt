package com.pandey.shubham.katty.core.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.pandey.shubham.katty.R
import com.pandey.shubham.katty.core.base.BaseCustomView
import com.pandey.shubham.katty.core.utils.getSnapPosition
import com.pandey.shubham.katty.databinding.LayoutImageCarouselBinding

/**
 * Created by shubhampandey
 */
class ImageCarouselWidget @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defyStyle: Int = 0
) : BaseCustomView<LayoutImageCarouselBinding>(context, attributeSet, defyStyle) {

    override fun viewBinding() = LayoutImageCarouselBinding.inflate(LayoutInflater.from(context), this, true)

    fun setData(images: List<String>) {
        val pagerSnapHelper = PagerSnapHelper()
        with(binding.rvImages) {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = ImageCarouselAdapter(images)
            pagerSnapHelper.attachToRecyclerView(this)
            binding.indicator.text = context.getString(R.string.indicator_txt, 1, images.count())
            addOnScrollListener(snapScrollListener(pagerSnapHelper) {
                binding.indicator.text = context.getString(R.string.indicator_txt, it + 1, images.count())
            })
        }
    }

    private fun snapScrollListener(snapHelper: SnapHelper, onScrollChanged: (Int) -> Unit) = object : RecyclerView.OnScrollListener() {
            private var snapPosition = RecyclerView.NO_POSITION
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    maybeNotifySnapPositionChange(recyclerView)
                }
            }

            private fun maybeNotifySnapPositionChange(recyclerView: RecyclerView) {
                val snapPosition = snapHelper.getSnapPosition(recyclerView)
                val snapPositionChanged = this.snapPosition != snapPosition
                if (snapPositionChanged) {
                    onScrollChanged(snapPosition)
                    this.snapPosition = snapPosition
                }
            }
        }
}