package com.cpen391group13.inventorymanager.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.models.Warehouse;
import com.cpen391group13.inventorymanager.ui.LocationFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import butterknife.*;

/**
 * Created by Logan on 2018-03-08.
 */

public class WarehouseAdapter extends RecyclerView.Adapter<WarehouseAdapter.ViewHolder> {

    private Context context;
    private List<Warehouse> values;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(com.cpen391group13.inventorymanager.R.id.warehouse_text) TextView warehouseName;
        @BindView(R.id.gps_text) TextView warehouseLocation;
        // each data item is just a string in this case
        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            Warehouse item = values.get(getAdapterPosition());

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
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public WarehouseAdapter(Context context, List<Warehouse> values) {
        // TODO: Not sure what this is for
        // super(context, R.layout.warehouse_recycler_view, values);

        this.context = context;
        this.values = values;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WarehouseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // create a new view
        View v = inflater.inflate(R.layout.warehouse_recycler_view, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        Warehouse item = values.get(position);

        // - replace the contents of the view with that element
        TextView warehouseName = holder.warehouseName;
        warehouseName.setText(item.getLocation());

        TextView warehouseLocation = holder.warehouseLocation;
        warehouseLocation.setText("(" + item.getLatitude() + "," + item.getLongitude() + ")");

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}
