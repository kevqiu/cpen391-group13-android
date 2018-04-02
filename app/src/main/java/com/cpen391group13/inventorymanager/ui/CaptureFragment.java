package com.cpen391group13.inventorymanager.ui;

import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.models.CaptureResponseBody;
import com.cpen391group13.inventorymanager.api.service.CaptureClient;
import com.cpen391group13.inventorymanager.api.service.RetrofitClient;
import com.cpen391group13.inventorymanager.helpers.CategoryHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CaptureFragment extends Fragment {
    @BindView(R.id.test_button) Button testButton;

    private CaptureClient client;
    private Location location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_capture, container, false);
        ButterKnife.bind(this, view);

        // Inflate the layout for this fragment
        getActivity().setTitle("Capture");

        Retrofit retrofit = RetrofitClient.getClient(this.getContext());
        client = retrofit.create(CaptureClient.class);

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
                RequestBody dtBody =
                        RequestBody.create(MediaType.parse("text/plain"), dt);
                RequestBody latBody =
                        RequestBody.create(MediaType.parse("text/plain"), String.valueOf(location.getLatitude()));
                RequestBody lngBody =
                        RequestBody.create(MediaType.parse("text/plain"), String.valueOf(location.getLongitude()));

                // add image to request body
                File dlDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File image = new File(dlDir.getAbsolutePath() + "/665.jpg");
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), image);
                MultipartBody.Part imageBody =
                        MultipartBody.Part.createFormData("file", image.getName(), requestFile);

                Call<CaptureResponseBody> call = client.mobileCapture(dtBody, latBody, lngBody, imageBody);
                call.enqueue(new Callback<CaptureResponseBody>() {
                    @Override
                    public void onResponse(Call<CaptureResponseBody> call, Response<CaptureResponseBody> response) {
                        if (response.isSuccessful()) {
                            String category = CategoryHelper.getCategoryById(response.body().getCategoryId());
                            Toast.makeText(getActivity(), "Capture Successful, Category = " + category, Toast.LENGTH_SHORT).show();
                            Log.d("Capture", response.message());
                        } else {
                            Toast.makeText(getActivity(), "Bad Request", Toast.LENGTH_SHORT).show();
                            try {
                                Log.d("Capture", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<CaptureResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(), "Capture Failed", Toast.LENGTH_SHORT).show();
                        Log.d("Capture", t.getLocalizedMessage());
                    }
                });
            }
        });

        return view;
    }
}
