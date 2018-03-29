package com.cpen391group13.inventorymanager.ui;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.models.Category;
import com.cpen391group13.inventorymanager.api.models.Item;
import com.cpen391group13.inventorymanager.api.service.CategoryClient;
import com.cpen391group13.inventorymanager.api.service.ItemClient;
import com.cpen391group13.inventorymanager.api.service.RetrofitClient;
import com.cpen391group13.inventorymanager.ui.adapters.CategoryAdapter;
import com.cpen391group13.inventorymanager.ui.adapters.CategoryAdapterItem;

import java.util.ArrayList;
import java.util.List;

import static com.cpen391group13.inventorymanager.helpers.WarehouseHelper.getWarehouseById;


public class CategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.category_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_category) SwipeRefreshLayout swipeRefreshLayout;

    private int warehouse_id;
    private CategoryClient categoryClient;
    private ItemClient itemClient;
    private RecyclerView.LayoutManager layoutManager;
    List<CategoryAdapterItem> categoryAdapterItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(this);

        Bundle bundle = getArguments();
        warehouse_id = bundle.getInt("warehouse_id");

        getActivity().setTitle(getWarehouseById(warehouse_id) + "Categories");

        Retrofit retrofit = RetrofitClient.getClient(this.getContext());

        categoryClient = retrofit.create(CategoryClient.class);
        itemClient = retrofit.create(ItemClient.class);

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

    private void fetchCategories() {
        Call<List<Category>> call = categoryClient.getCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, final Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    final List<Category> categories = response.body();
                    categoryAdapterItems = new ArrayList<>();
                    // Add a 'all' category
                    categoryAdapterItems.add(new CategoryAdapterItem("all", 0, warehouse_id));
                    // Loop through categories and make a new CategoryAdapterItem for each
                    for (Category category : categories) {
                        CategoryAdapterItem categoryAdapterItem = new CategoryAdapterItem(category.getCategory(), category.getId(), warehouse_id);
                        categoryAdapterItems.add(categoryAdapterItem);
                    }
                    // Get items to be able to set the category count
                    fetchItems();
                } else {
                    Toast.makeText(getActivity(), "Category Response Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getActivity(), "Category error :(", Toast.LENGTH_SHORT).show();
            }
        });
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    private void fetchItems() {
        Call<List<Item>> itemCall = itemClient.getItems(null, String.valueOf(warehouse_id), null);
        itemCall.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> itemCall, Response<List<Item>> itemResponse) {
                if (itemResponse.isSuccessful()) {
                    List<Item> items = itemResponse.body();
                    // Set 'All' count
                    categoryAdapterItems.get(0).setCategoryCount(items.size());
                    // Loop through items and increment count for specific category
                    for (Item item : items) {
                        categoryAdapterItems.get(item.getCategoryId()).incrementCategoryCount();
                    }
                    recyclerView.setAdapter(new CategoryAdapter(getActivity(), categoryAdapterItems));
                } else {
                    Toast.makeText(getActivity(), "Item Response Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> itemCall, Throwable t) {
                Toast.makeText(getActivity(), "Item error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
