@file:Suppress("NOTHING_TO_INLINE")

package com.minyushov.adapter

import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.ParameterizedType

fun modularAdapter(
  dataSource: ModularAdapter.DataSource<ModularItem>,
  modules: Array<AdapterModule<*, *>>
): ModularAdapter<RecyclerView.ViewHolder, ModularItem> =
  ModularAdapter<RecyclerView.ViewHolder, ModularItem>().apply {
    modules.forEach { registerModule(it) }
    setDataSource(dataSource)
  }

inline fun listDataSource(): ListDataSource<ModularItem> =
  ListDataSource()

inline fun diffDataSource(): DiffDataSource<ModularItem> =
  DiffDataSource()


internal fun <T : ModularItem> Any.getGenericType(): Class<T> {
  var type: Class<*>? = javaClass
  while (type?.genericSuperclass !is ParameterizedType) {
    type = type?.superclass
  }

  val parameterizedType = type.genericSuperclass as ParameterizedType
  @Suppress("UNCHECKED_CAST")
  parameterizedType.actualTypeArguments.forEach { parameterType ->
    val parameterTypeClass = parameterType as? Class<T>
    if (parameterTypeClass != null && ModularItem::class.java.isAssignableFrom(parameterTypeClass)) {
      return parameterType
    }
  }

  throw IllegalArgumentException("Unable to find a subclass of ModularItem in type parameters of ${javaClass.name}")
}

internal fun <T> MutableList<T>.swap(from: Int, to: Int) {
  val tmp = this[from]
  this[from] = this[to]
  this[to] = tmp
}