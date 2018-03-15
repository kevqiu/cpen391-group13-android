package com.cpen391group13.inventorymanager.api.service;


import com.cpen391group13.inventorymanager.api.models.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
/**
 * Created by Logan on 3/15/2018.
 */

public interface CategoryClient {
    @GET("/categories")
    Call<List<Category>> getCategories();
}
