package com.pandey.shubham.katty.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.pandey.shubham.katty.utils.Callback

/**
 * Created by shubhampandey
 */
abstract class BaseFragment<VB: ViewBinding, T> : Fragment() {

    private var _binding: VB? = null

    protected val binding: VB get() = _binding!!

    private var _callback: T? = null

    protected val callback: T? get() = _callback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment != null) {
            this._callback = parentFragment as T
        } else if (context is Callback) {
            this._callback = context as T
        } else {
            throw IllegalArgumentException("$context must implement fragment callback") //TODO fix exception type.
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = viewBinding(inflater, container)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateViews()
    }

    protected fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    protected abstract fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    protected abstract fun populateViews()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}