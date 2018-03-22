package com.cpen391group13.inventorymanager.ui.adapters;

public class CategoryAdapterItem {
    private String category;
    private int categoryCount;
    private int categoryId;
    private int warehouseId;

    public CategoryAdapterItem(String category, int categoryId, int warehouseId){
        this.category = category;
        this.categoryId = categoryId;
        this.warehouseId = warehouseId;
        this.categoryCount = 0;
    }

    public void incrementCategoryCount(){
        this.categoryCount++;
    }

    public void setCategoryCount(int count){
        this.categoryCount = count;
    }

    public String getCategory(){
        return category;
    }

    public int getCategoryId() { return categoryId; }

    public int getWarehouseId() { return warehouseId; }

    public int getCategoryCount(){
        return categoryCount;
    }
}
