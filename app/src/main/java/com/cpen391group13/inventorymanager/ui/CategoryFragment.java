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
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.models.Category;
import com.cpen391group13.inventorymanager.api.models.Item;
import com.cpen391group13.inventorymanager.api.service.CategoryClient;
import com.cpen391group13.inventorymanager.api.service.ItemClient;
import com.cpen391group13.inventorymanager.helpers.PreferencesHelper;
import com.cpen391group13.inventorymanager.ui.adapters.CategoryAdapter;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Fragment for category view
 *
 * TODO: Add communication, make CategoryClient, update toolbar
 *
 */
public class CategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.category_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_category) SwipeRefreshLayout swipeRefreshLayout;

    private int warehouse_id;
    private Retrofit.Builder builder;
    private Retrofit retrofit;
    private CategoryClient client;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(this);

        Bundle bundle = getArguments();
        warehouse_id = bundle.getInt("warehouse_id");

        //categoryText.setText("Category view opened on " + warehouse_id);

        builder = new Retrofit.Builder()
                .baseUrl(PreferencesHelper.getServerPath(this.getContext()))
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();

        client = retrofit.create(CategoryClient.class);

        fetchCategories();

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onRefresh() {
        fetchCategories();
    }

    //TODO: Create CategoryAdapterItem class under adapters that takes a category
    //TODO: increment category count, getter for category and for category count
    private void fetchCategories(){
        Call<List<Category>> call = client.getCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()) {
                    List<Category> categories = response.body();

                    recyclerView.setAdapter(new CategoryAdapter(getActivity(), categories));
                }
                else{
                    Toast.makeText(getActivity(), "AHHHHHHHH :(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getActivity(), "error :(", Toast.LENGTH_SHORT).show();
            }
        });
        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);

        Log.d("LOAD", "Got categories to load");
    }
}
