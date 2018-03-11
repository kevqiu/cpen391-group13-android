package com.cpen391group13.inventorymanager.ui.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.models.Warehouse;
import com.cpen391group13.inventorymanager.ui.LocationFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Kevin on 3/7/2018.
 */

public class WarehouseAdapter extends ArrayAdapter<Warehouse> {
    private Context context;
    private List<Warehouse> values;

    public WarehouseAdapter(Context context, List<Warehouse> values) {
        super(context, R.layout.list_item_pagination, values);

        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_pagination, parent, false);

        final Warehouse item = values.get(position);

        TextView warehouseNameText = view.findViewById(R.id.warehouse_name);
        TextView warehouseLatitudeText = view.findViewById(R.id.warehouse_latitude);
        TextView warehouseLongitudeText = view.findViewById(R.id.warehouse_longitude);

        warehouseNameText.setText("Location: " + item.getLocation());
        warehouseLatitudeText.setText("Latitude: " + String.valueOf(item.getLatitude()));
        warehouseLongitudeText.setText("Longitude: " + String.valueOf(item.getLongitude()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationFragment mapFrag = new LocationFragment();
                Bundle bundle = new Bundle();
                LatLng latlng = new LatLng(item.getLatitude(), item.getLongitude());
                bundle.putParcelable("latlng", latlng);
                bundle.putString("warehouse", item.getLocation());
                mapFrag.setArguments(bundle);

                ((Activity) context).getFragmentManager()
                        .beginTransaction()
                        .add(R.id.main_layout, mapFrag)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}
