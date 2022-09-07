package com.minyushov.adapter.plugins

interface DragAndDropPlugin<I> {
  @Direction
  val dragDirections: Int

  fun onMoved(item: I, fromPosition: Int, toPosition: Int)
}