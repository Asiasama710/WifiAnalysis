@file:Suppress("UNCHECKED_CAST")

package com.hub.wifianalysis.ui.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hub.wifianalysis.ui.base.BaseAdapter

@BindingAdapter(value = ["app:items"])
fun <T> setRecyclerItems(view: RecyclerView, items: List<T>?) {
    (view.adapter as BaseAdapter<T>?)?.setItems(items ?: emptyList())
}

@BindingAdapter(value = ["app:showWhenEmptyList"])
fun <T> showWhenEmptyList(view: View, list: List<T>?) {
    if (list.isNullOrEmpty()) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter(value = ["app:hideWhenEmptyList"])
fun hideWhenEmptyList(view: View, listSize: Int) {
    if (listSize == 0) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}
@BindingAdapter("app:showIf")
fun showIf(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}
