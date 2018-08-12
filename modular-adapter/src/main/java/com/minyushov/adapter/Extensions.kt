package com.minyushov.adapter

import java.lang.reflect.ParameterizedType

internal fun <T> Any.getGenericType(): Class<T> {
  var type: Class<*>? = javaClass
  while (type?.genericSuperclass !is ParameterizedType) {
    type = type?.superclass
  }
  val parameterizedType = type.genericSuperclass as ParameterizedType
  @Suppress("UNCHECKED_CAST")
  return parameterizedType.actualTypeArguments[1] as Class<T>
}

internal fun <T> MutableList<T>.swap(from: Int, to: Int) {
  val tmp = this[from]
  this[from] = this[to]
  this[to] = tmp
}