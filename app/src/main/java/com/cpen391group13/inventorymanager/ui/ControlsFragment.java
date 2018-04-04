package com.cpen391group13.inventorymanager.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.service.ControlsService;
import com.cpen391group13.inventorymanager.api.service.EmptyCallback;
import com.cpen391group13.inventorymanager.api.service.RetrofitService;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Retrofit;

public class ControlsFragment extends Fragment {
    //bind views
    @BindView(R.id.autosort_btn) Button autosortButton;
    @BindView(R.id.stop_btn) Button stopButton;
    @BindView(R.id.position_1_btn) Button pos1Button;
    @BindView(R.id.position_2_btn) Button pos2Button;
    @BindView(R.id.position_3_btn) Button pos3Button;
    @BindView(R.id.position_4_btn) Button pos4Button;
    @BindView(R.id.ml_dropdown) Spinner mlDropdown;

    private ControlsService client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_controls, container, false);
        ButterKnife.bind(this, view);

        getActivity().setTitle("Controls");

        Retrofit retrofit = RetrofitService.getClient(this.getContext());
        client = retrofit.create(ControlsService.class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.ml_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mlDropdown.setAdapter(adapter);
        
        autosortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Void> call = client.beginAutosortProcess();
                call.enqueue(new EmptyCallback<Void>());
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Void> call = client.stopProcess();
                call.enqueue(new EmptyCallback<Void>());
            }
        });

        pos1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Void> call = client.setPosition("1");
                call.enqueue(new EmptyCallback<Void>());
            }
        });

        pos2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Void> call = client.setPosition("2");
                call.enqueue(new EmptyCallback<Void>());
            }
        });

        pos3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Void> call = client.setPosition("3");
                call.enqueue(new EmptyCallback<Void>());
            }
        });

        pos4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Void> call = client.setPosition("4");
                call.enqueue(new EmptyCallback<Void>());
            }
        });

        mlDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String mlModel = (String) adapterView.getItemAtPosition(i);
                Call<Void> call = client.setMLModel(mlModel);
                call.enqueue(new EmptyCallback<Void>());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }
}
