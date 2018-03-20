package com.cpen391group13.inventorymanager.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.models.Item;
import com.cpen391group13.inventorymanager.ui.ImageFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Logan on 2018-03-20.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context context;
    private List<Item> values;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.item_text) TextView itemName;

        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            Item item = values.get(getAdapterPosition());
            ImageFragment imageFrag = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("item_id", item.getId());
            imageFrag.setArguments(bundle);
            ((Activity) context).getFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_layout, imageFrag)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public ItemAdapter(Context context, List<Item> values) {
        this.context = context;
        this.values = values;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.item_recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Item item = values.get(position);
        Log.d("Item Category:", String.valueOf(item.getCategoryId()));
        Log.d("Item Id", String.valueOf(item.getId()));
        Log.d("Item Warehouse", String.valueOf(item.getWarehouseId()));

        TextView itemName = holder.itemName;
        itemName.setText(String.valueOf(item.getId()));
    }

    @Override
    public int getItemCount(){
        return values.size();
    }
}
