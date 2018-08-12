package com.minyushov.adapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntDef
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.minyushov.adapter.plugins.DragAndDropPlugin
import com.minyushov.adapter.plugins.ItemClickPlugin
import com.minyushov.adapter.plugins.ItemLongClickPlugin
import com.minyushov.adapter.plugins.SwipePlugin

interface ModularItem

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

open class ModularAdapter<VH : RecyclerView.ViewHolder, I : ModularItem> : RecyclerView.Adapter<VH>() {
  interface DataSource<I : ModularItem> {
    fun <VH : RecyclerView.ViewHolder> setAdapter(adapter: ModularAdapter<VH, I>)
    fun submitItems(items: List<I>)
    fun getItems(): List<I>
    fun getItemCount(): Int
    fun getItem(position: Int): I
    fun moveItem(fromPosition: Int, toPosition: Int)
  }

  private lateinit var dataSource: DataSource<I>

  private val moduleManager = ModuleManager<AdapterModule<VH, I>, VH, I>()

  private val onClickListener = View.OnClickListener { view ->
    val recyclerView = view.parent as? RecyclerView
      ?: throw IllegalArgumentException("View $view is not a direct child of RecyclerView")

    val holder = recyclerView.getChildViewHolder(view)

    @Suppress("UNCHECKED_CAST")
    val module = moduleManager.getModule(holder.itemViewType) as? ItemClickPlugin<I>
      ?: return@OnClickListener

    holder
      .adapterPosition
      .takeIf { it != RecyclerView.NO_POSITION }
      ?.let { module.onItemClicked(dataSource.getItem(it), it) }
  }

  private val onLongClickListener = View.OnLongClickListener { view ->
    val recyclerView = view.parent as? RecyclerView
      ?: throw IllegalArgumentException("View $view is not a direct child of RecyclerView")

    val holder = recyclerView.getChildViewHolder(view)

    @Suppress("UNCHECKED_CAST")
    val module = moduleManager.getModule(holder.itemViewType) as? ItemLongClickPlugin<I>
      ?: return@OnLongClickListener false

    holder
      .adapterPosition
      .takeIf { it != RecyclerView.NO_POSITION }
      ?.let { module.onItemLongClicked(dataSource.getItem(it), it) }
      ?: false
  }

  fun registerModule(module: AdapterModule<*, *>) {
    @Suppress("UNCHECKED_CAST")
    this.moduleManager.registerModule(module as AdapterModule<VH, I>)
  }

  fun setDataSource(dataSource: DataSource<I>) {
    this.dataSource = dataSource
    this.dataSource.setAdapter(this)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
    moduleManager.getModule(viewType).onCreateViewHolder(parent)

  override fun onBindViewHolder(holder: VH, position: Int) =
    moduleManager.getModule(holder.itemViewType).onBindViewHolder(holder, dataSource.getItem(position))

  @JvmSuppressWildcards
  override fun onBindViewHolder(holder: VH, position: Int, payloads: List<Any>) =
    if (payloads.isEmpty()) {
      onBindViewHolder(holder, position)
    } else {
      moduleManager.getModule(holder.itemViewType).onBindViewHolder(holder, dataSource.getItem(position), payloads)
    }

  override fun onViewAttachedToWindow(holder: VH) {
    holder.itemView.setOnClickListener(onClickListener)
    holder.itemView.setOnLongClickListener(onLongClickListener)
    moduleManager.getModule(holder.itemViewType).onViewAttachedToWindow(holder)
  }

  override fun onViewDetachedFromWindow(holder: VH) {
    holder.itemView.setOnClickListener(null)
    holder.itemView.setOnLongClickListener(null)
    moduleManager.getModule(holder.itemViewType).onViewDetachedFromWindow(holder)
  }

  override fun onViewRecycled(holder: VH) =
    moduleManager.getModule(holder.itemViewType).onViewRecycled(holder)

  override fun getItemCount(): Int =
    dataSource.getItemCount()

  override fun getItemViewType(position: Int): Int =
    moduleManager.getViewType(dataSource.getItem(position))

  fun getItemSwipeDirs(position: Int): Int {
    val item = dataSource.getItem(position)
    @Suppress("UNCHECKED_CAST")
    val module = moduleManager.getModule(item) as? SwipePlugin<I>
    return module?.swipeDirections ?: 0
  }

  fun getDragDirs(position: Int): Int {
    val item = dataSource.getItem(position)
    @Suppress("UNCHECKED_CAST")
    val module = moduleManager.getModule(item) as? DragAndDropPlugin<I>
    return module?.dragDirections ?: 0
  }

  fun onDrag(fromPosition: Int, toPosition: Int): Boolean {
    val item = dataSource.getItem(fromPosition)
    @Suppress("UNCHECKED_CAST")
    val module = moduleManager.getModule(item) as? DragAndDropPlugin<I>
    if (module != null) {
      dataSource.moveItem(fromPosition, toPosition)
      module.onMoved(item, fromPosition, toPosition)
      return true
    }
    return false
  }

  fun onSwiped(position: Int, swipeDir: Int) {
    val item = dataSource.getItem(position)
    @Suppress("UNCHECKED_CAST")
    val module = moduleManager.getModule(item) as? SwipePlugin<I>
    module?.onSwiped(item, position, swipeDir)
  }

  fun areItemsTheSame(oldItem: I, newItem: I): Boolean {
    val oldItemModule = moduleManager.getModule(oldItem)
    val newItemModule = moduleManager.getModule(newItem)
    return oldItemModule === newItemModule && oldItemModule.areItemsTheSame(oldItem, newItem)
  }

  fun areContentsTheSame(oldItem: I, newItem: I): Boolean =
    moduleManager.getModule(oldItem).areContentsTheSame(oldItem, newItem)

  fun getChangePayload(oldItem: I, newItem: I): Any? =
    moduleManager.getModule(oldItem).getChangePayload(oldItem, newItem)
}