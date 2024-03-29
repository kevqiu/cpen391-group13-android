package com.cpen391group13.inventorymanager.ui;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.models.Warehouse;
import com.cpen391group13.inventorymanager.api.service.RetrofitService;
import com.cpen391group13.inventorymanager.api.service.WarehouseService;
import com.cpen391group13.inventorymanager.ui.adapters.WarehouseAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Fragment for the warehouse view
 *
 * Adapted from https://developer.android.com/samples/RecyclerView/src/com.example.android.recyclerview/RecyclerViewFragment.html
 */
public class WarehouseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.warehouse_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_warehouse) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.warehouse_progress_bar) ProgressBar warehouseProgressBar;

    private WarehouseService service;
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

        getActivity().setTitle("Warehouses");

        warehouseProgressBar.setVisibility(View.VISIBLE);

        swipeRefreshLayout.setOnRefreshListener(this);

        Retrofit retrofit = RetrofitService.getClient(this.getContext());
        service = retrofit.create(WarehouseService.class);

        refreshWarehouses();

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onRefresh() {
        refreshWarehouses();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void refreshWarehouses() {
        Call<List<Warehouse>> call = service.getWarehouses();
        call.enqueue(new Callback<List<Warehouse>>() {
            @Override
            public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
                if (response.isSuccessful()) {
                    warehouseProgressBar.setVisibility(View.INVISIBLE);
                    List<Warehouse> warehouses = response.body();
                    recyclerView.setAdapter(new WarehouseAdapter(getActivity(), warehouses));
                } else {
                    Toast.makeText(getActivity(), "AHHHHHHHH :(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Warehouse>> call, Throwable t) {
                Toast.makeText(getActivity(), "error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
