package com.example.habiticasmaextension.core.proxy;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Proxy_GroupChat {
    public boolean success;
    public List<ChatData> data;
    public List<Notification> notifications;
    public int userV;
    public String appVersion;
}

class ChatData{
    public int flagCount;
    public String _id;
    public Flags flags;
    public String id;
    public String text;
    public String unformattedText;
    public Info info;
    public Object timestamp;
    public Likes likes;
    public String client;
    public String uuid;
    public Contributor contributor;
    public Backer backer;
    public String user;
    public String username;
    public String groupId;
    public UserStyles userStyles;
    public String title;
    public List<String> items;
}

class Items{
    public int pixie;
    public int brownie;
    public int dryad;
    public int tracks;
    public int branches;
    public Gear gear;
    public String currentMount;
    public String currentPet;
}

class Info{
    public String type;
    public String user;
    public String quest;
    public String userDamage;
    public String bossDamage;
    public Items items;
    @SerializedName("class")
    public String klass;
    public String spell;
    public String target;
}

class Likes{
}

class Contributor{
}

class Backer{
}

class Costume{
    public String armor;
    public String head;
    public String shield;
    public String weapon;
    public String back;
    public String headAccessory;
    public String eyewear;
}

class Equipped{
    public String armor;
    public String head;
    public String shield;
    public String weapon;
    public String eyewear;
    public String headAccessory;
    public String back;
}

class Gear{
    public Costume costume;
    public Equipped equipped;
}

class UserStyles{
    public Items items;
    public Preferences preferences;
    public Stats stats;
}


