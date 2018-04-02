package com.cpen391group13.inventorymanager.ui.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpen391group13.inventorymanager.R;

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
        @BindView(R.id.overview_cardview) CardView overviewCardView;
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

        //RelativeLayout relativeLayout = holder.relativeLayout;
        warehouseText.setText(item.getWarehouseLocation());

        allText.setText(item.getCategoryAdapterItem(0).getCategory().toString() + ": ");
        allCountText.setText(Integer.toString(item.getCategoryAdapterItem(0).getCategoryCount()));

        String redTextString = item.getCategoryAdapterItem(1).getCategory();
        redTextString = redTextString.substring(0,1).toUpperCase() + redTextString.substring(1);
        redText.setText(redTextString + ": ");
        redCountText.setText(Integer.toString(item.getCategoryAdapterItem(1).getCategoryCount()));

        String greenTextString = item.getCategoryAdapterItem(2).getCategory();
        greenTextString = greenTextString.substring(0,1).toUpperCase() + greenTextString.substring(1);
        greenText.setText(greenTextString + ": ");
        greenCountText.setText(Integer.toString(item.getCategoryAdapterItem(2).getCategoryCount()));

        String blueTextString = item.getCategoryAdapterItem(3).getCategory();
        blueTextString = blueTextString.substring(0,1).toUpperCase() + blueTextString.substring(1);
        blueText.setText(blueTextString + ": ");
        blueCountText.setText(Integer.toString(item.getCategoryAdapterItem(3).getCategoryCount()));

        CardView overviewCardView = holder.overviewCardView;
        overviewCardView.setBackgroundResource(R.drawable.other_gradient);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}
