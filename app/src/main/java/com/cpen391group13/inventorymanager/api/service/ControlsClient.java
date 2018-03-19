package com.cpen391group13.inventorymanager.api.service;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Kevin on 3/7/2018.
 */

public interface ControlsClient {

    @POST("/controls/autosort")
    Call<Void> beginAutosortProcess();

    @POST("/controls/stop")
    Call<Void> stopProcess();

    @POST("/controls/position/{position}")
    Call<Void> setPosition(
            @Path("position") String position
    );
}
