package com.cpen391group13.inventorymanager.api.models;

import com.google.gson.annotations.SerializedName;

public class CaptureResponseBody {
    @SerializedName("category_id")
    private int categoryId;
    @SerializedName("warehouse_id")
    private int warehouseId;
    private String message;

    public int getCategoryId() {
        return categoryId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public String getMessage() {
        return message;
    }
}
