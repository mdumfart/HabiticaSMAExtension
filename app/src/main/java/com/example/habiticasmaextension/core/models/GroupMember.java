package com.example.habiticasmaextension.core.models;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.habiticasmaextension.R;
import com.example.habiticasmaextension.core.proxy.HabiticaProxyFactory;
import com.example.habiticasmaextension.ui.partymembers.PartyMembersAdapter;

import java.io.IOException;
import java.util.List;

public class GroupMember {
    public String username;
    public String userId;
    private String apiKey;
    public Stats stats;

    public GroupMember(String username, String userId, String apiKey) {
        this.username = username;
        this.userId = userId;
        this.apiKey = apiKey;
    }

    public void setApiKey(String value) {
        apiKey = value;
    }

    public String getApiKey() {
        return apiKey;
    }
}
