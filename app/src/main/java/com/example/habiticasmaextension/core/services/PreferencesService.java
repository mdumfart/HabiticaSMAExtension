package com.example.habiticasmaextension.core.services;

import android.content.Context;

public interface PreferencesService {
    void put(String key, String value, Context ctx);
    String get(String key, Context ctx);
    void remove(String key, Context ctx);
}
