@file:Suppress("UNCHECKED_CAST")

package com.hub.wifianalysis.ui.util

import android.view.LayoutInflater
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hub.wifianalysis.R
import com.hub.wifianalysis.ui.base.BaseAdapter
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.charts.ValueLineChart
import org.eazegraph.lib.models.PieModel
import org.eazegraph.lib.models.ValueLinePoint
import org.eazegraph.lib.models.ValueLineSeries

/**
 * Binding adapter to set items to a RecyclerView.
 *
 * @param view The RecyclerView to set the items to.
 * @param items The list of items to set to the RecyclerView.
 */
@BindingAdapter(value = ["app:items"])
fun <T> setRecyclerItems(view: RecyclerView, items: List<T>?) {
    (view.adapter as BaseAdapter<T>?)?.setItems(items ?: emptyList())
}

/**
 * Binding adapter to show a view when a list is empty.
 *
 * @param view The view to show or hide.
 * @param list The list to check if it's empty.
 */
@BindingAdapter(value = ["app:showWhenEmptyList"])
fun <T> showWhenEmptyList(view: View, list: List<T>?) {
    if (list.isNullOrEmpty()) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

/**
 * Binding adapter to hide a view when a list is empty.
 *
 * @param view The view to show or hide.
 * @param listSize The size of the list to check if it's empty.
 */
@BindingAdapter(value = ["app:hideWhenEmptyList"])
fun hideWhenEmptyList(view: View, listSize: Int) {
    if (listSize == 0) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

/**
 * Binding adapter to show or hide a view based on a condition.
 *
 * @param view The view to show or hide.
 * @param show The condition to check.
 */
@BindingAdapter("app:showIf")
fun showIf(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}

/**
 * Binding adapter to set data to a TableLayout.
 *
 * @param tableLayout The TableLayout to set the data to.
 * @param data The data to set to the TableLayout.
 */
@BindingAdapter("app:tableData")
fun tableData(tableLayout: TableLayout, data: MutableMap<Pair<String,String>, Int>?) {
    val inflater = LayoutInflater.from(tableLayout.context)

    data?.forEach { item ->
        val rowView = inflater.inflate(R.layout.layout_table, null) as TableRow
        val textView1 = rowView.findViewById<TextView>(R.id.textViewColumn)
        val textView2 = rowView.findViewById<TextView>(R.id.TextViewCount)

        textView1.text = item.key.second
        textView2.text = item.value.toString()

        tableLayout.addView(rowView)
    }
}

/**
 * Binding adapter to set data to a PieChart.
 *
 * @param chart The PieChart to set the data to.
 * @param data The data to set to the PieChart.
 */
@BindingAdapter("app:chartData")
fun chartData(chart: PieChart, data: MutableMap<Pair<String,String>, Int>?) {

    val listWithoutDuplicates = data?.toList()?.distinctBy { it.first.second }?.toMap()
    listWithoutDuplicates?.forEach { (label, value) ->
        chart.addPieSlice(PieModel(label.second, value.toFloat(), getRandomColor()))
    }
    chart.startAnimation()
}