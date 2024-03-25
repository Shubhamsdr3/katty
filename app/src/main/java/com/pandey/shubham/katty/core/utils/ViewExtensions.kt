package com.pandey.shubham.katty.core.utils

import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
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

fun AppCompatImageView.setNetworkImage(imageUrl: String?, @DrawableRes placeHolder: Int = R.drawable.ic_launcher_background) {
    this.visible()
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

inline fun FragmentManager.updateFragment(fragment: Fragment, allowStateLoss: Boolean = false, body: FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    val topFragment = findFragmentByTag(fragment::class.java.simpleName)
    if (topFragment != null) {
        transaction.remove(topFragment)
    }
    transaction.body()
    if (allowStateLoss) {
        transaction.commitAllowingStateLoss()
    } else {
        transaction.commit()
    }
}

inline fun RecyclerView.ViewHolder.absoluteAdapterPosition(callback: (Int) -> Unit) {
    val position = absoluteAdapterPosition
    if (position != RecyclerView.NO_POSITION) callback(position)
}