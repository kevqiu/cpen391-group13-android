package com.cpen391group13.inventorymanager.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.models.Warehouse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        Warehouse item = values.get(position);

        TextView warehouseNameText = view.findViewById(R.id.warehouse_name);
        TextView warehouseLatitudeText = view.findViewById(R.id.warehouse_latitude);
        TextView warehouseLongitudeText = view.findViewById(R.id.warehouse_longitude);

        warehouseNameText.setText("Location: " + item.getLocation());
        warehouseLatitudeText.setText("Latitude: " + String.valueOf(item.getLatitude()));
        warehouseLongitudeText.setText("Longitude: " + String.valueOf(item.getLongitude()));

        return view;
    }
}
