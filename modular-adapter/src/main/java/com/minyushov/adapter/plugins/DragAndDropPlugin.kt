package com.minyushov.adapter.plugins

import com.minyushov.adapter.Direction

interface DragAndDropPlugin<I> {
  @Direction
  val dragDirections: Int

  fun onMoved(item: I, fromPosition: Int, toPosition: Int)
}