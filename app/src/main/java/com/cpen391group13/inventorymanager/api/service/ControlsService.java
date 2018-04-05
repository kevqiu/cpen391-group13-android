package com.cpen391group13.inventorymanager.api.service;

import com.cpen391group13.inventorymanager.api.models.MLModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Kevin on 3/7/2018.
 */

public interface ControlsService {

    @POST("/controls/autosort")
    Call<Void> beginAutosortProcess();

    @POST("/controls/stop")
    Call<Void> stopProcess();

    @POST("/controls/position/{position}")
    Call<Void> setPosition(
            @Path("position") String position
    );

    @POST("/controls/model/{model}")
    Call<Void> setMLModel(
            @Path("model") String model
    );

    @GET("/controls/model")
    Call<MLModel> getCurrentModel();
}
