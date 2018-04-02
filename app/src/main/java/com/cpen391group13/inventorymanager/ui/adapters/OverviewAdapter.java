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
import android.widget.RelativeLayout;
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
        @BindView(R.id.overview_relative_layout) RelativeLayout relativeLayout;
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
        //Log.d("VALUES:", values.get(0).getWarehouseLocation() + String.valueOf(values.get(1).getCategoryAdapterItem(1).getCategoryCount()));
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
        RelativeLayout relativeLayout = holder.relativeLayout;
        warehouseText.setText(item.getWarehouseLocation());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, R.id.overview_warehouse_text);

        //TODO: Make sure this is under the correct item
        for(CategoryAdapterItem categoryAdapterItem : item.getCategoryAdapterItems()){
            final TextView categoryTitle = new TextView(context);
            final TextView categoryCount = new TextView(context);
            categoryTitle.setText(categoryAdapterItem.getCategory());
            categoryCount.setText(categoryAdapterItem.getCategoryCount());
            relativeLayout.addView(categoryTitle, params);
            relativeLayout.addView(categoryCount, params);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}
