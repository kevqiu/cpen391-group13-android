package com.cpen391group13.inventorymanager.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.models.Category;
import com.cpen391group13.inventorymanager.api.models.Item;
import com.cpen391group13.inventorymanager.api.models.Warehouse;
import com.cpen391group13.inventorymanager.ui.CategoryFragment;
import com.cpen391group13.inventorymanager.ui.LocationFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Logan on 3/15/2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<CategoryAdapterItem> values;

    // Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(com.cpen391group13.inventorymanager.R.id.category_text) TextView categoryName;
        @BindView(R.id.category_count_text) TextView categoryCount;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            CategoryAdapterItem item = values.get(getAdapterPosition());
                //Log.d("Category pressed", item.getCategory());
                //Log.d("Count", String.valueOf(item.getCategoryCount()));
                /*
                CategoryFragment categoryFrag = new CategoryFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("warehouse_id", item.getId());
                categoryFrag.setArguments(bundle);
                ((Activity) context).getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_layout, categoryFrag)
                        .addToBackStack(null)
                        .commit();*/

        }
    }

    // Constructor for this dataset
    public CategoryAdapter(Context context, List<CategoryAdapterItem> values) {
        this.context = context;
        this.values = values;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // create a new view
        View v = inflater.inflate(R.layout.category_recycler_item, parent, false);
        return new CategoryAdapter.ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        String categoryText;
        // - get element from your dataset at this position
        CategoryAdapterItem item = values.get(position);
        categoryText = item.getCategory();
        categoryText = categoryText.substring(0,1).toUpperCase() + categoryText.substring(1);

        // - replace the contents of the view with that element
        TextView categoryName = holder.categoryName;
        categoryName.setText(categoryText);

        TextView categoryCount = holder.categoryCount;
        //categoryCount.setText("Count: " + String.valueOf(item.getCategoryCount()));
        categoryCount.setText("Count: " + String.valueOf(item.getCategoryCount()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}
