package com.minyushov.adapter.plugins

interface ItemLongClickPlugin<I> {
  fun onItemLongClicked(item: I, position: Int): Boolean
}