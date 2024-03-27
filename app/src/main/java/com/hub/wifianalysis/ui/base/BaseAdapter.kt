package com.hub.wifianalysis.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * BaseAdapter is an abstract class that provides a base implementation for a RecyclerView.Adapter.
 * It includes functionality for handling item updates using DiffUtil and data binding.
 *
 * @param T The type of the data in the list.
 * @param listener The listener that receives interaction events.
 */
abstract class BaseAdapter<T>(private val listener: BaseInteractionListener) :
    RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {

    // The current list of items
    private var items = emptyList<T>()

    // The layout resource ID for the item view
    abstract val layoutId: Int

    /**
     * Updates the current list with the new list.
     * It calculates the diff between the old and new list and dispatches the updates.
     *
     * @param newItems The new list of items.
     */
    open fun setItems(newItems: List<T>) {
        val diffResult =
            DiffUtil.calculateDiff(BaseDiffUtil(items, newItems, ::areItemsSame, ::areContentSame))
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * Checks if two items are the same.
     *
     * @param oldItem The item from the old list.
     * @param newItem The item from the new list.
     * @return True if the items are the same, false otherwise.
     */
    open fun areItemsSame(oldItem: T, newItem: T) = oldItem?.equals(newItem) == true

    /**
     * Checks if the content of two items is the same.
     *
     * @param oldPosition The item from the old list.
     * @param newPosition The item from the new list.
     * @return True if the content of the items is the same, false otherwise.
     */
    open fun areContentSame(oldPosition: T, newPosition: T) = true

    /**
     * Creates a new ViewHolder for the item view.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        )
    }

    /**
     * Binds the data to the ViewHolder.
     *
     * @param holder The ViewHolder to be updated.
     * @param position The position of the item in the data set.
     */
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val currentItem = items[position]
        when (holder) {
            is ItemViewHolder -> {
                holder.binding.apply {
                    setVariable(BR.item, currentItem)
                    setVariable(BR.listener, listener)
                }
            }
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int = items.size

    /**
     * ViewHolder for the item view.
     *
     * @param binding The ViewDataBinding for the item view.
     */
    class ItemViewHolder(val binding: ViewDataBinding) : BaseViewHolder(binding)

    /**
     * Base ViewHolder class.
     *
     * @param binding The ViewDataBinding for the item view.
     */
    abstract class BaseViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

}

/**
 * Interface for handling interaction events.
 */
interface BaseInteractionListener