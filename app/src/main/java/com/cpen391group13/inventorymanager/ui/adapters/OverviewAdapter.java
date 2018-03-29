package com.cpen391group13.inventorymanager.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.ui.adapters.OverviewAdapterItem;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Logan on 2018-03-27.
 */

public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.ViewHolder> {
    private Context context;
    private List<OverviewAdapterItem> values;

    // Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.overview_warehouse_text) TextView warehouseText;
        @BindView(R.id.overview_all_text) TextView allText;
        @BindView(R.id.overview_all_count_text) TextView allCountText;
        @BindView(R.id.overview_red_text) TextView redText;
        @BindView(R.id.overview_red_count_text) TextView redCountText;
        @BindView(R.id.overview_green_text) TextView greenText;
        @BindView(R.id.overview_green_count_text) TextView greenCountText;
        @BindView(R.id.overview_blue_text) TextView blueText;
        @BindView(R.id.overview_blue_count_text) TextView blueCountText;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    // Constructor for this dataset
    public OverviewAdapter(Context context, List<OverviewAdapterItem> values) {
        this.context = context;
        this.values = values;
        Log.d("VALUES:", values.get(0).getWarehouseLocation() + String.valueOf(values.get(1).getCategoryAdapterItem(1).getCategoryCount()));
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OverviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // create a new view
        View v = inflater.inflate(R.layout.overview_recycler_item, parent, false);
        return new OverviewAdapter.ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(OverviewAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        OverviewAdapterItem item = values.get(position);
        TextView warehouseText = holder.warehouseText;
        TextView allText = holder.allText;
        TextView redText = holder.redText;
        TextView greenText = holder.greenText;
        TextView blueText = holder.blueText;
        TextView allCountText = holder.allCountText;
        TextView redCountText = holder.redCountText;
        TextView greenCountText = holder.greenCountText;
        TextView blueCountText = holder.blueCountText;

        Log.d("Current item: ", "Location: " + item.getWarehouseLocation());
        warehouseText.setText(item.getWarehouseLocation());
        Log.d("TEXT", "Setting text");

        allText.setText(item.getCategoryAdapterItem(0).getCategory().toString());
        allCountText.setText(Integer.toString(item.getCategoryAdapterItem(0).getCategoryCount()));

        redText.setText(item.getCategoryAdapterItem(1).getCategory().toString());
        redCountText.setText(Integer.toString(item.getCategoryAdapterItem(1).getCategoryCount()));

        greenText.setText(item.getCategoryAdapterItem(2).getCategory().toString());
        greenCountText.setText(Integer.toString(item.getCategoryAdapterItem(2).getCategoryCount()));

        blueText.setText(item.getCategoryAdapterItem(3).getCategory().toString());
        blueCountText.setText(Integer.toString(item.getCategoryAdapterItem(3).getCategoryCount()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}
