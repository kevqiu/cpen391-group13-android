package com.cpen391group13.inventorymanager.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

/**
 * Created by Logan on 2018-03-20.
 */

public class ItemFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.item_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_item) SwipeRefreshLayout swipeRefreshLayout;

    private int warehouse_id;
    private int category_id;
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

        swipeRefreshLayout.setOnRefreshListener(this);

        getActivity().setTitle("Items");

        Bundle bundle = getArguments();
        warehouse_id = bundle.getInt("warehouse_id");
        category_id = bundle.getInt("category_id");

        Retrofit retrofit = RetrofitClient.getClient(this.getContext());

        itemClient = retrofit.create(ItemClient.class);

        fetchItems();

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onRefresh(){
        fetchItems();
    }

    private void fetchItems(){
        Call<List<Item>> call;
        if(category_id != 0){
            call = itemClient.getItems(String.valueOf(category_id), String.valueOf(warehouse_id));
        }
        else{
            call = itemClient.getItems(null, String.valueOf(warehouse_id));
        }
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

        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }

    }

}
