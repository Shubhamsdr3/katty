package com.pandey.shubham.katty.core.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by shubhampandey
 */
class SpaceItemDecoration(offset: Int, @RecyclerView.Orientation private val orientation: Int): RecyclerView.ItemDecoration() {

    private val halfSpace = offset / 2 // to maintain the symmetry

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (orientation == RecyclerView.VERTICAL) {
            outRect.top = halfSpace
            outRect.bottom = halfSpace
        } else {
            outRect.left = halfSpace
            outRect.right = halfSpace
        }
    }
}