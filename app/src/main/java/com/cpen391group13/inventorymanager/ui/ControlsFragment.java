package com.cpen391group13.inventorymanager.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.service.ControlsClient;
import com.cpen391group13.inventorymanager.helpers.PreferencesHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControlsFragment extends Fragment {
    //bind views
    @BindView(R.id.autosort_btn) Button autosortButton;
    @BindView(R.id.stop_btn) Button stopButton;

    private Retrofit.Builder builder;
    private Retrofit retrofit;
    private ControlsClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_controls, container, false);
        ButterKnife.bind(this, view);

        builder = new Retrofit.Builder()
                .baseUrl(PreferencesHelper.getServerPath(this.getContext()));

        retrofit = builder.build();

        client = retrofit.create(ControlsClient.class);

        autosortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("hello", "hello");
                client.beginAutosortProcess();
            }
        });

        return view;
    }
}
