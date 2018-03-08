package com.cpen391group13.inventorymanager.services;

import java.util.ArrayList;

/**
 * Created by Kevin on 3/7/2018.
 */

public abstract class BaseService<T> {
    private String baseUrl;

    public BaseService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public ArrayList<T> getAll() {
        ArrayList<T> list = new ArrayList<T>();

        // jsonconvert(list -> listof<T>

        return list;
    }
}
