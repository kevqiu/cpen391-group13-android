package com.cpen391group13.inventorymanager.helpers;

import com.cpen391group13.inventorymanager.R;

/**
 * Created by Logan on 2018-03-21.
 */

public class CategoryHelper {
    public static int getGradientCategoryId(int id) {
        switch (id) {
            case 1:
                return R.drawable.red_gradient;
            case 2:
                return R.drawable.green_gradient;
            case 3:
                return R.drawable.blue_gradient;
            default:
                return R.drawable.other_gradient;
        }
    }

    public static String getCategoryById(int id) {
        switch (id) {
            case 1:
                return "Red";
            case 2:
                return "Green";
            case 3:
                return "Blue";
            default:
                return "Other";
        }
    }
}
