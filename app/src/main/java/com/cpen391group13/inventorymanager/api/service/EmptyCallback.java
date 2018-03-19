package com.cpen391group13.inventorymanager.api.service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Empty callback for POST requests to /controls endpoints
 */
public class EmptyCallback<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {}

    @Override
    public void onFailure(Call<T> call, Throwable t) {}
}
