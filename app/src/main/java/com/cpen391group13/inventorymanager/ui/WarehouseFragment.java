package com.cpen391group13.inventorymanager.ui;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.models.Warehouse;
import com.cpen391group13.inventorymanager.api.service.RetrofitClient;
import com.cpen391group13.inventorymanager.api.service.WarehouseClient;
import com.cpen391group13.inventorymanager.helpers.PreferencesHelper;
import com.cpen391group13.inventorymanager.ui.adapters.WarehouseAdapter;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fragment for the warehouse view
 *
 * Adapted from https://developer.android.com/samples/RecyclerView/src/com.example.android.recyclerview/RecyclerViewFragment.html
 */
public class WarehouseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    //bind views
    @BindView(R.id.warehouse_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_warehouse) SwipeRefreshLayout swipeRefreshLayout;

    private Retrofit retrofit;
    private WarehouseClient client;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_warehouse, container, false);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(this);

        retrofit = RetrofitClient.getClient(this.getContext());
        client = retrofit.create(WarehouseClient.class);

        refreshWarehouses();

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onRefresh() {
        refreshWarehouses();
    }

    private void refreshWarehouses(){
        Call<List<Warehouse>> call = client.getWarehouses();
        call.enqueue(new Callback<List<Warehouse>>() {
            @Override
            public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
                List<Warehouse> warehouses = response.body();

                recyclerView.setAdapter(new WarehouseAdapter(getActivity(), warehouses));
            }

            @Override
            public void onFailure(Call<List<Warehouse>> call, Throwable t) {
                Toast.makeText(getActivity(), "error :(", Toast.LENGTH_SHORT).show();
            }
        });
        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);

        Log.d("LOAD", "Got warehosues to load");

    }
}
