package com.example.habiticasmaextension.core.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class PreferencesServiceImpl implements PreferencesService {

    @Override
    public void put(String key, String value, Context ctx) {
        SharedPreferences pref = getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(key, value);

        editor.commit();
    }

    @Override
    public String get(String key, Context ctx) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);

        return pref.getString(key, "");
    }

    @Override
    public void remove(String key, Context ctx) {
        SharedPreferences pref = getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = pref.edit();

        editor.remove(key);

        editor.commit();
    }
}
