package com.cpen391group13.inventorymanager.ui.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cpen391group13.inventorymanager.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Logan on 2018-03-27.
 */

public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.ViewHolder> {
    private static final int HEADER_VIEW = 1;
    private static final int LIST_ITEM_VIEW = 2;

    private Context context;
    private List<OverviewAdapterItem> values;
    private List<CategoryAdapterItem> listValues;

    // Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder{
        @Nullable@BindView(R.id.overview_warehouse_text) TextView warehouseText;
        @Nullable@BindView(R.id.overview_category_text) TextView categoryText;
        @Nullable@BindView(R.id.overview_category_count_text) TextView categoryCountText;
        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void bindViewList(int pos){
            pos -= 1;
            categoryText.setText(listValues.get(pos).getCategory());
            categoryCountText.setText(Integer.toString(listValues.get(pos).getCategoryCount()));
        }

        public void bindViewHeader(int pos){
            warehouseText.setText(values.get(pos).getWarehouseLocation());
        }
    }

    public class HeaderViewHolder extends OverviewAdapter.ViewHolder{
        public HeaderViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public class ListViewHolder extends OverviewAdapter.ViewHolder{
        public ListViewHolder(View v) {
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
        Log.d("Viewtype", Integer.toString(viewType));
        if (viewType == HEADER_VIEW){
            Log.d("block", "Header viewtype");
            View v = inflater.inflate(R.layout.overview_recycler_item, parent, false);
            return new OverviewAdapter.HeaderViewHolder(v);
        }
        else{
            Log.d("block", "List viewtype");
            View v = inflater.inflate(R.layout.overview_categories_recycler_item, parent, false);
            return new OverviewAdapter.ListViewHolder(v);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(OverviewAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        listValues = values.get(position).getCategoryAdapterItems();
        Log.d("values at", Integer.toString(position));
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder vh = (HeaderViewHolder) holder;
            vh.bindViewHeader(position);
            Log.d("Holder: ", "Header");
        } else if (holder instanceof ListViewHolder) {
            ListViewHolder vh = (ListViewHolder) holder;
            vh.bindViewList(position);
            Log.d("Holder: ", "List");
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

    @Override
    public int getItemViewType(int position){
        if (position == 0){
            Log.d("Get view type", "Header");
            return HEADER_VIEW;
        }
        else if (position > 0){
            Log.d("Get view type", "List");
            return LIST_ITEM_VIEW;
        }
        else {

            Log.d("Get view type", "Other");
            return super.getItemViewType(position);
        }
    }
}
