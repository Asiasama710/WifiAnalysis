package com.hub.wifianalysis.ui.base

import androidx.recyclerview.widget.DiffUtil

/**
 * BaseDiffUtil is a utility class that helps to calculate the difference between two lists.
 * It is used in conjunction with RecyclerView to efficiently update the items.
 *
 * @param T The type of the items in the list.
 * @param oldList The old list of items.
 * @param newList The new list of items.
 * @param checkIfSameItem A function that checks if two items are the same.
 * @param checkIfSameContent A function that checks if the content of two items is the same.
 */
open class BaseDiffUtil<T>(
    private val oldList: List<T>,
    private val newList: List<T>,
    private val checkIfSameItem: (oldItem: T, newItem: T) -> Boolean,
    private val checkIfSameContent: (oldItem: T, newItem: T) -> Boolean
) : DiffUtil.Callback() {

    /**
     * Returns the size of the old list.
     *
     * @return The size of the old list.
     */
    override fun getOldListSize(): Int = oldList.size

    /**
     * Returns the size of the new list.
     *
     * @return The size of the new list.
     */
    override fun getNewListSize(): Int = newList.size

    /**
     * Checks if two items are the same.
     *
     * @param oldItemPosition The position of the item in the old list.
     * @param newItemPosition The position of the item in the new list.
     * @return True if the items are the same, false otherwise.
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return checkIfSameItem(oldList[oldItemPosition], newList[newItemPosition])
    }

    /**
     * Checks if the content of two items is the same.
     *
     * @param oldItemPosition The position of the item in the old list.
     * @param newItemPosition The position of the item in the new list.
     * @return True if the content of the items is the same, false otherwise.
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return checkIfSameContent(oldList[oldItemPosition], newList[newItemPosition])
    }
}