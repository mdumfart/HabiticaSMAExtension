package com.example.habiticasmaextension.core.proxy;

import java.util.List;
import java.util.Map;

public class Proxy_Quests {
    public boolean success;
    public QuestData data;
    public String appVersion;
}

class QuestData {
    public Map<String, Quest> quests;
}

class Quest {
    public String text;
    public String key;
}


