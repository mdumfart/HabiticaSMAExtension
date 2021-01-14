package com.example.habiticasmaextension.core.models;

public class GroupMember {
    public String username;
    public String userId;
    public String apiKey;

    public GroupMember(String username, String userId, String apiKey) {
        this.username = username;
        this.userId = userId;
        this.apiKey = apiKey;
    }
}
