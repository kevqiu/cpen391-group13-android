package com.cpen391group13.inventorymanager.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.models.Warehouse;
import com.cpen391group13.inventorymanager.api.service.WarehouseClient;
import com.cpen391group13.inventorymanager.ui.adapters.WarehouseAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestFragment extends Fragment {
    @BindView(R.id.test_list) ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this, view);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.107:5000")
                .addConverterFactory(GsonConverterFactory.create());

        final Retrofit retrofit = builder.build();

        WarehouseClient client = retrofit.create(WarehouseClient.class);
        Call<List<Warehouse>> call = client.getWarehouses();

        call.enqueue(new Callback<List<Warehouse>>() {
            @Override
            public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
                List<Warehouse> warehouses = response.body();

                listView.setAdapter(new WarehouseAdapter(getActivity(), warehouses));
            }

            @Override
            public void onFailure(Call<List<Warehouse>> call, Throwable t) {
                Toast.makeText(getActivity(), "error :(", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
