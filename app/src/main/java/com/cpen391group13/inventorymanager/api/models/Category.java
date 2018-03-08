package com.cpen391group13.inventorymanager.api.models;

/**
 * Created by Kevin on 3/7/2018.
 */

public class Category {
    private int id;
    private String category;

    public Category(int id, String category) {
        this.id = id;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }
}
