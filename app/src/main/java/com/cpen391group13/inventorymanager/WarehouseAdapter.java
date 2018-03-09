package com.cpen391group13.inventorymanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cpen391group13.inventorymanager.api.models.Warehouse;

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
        @BindView(R.id.warehouse_text) TextView warehouseName;
        @BindView(R.id.gps_text) TextView warehouseLocation;
        // each data item is just a string in this case
        public ViewHolder(TextView v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            Log.v("Clicked","A tag has been clicked in warehouse view");
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
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.warehouse_recycler_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
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
