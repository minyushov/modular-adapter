package com.minyushov.adapter.x

import android.view.ViewGroup
import android.widget.Space
import com.minyushov.adapter.AdapterModule
import com.minyushov.support.recyclerview.ViewHolder

class OffsetModule : AdapterModule<ViewHolder<Space>, OffsetItem>() {
  override fun onCreateViewHolder(parent: ViewGroup) =
    ViewHolder(Space(parent.context))

  override fun onBindViewHolder(holder: ViewHolder<Space>, item: OffsetItem) {
    holder.view.minimumHeight = item.getSize(holder.view.context)
  }
}