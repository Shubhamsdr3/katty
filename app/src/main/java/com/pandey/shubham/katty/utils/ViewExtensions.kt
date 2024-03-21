package com.pandey.shubham.katty.utils

import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.pandey.shubham.katty.R

/**
 * Created by shubhampandey
 */

fun AppCompatActivity.shouldInterceptBackPress() = supportFragmentManager.backStackEntryCount > 0

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

fun AppCompatImageView.setDrawable(@DrawableRes resId: Int) {
    this.setImageDrawable(ContextCompat.getDrawable(context, resId))
}

inline fun <reified T> FragmentActivity.getTopFragment(): T?
        = supportFragmentManager.fragments.firstOrNull()?.let { it as? T }