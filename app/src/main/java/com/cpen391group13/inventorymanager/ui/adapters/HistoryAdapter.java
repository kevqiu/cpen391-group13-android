package com.cpen391group13.inventorymanager.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.models.Cycle;
import com.cpen391group13.inventorymanager.ui.ItemFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Logan on 2018-03-08.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;
    private List<Cycle> values;

    // Provide a reference to the views for each data item

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.cycle_text) TextView cycleText;
        @BindView(R.id.time_text) TextView cycleTimeRange;
        @BindView(R.id.history_cardview) CardView warehouseCardView;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Cycle cycle = values.get(getAdapterPosition());
            ItemFragment itemFrag = new ItemFragment();
            Bundle bundle = new Bundle();
            String timeRange = cycle.getStartTimeString() + "," + cycle.getEndTimeString();
            bundle.putString("time_range", timeRange);
            itemFrag.setArguments(bundle);
            ((Activity) context).getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_layout, itemFrag)
                    .addToBackStack(null)
                    .commit();
        }
    }

    // Constructor for this dataset
    public HistoryAdapter(Context context, List<Cycle> values) {
        this.context = context;
        this.values = values;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // create a new view
        View v = inflater.inflate(R.layout.history_recycler_item, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        Cycle cycle = values.get(position);

        // - replace the contents of the view with that element
        TextView cycleText = holder.cycleText;
        cycleText.setText("Cycle " + cycle.getId());

        TextView cycleTimeRange = holder.cycleTimeRange;
        cycleTimeRange.setText("Time: " + cycle.getStartTimeString() + " - " + cycle.getEndTimeString());

        CardView warehouseCardView = holder.warehouseCardView;
        warehouseCardView.setBackgroundResource(R.drawable.other_gradient);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}
