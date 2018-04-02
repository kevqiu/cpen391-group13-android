package com.cpen391group13.inventorymanager.api.service;


import com.cpen391group13.inventorymanager.api.models.Cycle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface CycleClient {
    @GET("/cycles")
    Call<List<Cycle>> getCycles();

    @GET("/cycles/{id}")
    Call<Cycle> getCycle(@Path("id") String id);
}
