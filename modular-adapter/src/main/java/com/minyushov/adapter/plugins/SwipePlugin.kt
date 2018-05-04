package com.minyushov.adapter.plugins

import com.minyushov.adapter.Direction

interface SwipePlugin<I> {
  @Direction
  val swipeDirections: Int

  fun onSwiped(item: I, position: Int, @Direction swipeDirection: Int)
}