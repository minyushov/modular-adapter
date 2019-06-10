package com.minyushov.adapter

import androidxx.recyclerview.widget.AsyncDifferConfig
import androidxx.recyclerview.widget.AsyncListDiffer
import androidxx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView

class DiffDataSource<I : ModularItem> : ModularAdapter.DataSource<I> {
  private lateinit var adapter: ModularAdapter<*, I>
  private lateinit var differ: AsyncListDiffer<I>

  private val config: AsyncDifferConfig<I> = AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<I>() {
    override fun areItemsTheSame(oldItem: I, newItem: I) =
      adapter.areItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: I, newItem: I) =
      adapter.areContentsTheSame(oldItem, newItem)

    override fun getChangePayload(oldItem: I, newItem: I) =
      adapter.getChangePayload(oldItem, newItem)
  }).build()

  override fun <VH : RecyclerView.ViewHolder> setAdapter(adapter: ModularAdapter<VH, I>) {
    this.adapter = adapter
    this.differ = AsyncListDiffer(AdapterListUpdateCallback(adapter), config)
  }

  override fun submitItems(items: List<I>) =
    differ.submitList(items)

  override fun submitItems(items: List<I>, completion: () -> Unit) =
    differ.submitList(items, completion)

  override fun getItems(): List<I> =
    differ.currentList

  override fun getItemCount(): Int =
    differ.currentList.size

  override fun getItem(position: Int): I =
    differ.currentList[position]

  override fun moveItem(fromPosition: Int, toPosition: Int) =
    throw UnsupportedOperationException("Item movements are not supported by the DiffDataSource")
}
