package com.example.habiticasmaextension.core.models;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    public String userId;
    public String apiToken;
    public String username;
    public Stats stats;
    public String partyId;
    public List<String> questStrings;

    public User(String userId, String apiToken, String username, Stats stats, String partyId, List<String> questStrings) {
        this.userId = userId;
        this.apiToken = apiToken;
        this.username = username;
        this.stats = stats;
        this.partyId = partyId;
        this.questStrings = questStrings;
    }
}
