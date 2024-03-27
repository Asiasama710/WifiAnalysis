package com.hub.wifianalysis.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

/**
 * BaseFragment is an abstract class that provides a base implementation for a Fragment.
 * It includes functionality for data binding and lifecycle management.
 *
 * @param VB The type of the ViewDataBinding.
 */
abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    // The tag used for logging
    abstract val TAG: String

    // The layout resource ID for the fragment
    abstract val layoutIdFragment: Int

    // The ViewModel associated with the fragment
    abstract val viewModel: ViewModel

    // The binding for the fragment
    private lateinit var _binding: VB

    // A protected getter for the binding
    protected val binding get() = _binding

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = DataBindingUtil.inflate(
            inflater,
            layoutIdFragment,
            container, false
        )

        _binding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, viewModel)
            return root
        }
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved state has been restored in to the view.
     *
     * @param view The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    /**
     * An open method that can be overridden by subclasses to setup the fragment.
     */
    protected open fun setup() {}

    /**
     * A helper method to log a value.
     *
     * @param value The value to be logged.
     */
    protected fun log(value: Any) {
        Log.e(TAG, value.toString())
    }

}