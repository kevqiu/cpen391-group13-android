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
import com.cpen391group13.inventorymanager.ui.ItemFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Logan on 2018-03-27.
 */

public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.ViewHolder> {
    @BindView(R.id.overview_warehouse_text) TextView warehouseText;
    @BindView(R.id.overview_all_text) TextView allText;
    @BindView(R.id.overview_all_count_text) TextView allCountText;
    @BindView(R.id.overview_red_text) TextView redText;
    @BindView(R.id.overview_red_count_text) TextView redCountText;
    @BindView(R.id.overview_green_text) TextView greenText;
    @BindView(R.id.overview_green_count_text) TextView greenCountText;
    @BindView(R.id.overview_blue_text) TextView blueText;
    @BindView(R.id.overview_blue_count_text) TextView blueCountText;
    private Context context;
    private List<OverviewAdapterItem> values;

    // Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    // Constructor for this dataset
    public OverviewAdapter(Context context, List<OverviewAdapterItem> values) {
        this.context = context;
        this.values = values;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OverviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // create a new view
        View v = inflater.inflate(R.layout.category_recycler_item, parent, false);
        return new OverviewAdapter.ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(OverviewAdapter.ViewHolder holder, int position) {
        String categoryText;
        // - get element from your dataset at this position
        OverviewAdapterItem item = values.get(position);
        warehouseText.setText(item.getWarehouseLocation());

        allText.setText(item.getCategoryAdapterItem(0).getCategory());
        allCountText.setText(item.getCategoryAdapterItem(0).getCategoryCount());

        redText.setText(item.getCategoryAdapterItem(1).getCategory());
        redCountText.setText(item.getCategoryAdapterItem(1).getCategoryCount());

        greenText.setText(item.getCategoryAdapterItem(2).getCategory());
        greenCountText.setText(item.getCategoryAdapterItem(2).getCategoryCount());

        blueText.setText(item.getCategoryAdapterItem(3).getCategory());
        blueCountText.setText(item.getCategoryAdapterItem(3).getCategoryCount());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}
