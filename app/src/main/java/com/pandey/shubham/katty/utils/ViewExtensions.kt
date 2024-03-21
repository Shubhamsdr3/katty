package com.pandey.shubham.katty.utils

import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.pandey.shubham.katty.R

/**
 * Created by shubhampandey
 */
fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun AppCompatImageView.setImage(imageUrl: String?, @DrawableRes placeHolder: Int = R.drawable.ic_launcher_background) {
    Glide.with(this)
        .load(imageUrl)
        .placeholder(ContextCompat.getDrawable(context, placeHolder))
        .into(this)
}