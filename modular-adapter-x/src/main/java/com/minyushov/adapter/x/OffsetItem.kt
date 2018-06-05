package com.minyushov.adapter.x

import android.content.Context
import android.support.annotation.DimenRes
import android.support.annotation.Px
import com.minyushov.adapter.ModularItem

class OffsetItem(
  @DimenRes
  private val size: Int
) : ModularItem {

  @Px
  internal fun getSize(context: Context): Int =
    context.resources.getDimensionPixelOffset(size)

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as OffsetItem

    if (size != other.size) return false

    return true
  }

  override fun hashCode() = size
}