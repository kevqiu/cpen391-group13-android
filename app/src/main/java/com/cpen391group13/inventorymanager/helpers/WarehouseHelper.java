package com.cpen391group13.inventorymanager.helpers;

/**
 * Created by Logan on 2018-03-21.
 */

public class WarehouseHelper {
    public static String getWarehouseById(int id){
        switch (id) {
            case 1:
                return "UBC ";
            case 2:
                return "UofT ";
            case 3:
                return "McGill ";
            default:
                return "";
        }
    }
}
