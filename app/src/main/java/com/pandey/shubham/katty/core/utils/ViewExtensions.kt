package com.pandey.shubham.katty.core.utils

import android.text.Editable
import android.text.TextWatcher
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
import androidx.recyclerview.widget.SnapHelper
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
    this.visible()
}

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

fun onAfterTextChange(block: (CharSequence?) -> Unit) = object : TextWatcher{
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable?) {
        block(s)
    }
}

fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
    val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
    val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
    return layoutManager.getPosition(snapView)
}