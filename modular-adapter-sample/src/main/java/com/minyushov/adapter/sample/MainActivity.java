package com.minyushov.adapter.sample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.minyushov.adapter.AdapterModule;
import com.minyushov.adapter.ItemTouchHelperCallback;
import com.minyushov.adapter.ListDataSource;
import com.minyushov.adapter.ModularAdapter;
import com.minyushov.adapter.ModularItem;
import com.minyushov.adapter.plugins.DragAndDropPlugin;
import com.minyushov.adapter.plugins.ItemClickPlugin;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import msv.androidx.recyclerview.widget.ViewHolder;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.a_main);

    ModularAdapter.DataSource<ModularItem> dataSource = new ListDataSource<>();

    ModularAdapter<RecyclerView.ViewHolder, ModularItem> adapter = new ModularAdapter<>();
    adapter.registerModule(new Module1());
    adapter.registerModule(new Module2());
    adapter.setDataSource(dataSource);

    RecyclerView recyclerView = findViewById(R.id.recycler_view);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    new ItemTouchHelper(new ItemTouchHelperCallback(adapter)).attachToRecyclerView(recyclerView);

    List<ModularItem> items = new ArrayList<>(10);
    for (int index = 0; index < 10; index++) {
      if (index % 2 != 0) {
        items.add(new Item1(String.valueOf(index)));
      } else {
        items.add(new Item2(String.valueOf(index)));
      }
    }

    dataSource.submitItems(items);
  }

  private class Module1 extends AdapterModule<ViewHolder<TextView>, Item1> implements ItemClickPlugin<Item1>, DragAndDropPlugin<Item1> {
    @NonNull
    @Override
    public ViewHolder<TextView> onCreateViewHolder(@NonNull ViewGroup parent) {
      TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
      return new ViewHolder<>(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<TextView> holder, @NonNull Item1 item) {
      holder.getView().setText(item.name);
    }

    @Override
    public void onItemClicked(@NonNull Item1 item, int position) {
      Toast.makeText(MainActivity.this, "Clicked item from module 1 at position " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getDragDirections() {
      return ItemTouchHelper.DOWN | ItemTouchHelper.UP;
    }

    @Override
    public void onMoved(@NonNull Item1 item, int fromPosition, int toPosition) {
    }
  }

  private class Module2 extends AdapterModule<ViewHolder<TextView>, Item2> implements ItemClickPlugin<Item2>, DragAndDropPlugin<Item2> {
    @NonNull
    @Override
    public ViewHolder<TextView> onCreateViewHolder(@NonNull ViewGroup parent) {
      TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
      return new ViewHolder<>(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<TextView> holder, @NonNull Item2 item) {
      holder.getView().setText(item.name);
    }

    @Override
    public void onItemClicked(@NonNull Item2 item, int position) {
      Toast.makeText(MainActivity.this, "Clicked item from module 2 at position " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getDragDirections() {
      return ItemTouchHelper.DOWN | ItemTouchHelper.UP;
    }

    @Override
    public void onMoved(@NonNull Item2 item, int fromPosition, int toPosition) {

    }
  }

  private static class Item1 implements ModularItem {
    private final String name;

    Item1(String name) {
      this.name = name;
    }
  }

  private static class Item2 implements ModularItem {
    private final String name;

    Item2(String name) {
      this.name = name;
    }
  }
}