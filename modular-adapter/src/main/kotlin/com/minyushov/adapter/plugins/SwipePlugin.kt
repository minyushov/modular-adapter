package com.minyushov.adapter.plugins

interface SwipePlugin<I> {
  @Direction
  val swipeDirections: Int

  fun onSwiped(item: I, position: Int, @Direction swipeDirection: Int)
}