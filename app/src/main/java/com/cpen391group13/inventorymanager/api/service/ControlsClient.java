package com.cpen391group13.inventorymanager.api.service;

import com.cpen391group13.inventorymanager.api.models.Warehouse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Kevin on 3/7/2018.
 */

public interface ControlsClient {

    @POST("/controls/autosort")
    Call<Void> beginAutosortProcess();

    @POST("/controls/stop")
    Call<Void> stopProcess();
}
