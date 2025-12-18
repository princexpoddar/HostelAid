package com.example.hostelaid;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Simple session helper to persist minimal user info across launches
 * so sync requests can be scoped per user/device.
 */
public class SessionManager {
    private static final String PREFS_NAME = "HostelAidSession";
    private static final String KEY_EMAIL = "email";

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveEmail(String email) {
        prefs.edit().putString(KEY_EMAIL, email).apply();
    }

    public String getEmail() {
        return prefs.getString(KEY_EMAIL, "");
    }
}

