package com.minyushov.adapter

import android.support.v7.widget.RecyclerView

class ListDataSource<I : ModularItem> : ModularAdapter.DataSource<I> {
  private val items = mutableListOf<I>()

  private lateinit var adapter: RecyclerView.Adapter<*>

  override fun <VH : RecyclerView.ViewHolder> setAdapter(adapter: ModularAdapter<VH, I>) {
    this.adapter = adapter
  }

  override fun submitList(list: List<I>) {
    items.clear()
    items.addAll(list)
    adapter.notifyDataSetChanged()
  }

  override fun getItemCount(): Int =
    items.size

  override fun getItem(position: Int): I =
    items[position]

  override fun moveItem(fromPosition: Int, toPosition: Int) {
    items.swap(fromPosition, toPosition)
    adapter.notifyItemMoved(fromPosition, toPosition)
  }
}