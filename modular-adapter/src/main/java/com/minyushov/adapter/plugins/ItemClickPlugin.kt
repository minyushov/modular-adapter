package com.minyushov.adapter.plugins

interface ItemClickPlugin<I> {
  fun onItemClicked(item: I, position: Int)
}