package com.cpen391group13.inventorymanager.api.client;

import android.util.Log;
import android.widget.Toast;

import com.cpen391group13.inventorymanager.api.models.Warehouse;
import com.cpen391group13.inventorymanager.api.service.WarehouseService;
import com.cpen391group13.inventorymanager.ui.adapters.WarehouseAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Logan on 3/22/2018.
 */

public class WarehouseClient {
    private List<Warehouse> warehouses;

    public WarehouseClient(){
        this.warehouses = null;
    }

    public List<Warehouse> fetchWarehouses(WarehouseService service, Callback<List<Warehouse>> callback){
        Call<List<Warehouse>> call = service.getWarehouses();
        call.enqueue(new Callback<List<Warehouse>>() {
            @Override
            public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
                if (response.isSuccessful()) {
                    List<Warehouse> warehouses = response.body();
                } else {
                    //Toast.makeText(getActivity(), "AHHHHHHHH :(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Warehouse>> call, Throwable t) {
                //Toast.makeText(getActivity(), "error :(", Toast.LENGTH_SHORT).show();
            }
        });
        return ;
    }
}
