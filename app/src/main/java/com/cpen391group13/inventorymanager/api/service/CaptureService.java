package com.cpen391group13.inventorymanager.api.service;

import com.cpen391group13.inventorymanager.api.models.CaptureResponseBody;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Kevin on 4/1/2018.
 */

public interface CaptureService {
    @Multipart
    @POST("/capture/mobile")
    Call<CaptureResponseBody> mobileCapture(
            @Part("datetime") RequestBody datetime,
                                            @Part("latitude") RequestBody latitude,
                                            @Part("longitude") RequestBody longitude,
                                            @Part MultipartBody.Part image);

}

