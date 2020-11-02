package com.filit.testweather.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(
    protected val items: List<T>
) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        items[position]?.let { holder.bind(it, position) }
    }

    protected fun createView(viewGroup: ViewGroup, viewRes: Int): View =
        LayoutInflater.from(viewGroup.context).inflate(viewRes, viewGroup, false)
}