package com.cpen391group13.inventorymanager.ui;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.service.WarehouseClient;


/**
 * Fragment for category view
 */
public class CategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.category_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_category) SwipeRefreshLayout swipeRefreshLayout;

    private String location;
    private Retrofit.Builder builder;
    private Retrofit retrofit;
    private WarehouseClient client;
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
        location = bundle.getString("warehouse");

        builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.72:5000")
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();

//        client = retrofit.create(CategoryClient.class);

        refreshCategories();

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onRefresh() {
        refreshCategories();
    }

    private void refreshCategories(){

    }
}
