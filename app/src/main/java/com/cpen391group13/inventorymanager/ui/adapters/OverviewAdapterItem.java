package com.cpen391group13.inventorymanager.ui.adapters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Logan on 2018-03-27.
 */

public class OverviewAdapterItem {
    private List<CategoryAdapterItem> categoryAdapterItems;
    private String warehouseLocation;
    private int warehouseId;

    public OverviewAdapterItem(int warehouseId, String warehouseLocation){
        this.warehouseId = warehouseId;
        this.warehouseLocation = warehouseLocation;
        categoryAdapterItems = new ArrayList<CategoryAdapterItem>();
    }

    public void addListItem(String category, int categoryId){
        categoryAdapterItems.add(new CategoryAdapterItem(category, categoryId, warehouseId));
    }

    public int getWarehouseId(){
        return warehouseId;
    }

    public CategoryAdapterItem getCategoryAdapterItem(int i) {
        return categoryAdapterItems.get(i);
    }

    public String getWarehouseLocation(){
        return warehouseLocation;
    }
}
