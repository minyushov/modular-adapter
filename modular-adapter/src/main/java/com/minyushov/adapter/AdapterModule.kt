package com.minyushov.adapter

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.ViewGroup

abstract class AdapterModule<VH : ViewHolder, I : ModularItem> {
  open val itemType = getGenericType<I>()

  abstract fun onCreateViewHolder(parent: ViewGroup): VH
  abstract fun onBindViewHolder(holder: VH, item: I)

  open fun onBindViewHolder(holder: VH, item: I, payloads: List<Any>) =
    onBindViewHolder(holder, item)

  open fun onViewAttachedToWindow(holder: VH) = Unit
  open fun onViewDetachedFromWindow(holder: VH) = Unit
  open fun onViewRecycled(holder: VH) = Unit

  open fun areItemsTheSame(oldItem: I, newItem: I): Boolean = false
  open fun areContentsTheSame(oldItem: I, newItem: I): Boolean = false
  open fun getChangePayload(oldItem: I, newItem: I): Any? = null
}