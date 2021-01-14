package com.example.habiticasmaextension.core.proxy;

import java.util.List;

public class Proxy_GroupMembers {
    public boolean success;
    public List<UserInGroup> data;
    public List<Notification> notifications;
    public int userV;
    public String appVersion;
}

class Profile{
    public String name;
}

class UserInGroup{
    public String _id;
    public Auth auth;
    public Flags flags;
    public Profile profile;
    public String id;
    public String title;
    public List<String> items;
    public Group group;
}
