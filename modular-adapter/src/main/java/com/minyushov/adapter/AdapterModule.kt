package com.minyushov.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class AdapterModule<VH : RecyclerView.ViewHolder, I : ModularItem>(
  val clickAction: ((item: I, position: Int) -> Unit)? = null,
  val longClickAction: ((item: I, position: Int) -> Unit)? = null
) {
  open val itemType by lazy { getGenericType<I>() }

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