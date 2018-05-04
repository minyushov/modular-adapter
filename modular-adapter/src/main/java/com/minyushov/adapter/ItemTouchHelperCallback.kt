package com.minyushov.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

class ItemTouchHelperCallback(
  private val adapter: ModularAdapter<*, *>
) : ItemTouchHelper.SimpleCallback(0, 0) {

  override fun getMovementFlags(recyclerView: RecyclerView, holder: RecyclerView.ViewHolder): Int {
    val position = holder.adapterPosition
    return if (position == RecyclerView.NO_POSITION) {
      super.getMovementFlags(recyclerView, holder)
    } else {
      ItemTouchHelper.Callback.makeMovementFlags(adapter.getDragDirs(position), adapter.getItemSwipeDirs(position))
    }
  }

  override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) =
    adapter.onSwiped(viewHolder.adapterPosition, swipeDir)

  override fun onMove(recyclerView: RecyclerView, from: RecyclerView.ViewHolder, to: RecyclerView.ViewHolder): Boolean =
    adapter.onDrag(from.adapterPosition, to.adapterPosition)
}