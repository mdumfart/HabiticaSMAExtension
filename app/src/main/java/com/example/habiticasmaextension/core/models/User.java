package com.example.habiticasmaextension.core.models;

import java.io.Serializable;

public class User implements Serializable {
    public String userId;
    public String apiToken;
    public String username;
    public Stats stats;
    public String partyId;

    public User(String userId, String apiToken, String username, Stats stats, String partyId) {
        this.userId = userId;
        this.apiToken = apiToken;
        this.username = username;
        this.stats = stats;
        this.partyId = partyId;
    }
}
