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
@BindingAdapter("app:chartData")
fun chartData(chart: PieChart, data: MutableMap<Pair<String,String>, Int>?) {

    val listWithoutDuplicates = data?.toList()?.distinctBy { it.first.second }?.toMap()
    listWithoutDuplicates?.forEach { (label, value) ->
        chart.addPieSlice(PieModel(label.second, value.toFloat(), getRandomColor()))
    }
    chart.startAnimation()
}