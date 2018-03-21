package com.cpen391group13.inventorymanager.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Logan on 3/15/2018.
 */

public class Item {
    private int id;
    @SerializedName("category_id")
    private int categoryId;
    @SerializedName("warehouse_id")
    private int warehouseId;
    private Date datetime;

    public Item(int id, int categoryId, int warehouseId, Date datetime){
        this.id = id;
        this.categoryId = categoryId;
        this.warehouseId = warehouseId;
        this.datetime = datetime;
    }

    public int getId(){return id;}

    public int getCategoryId(){return categoryId;}

    public int getWarehouseId(){return warehouseId;}

    public Date getDatetime(){return datetime;}
}
