package com.minyushov.adapter.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidxx.recyclerview.widget.ViewHolder
import com.minyushov.adapter.AdapterModule
import com.minyushov.adapter.ItemTouchHelperCallback
import com.minyushov.adapter.ModularItem
import com.minyushov.adapter.listDataSource
import com.minyushov.adapter.modularAdapter
import com.minyushov.adapter.plugins.DragAndDropPlugin
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.a_main)

    val dataSource = listDataSource()
    val adapter = modularAdapter(
      dataSource = dataSource,
      modules = arrayOf(
        Module1(
          clickAction = { _, position ->
            Toast.makeText(this, "Clicked item from module 1 at position $position", Toast.LENGTH_SHORT).show()
          }
        ),
        Module2(
          clickAction = { _, position ->
            Toast.makeText(this, "Clicked item from module 2 at position $position", Toast.LENGTH_SHORT).show()
          }
        )
      )
    )
    val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this)

    ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(recyclerView)

    val items = ArrayList<ModularItem>(10)
    for (index in 0..9) {
      if (index % 2 == 0) {
        items.add(Module1.Item(index.toString()))
      } else {
        items.add(Module2.Item(index.toString()))
      }
    }

    dataSource.submitItems(items)
  }

  private class Module1(
    clickAction: (item: Item, position: Int) -> Unit
  ) : AdapterModule<ViewHolder<TextView>, Module1.Item>(
    clickAction
  ), DragAndDropPlugin<Module1.Item> {

    data class Item(val name: String) : ModularItem

    override val dragDirections: Int
      get() = ItemTouchHelper.DOWN or ItemTouchHelper.UP

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder<TextView> {
      val textView = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
      return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder<TextView>, item: Item) {
      holder.view.text = item.name
    }

    override fun onMoved(item: Item, fromPosition: Int, toPosition: Int) = Unit
  }

  private class Module2(
    clickAction: (item: Item, position: Int) -> Unit
  ) : AdapterModule<ViewHolder<TextView>, Module2.Item>(
    clickAction
  ), DragAndDropPlugin<Module2.Item> {

    data class Item(val name: String) : ModularItem

    override val dragDirections: Int
      get() = ItemTouchHelper.DOWN or ItemTouchHelper.UP

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder<TextView> {
      val textView = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
      return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder<TextView>, item: Item) {
      holder.view.text = item.name
    }

    override fun onMoved(item: Item, fromPosition: Int, toPosition: Int) = Unit
  }
}