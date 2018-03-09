package com.cpen391group13.inventorymanager.api.models;

/**
 * Created by Kevin on 3/7/2018.
 */

public class Warehouse {
    private int id;
    private String location;
    private float latitude;
    private float longitude;


    public Warehouse(int id, String location, float latitude, float longitude) {
        this.id = id;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
