package com.cpen391group13.inventorymanager.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.models.Item;
import com.cpen391group13.inventorymanager.api.service.ItemClient;
import com.cpen391group13.inventorymanager.api.service.RetrofitClient;
import com.cpen391group13.inventorymanager.ui.adapters.ItemAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.cpen391group13.inventorymanager.helpers.WarehouseHelper.getWarehouseById;

/**
 * Created by Logan on 2018-03-20.
 */

public class ItemFragment extends Fragment{
    @BindView(R.id.item_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.item_fab) FloatingActionButton itemFab;

    private int warehouse_id;
    private int category_id;
    private String time_range;
    private ItemClient itemClient;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        ButterKnife.bind(this, view);

        itemFab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                fetchItems(true);
            }
        });

        Bundle bundle = getArguments();
        warehouse_id = bundle.getInt("warehouse_id");
        category_id = bundle.getInt("category_id");
        time_range = bundle.getString("time_range");

        getActivity().setTitle(getWarehouseById(warehouse_id) + "Items");

        Retrofit retrofit = RetrofitClient.getClient(this.getContext());

        itemClient = retrofit.create(ItemClient.class);

        fetchItems(false);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    private void fetchItems(boolean refresh){
        Call<List<Item>> call;
        String categoryQuery = category_id != 0 ? String.valueOf(category_id) : null;
        String warehouseQuery = warehouse_id != 0 ? String.valueOf(warehouse_id) : null;

        call = itemClient.getItems(categoryQuery, warehouseQuery, time_range);

        call.enqueue(new Callback<List<Item>>(){
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response){
                if (response.isSuccessful()){
                    List<Item> items = response.body();
                    recyclerView.setAdapter(new ItemAdapter(getActivity(), items));
                } else{
                    Toast.makeText(getActivity(), "AHHHHH :(", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Item>> call, Throwable t){
                Toast.makeText(getActivity(), "error :(", Toast.LENGTH_SHORT).show();
            }
        });
        if(refresh)
            Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_SHORT).show();
    }
}
