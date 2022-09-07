package com.minyushov.adapter

import androidx.recyclerview.widget.RecyclerView

internal class ModuleManager<M : AdapterModule<VH, I>, VH : RecyclerView.ViewHolder, I : ModularItem> {
  private val viewTypes = mutableMapOf<Int, M>()
  private val itemTypes = mutableMapOf<Class<I>, M>()
  private val modules = mutableMapOf<M, Int>()

  fun registerModule(module: M) {
    val type = module.itemType

    if (itemTypes.containsKey(type)) {
      throw IllegalArgumentException("AdapterModule for type $type is already registered")
    }

    if (itemTypes.containsValue(module)) {
      throw IllegalArgumentException("AdapterModule $module is already registered")
    }

    itemTypes[type] = module
    viewTypes[viewTypes.size] = module
    modules[module] = modules.size
  }

  fun getModule(viewType: Int): M =
    viewTypes[viewType]
      ?: throw IllegalArgumentException("AdapterModule for type $viewType is not registered")

  fun getModule(item: I): M =
    itemTypes[item.javaClass]
      ?: throw IllegalArgumentException("AdapterModule for item type " + item.javaClass + " is not registered")

  fun getViewType(item: I): Int =
    itemTypes[item.javaClass]
      ?.let { modules[it] }
      ?: throw IllegalArgumentException("AdapterModule for item type " + item.javaClass + " is not registered")
}