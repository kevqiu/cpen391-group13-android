package com.cpen391group13.inventorymanager.ui.adapters;

public class CategoryAdapterItem {
    private String category;
    private int categoryCount;

    public CategoryAdapterItem(String category){
        this.category = category;
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

    public int getCategoryCount(){
        return categoryCount;
    }
}
