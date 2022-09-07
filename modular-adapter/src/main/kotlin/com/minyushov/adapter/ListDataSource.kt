package com.minyushov.adapter

import androidx.recyclerview.widget.RecyclerView

class ListDataSource<I : ModularItem> : ModularAdapter.DataSource<I> {
  private val _items = mutableListOf<I>()

  private lateinit var adapter: RecyclerView.Adapter<*>

  override fun <VH : RecyclerView.ViewHolder> setAdapter(adapter: ModularAdapter<VH, I>) {
    this.adapter = adapter
  }

  override fun submitItems(items: List<I>) {
    _items.clear()
    _items.addAll(items)
    adapter.notifyDataSetChanged()
  }

  override fun submitItems(items: List<I>, completion: () -> Unit) {
    submitItems(items)
    completion.invoke()
  }

  override fun getItems(): List<I> =
    _items

  override fun getItemCount(): Int =
    _items.size

  override fun getItem(position: Int): I =
    _items[position]

  override fun moveItem(fromPosition: Int, toPosition: Int) {
    _items.swap(fromPosition, toPosition)
    adapter.notifyItemMoved(fromPosition, toPosition)
  }
}