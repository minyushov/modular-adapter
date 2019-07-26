package com.minyushov.adapter.plugins

import androidx.annotation.IntDef
import androidx.recyclerview.widget.ItemTouchHelper

@IntDef(flag = true, value = [
  ItemTouchHelper.UP,
  ItemTouchHelper.DOWN,
  ItemTouchHelper.LEFT,
  ItemTouchHelper.RIGHT,
  ItemTouchHelper.START,
  ItemTouchHelper.END
])
@Retention(AnnotationRetention.SOURCE)
annotation class Direction