package com.cpen391group13.inventorymanager.api.service;

import com.cpen391group13.inventorymanager.api.models.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Logan on 3/15/2018.
 */

public interface ItemService {
    @GET("/items")
    Call<List<Item>> getItems(@Query("category_id") String categoryId,
                              @Query("warehouse_id") String warehouseId,
                              @Query(value = "between", encoded=true) String timeRange);

    @GET("/items/{id}")
    Call<Item> getItem(@Path("id") String id);
}
