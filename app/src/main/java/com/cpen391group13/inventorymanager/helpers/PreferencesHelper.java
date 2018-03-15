package com.cpen391group13.inventorymanager.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Kevin on 3/14/2018.
 */

public class PreferencesHelper {

    public static String getServerPath(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = sharedPrefs.getString("IP", null);
        String port = sharedPrefs.getString("Port", null);
        return ip != null && port != null ? String.format("http://%s:%s", ip, port) : null;
    }
}

