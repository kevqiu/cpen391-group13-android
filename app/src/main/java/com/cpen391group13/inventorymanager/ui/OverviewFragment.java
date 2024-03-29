package com.cpen391group13.inventorymanager.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.models.Category;
import com.cpen391group13.inventorymanager.api.models.Item;
import com.cpen391group13.inventorymanager.api.models.Warehouse;
import com.cpen391group13.inventorymanager.api.service.CategoryService;
import com.cpen391group13.inventorymanager.api.service.ItemService;
import com.cpen391group13.inventorymanager.api.service.RetrofitService;
import com.cpen391group13.inventorymanager.api.service.WarehouseService;
import com.cpen391group13.inventorymanager.ui.adapters.OverviewAdapter;
import com.cpen391group13.inventorymanager.ui.adapters.OverviewAdapterItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OverviewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.overview_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_overview) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.overview_progress_bar) ProgressBar overviewProgressBar;

    private WarehouseService warehouseService;
    private CategoryService categoryService;
    private ItemService itemService;
    private RecyclerView.LayoutManager layoutManager;
    private List<OverviewAdapterItem> overviewAdapterItems;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        ButterKnife.bind(this, view);

        // Inflate the layout for this fragment
        getActivity().setTitle("Overview");

        overviewProgressBar.setVisibility(View.VISIBLE);

        swipeRefreshLayout.setOnRefreshListener(this);

        Retrofit retrofit = RetrofitService.getClient(this.getContext());
        warehouseService = retrofit.create(WarehouseService.class);
        categoryService = retrofit.create(CategoryService.class);
        itemService = retrofit.create(ItemService.class);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        
        fetchOverview();

        return view;
    }

    @Override
    public void onRefresh() {
        fetchOverview();
    }

    private void fetchOverview() {
        Call<List<Warehouse>> call = warehouseService.getWarehouses();
        call.enqueue(new Callback<List<Warehouse>>() {
            @Override
            public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
                if (response.isSuccessful()) {
                    List<Warehouse> warehouses = response.body();
                    overviewAdapterItems = new ArrayList<>();
                    // Get each warehouse and create an OverviewAdapterItem for each
                    for (Warehouse warehouse : warehouses) {
                        OverviewAdapterItem overviewAdapterItem = new OverviewAdapterItem(warehouse.getId(), warehouse.getLocation());
                        overviewAdapterItems.add(overviewAdapterItem);
                    }
                    fetchCategories();
                } else {
                    Toast.makeText(getActivity(), "AHHHHHHHH :(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Warehouse>> call, Throwable t) {
                Toast.makeText(getActivity(), "error :(", Toast.LENGTH_SHORT).show();
            }
        });

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void fetchCategories() {
        Call<List<Category>> call = categoryService.getCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, final Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    List<Category> categories = response.body();
                    // Get each category and add a list item for that category
                    for (Category category : categories) {
                        for (OverviewAdapterItem overviewAdapterItem : overviewAdapterItems) {
                            overviewAdapterItem.addListItem(category.getCategory(), category.getId());
                        }
                    }
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
    }

    private void fetchItems() {
        Call<List<Item>> itemCall = itemService.getItems(null, null, null);
        itemCall.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> itemCall, Response<List<Item>> itemResponse) {
                if (itemResponse.isSuccessful()) {
                    overviewProgressBar.setVisibility(View.INVISIBLE);
                    List<Item> items = itemResponse.body();
                    // Get each item and for corresponding warehouse increment all count and specific category count
                    for (Item item : items) {
                        OverviewAdapterItem currentItem = overviewAdapterItems.get(item.getWarehouseId() - 1);
                        currentItem.getCategoryAdapterItems().get(0).incrementCategoryCount();
                        currentItem.getCategoryAdapterItems().get(item.getCategoryId()).incrementCategoryCount();
                    }
                    recyclerView.setAdapter(new OverviewAdapter(getActivity(), overviewAdapterItems));
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
