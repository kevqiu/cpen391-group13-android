package com.cpen391group13.inventorymanager.api.service;


import com.cpen391group13.inventorymanager.api.models.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Logan on 3/15/2018.
 */

public interface ItemClient {
    @GET("/items")
    Call<List<Item>> getItems(@Query("category_id") String categoryId, @Query("warehouse_id") String warehouseId);
}
