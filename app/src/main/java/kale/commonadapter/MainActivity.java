package kale.commonadapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import kale.adapter.CommonAdapter;
import kale.adapter.CommonRcvAdapter;
import kale.adapter.item.AdapterItem;
import kale.commonadapter.item.ButtonItem;
import kale.commonadapter.item.ImageItem;
import kale.commonadapter.item.TextItem;
import kale.commonadapter.model.DemoModel;
import kale.commonadapter.util.DataManager;


public class MainActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    private ListView listView;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);

        listView = (ListView) findViewById(R.id.listView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Button showListViewBtn = (Button) findViewById(R.id.showListView_button);

        showListViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setTitle("ListView的效果");
                listView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                
                if (listView.getAdapter() == null) {
                    addDataToListView(DataManager.loadData(getBaseContext()));
                } else {
                    ((CommonAdapter) listView.getAdapter()).setData(DataManager.loadData(getBaseContext()));
                    ((CommonAdapter) listView.getAdapter()).notifyDataSetChanged();
                } 
            }
        });

        Button showRecyclerViewBtn = (Button) findViewById(R.id.showRecyclerView_button);
        showRecyclerViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setTitle("RecyclerView的效果");
                recyclerView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);

                if (recyclerView.getAdapter() == null) {
                    addDataToRecyclerView(DataManager.loadData(getBaseContext()));
                } else {
                    if (recyclerView.getAdapter() instanceof CommonRcvAdapter) {
                        ((CommonRcvAdapter<DemoModel>) recyclerView.getAdapter()).setData(DataManager.loadData(getBaseContext()));
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void addDataToListView(List<DemoModel> data) {
        listView.setAdapter(new CommonAdapter<DemoModel>(data, 3) {

            @Override
            public Object getItemType(DemoModel demoModel) {
                // Log.d(TAG, "getItemType = " + demoModel.getDataType());
                return demoModel.type;
            }

            @NonNull
            @Override
            public AdapterItem<DemoModel> createItem(Object type) {
                Log.d(TAG, "createItem " + type + " view");
                return initItem(type);
            }
        });
    }

    private void addDataToRecyclerView(List<DemoModel> data) {
        //GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setRecycleChildrenOnDetach(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CommonRcvAdapter<DemoModel>(data) {

            @Override
            public Object getItemType(DemoModel item) {
                return item.type;
            }

            @NonNull
            @Override
            public AdapterItem<DemoModel> createItem(Object type) {
                Log.d(TAG, "createItem " + type + " view");
                return initItem(type);
            }
        });
    }
    
    private AdapterItem<DemoModel> initItem(Object type) {
       switch ((String) type) {
            case "text":
                return new TextItem();
            case "button":
                return new ButtonItem();
            case "image":
                return new ImageItem();
            default:
                Log.e(TAG, "No default item");
                return new TextItem();
        }
    }
    
    
}
