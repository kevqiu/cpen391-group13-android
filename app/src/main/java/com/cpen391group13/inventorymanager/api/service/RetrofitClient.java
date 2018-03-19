package com.cpen391group13.inventorymanager.api.service;

import android.content.Context;

import com.cpen391group13.inventorymanager.helpers.PreferencesHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kevin on 3/19/2018.
 */

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static String lastBaseUrl = "";

    public static Retrofit getClient(Context context) {
        String baseUrl = PreferencesHelper.getServerPath(context);
        if (retrofit == null || !lastBaseUrl.equals(baseUrl)) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

