package com.example.habiticasmaextension.core.proxy;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import java.util.Map;

class Proxy_UserDetails {
    public boolean success;
    public Data data;
    public List<Notification> notifications;
    public String appVersion;
}

class Data {
    public Auth auth;
    public Achievements achievements;
    public Flags flags;
    public Party party;
    public Preferences preferences;
    public Stats stats;
}

class Achievements {
    Map<String, Integer> quests;
}

class Buffs{
    public int str;
    @SerializedName("int")
    public int ind;
    public int per;
    public int con;
    public int stealth;
    public boolean streaks;
    public boolean snowball;
    public boolean spookySparkles;
    public boolean shinySeed;
    public boolean seafoam;
}

class Training{
    @SerializedName("int")
    public int ind;
    public int per;
    public int str;
    public int con;
}

class Stats{
    public Buffs buffs;
    public Training training;
    public double hp;
    public double mp;
    public double exp;
    public double gp;
    public int lvl;
    @SerializedName("class")
    public String klass;
    public int points;
    public int str;
    public int con;
    @SerializedName("int")
    public int ind;
    public int per;
}

class Local{
    public String username;
    public String lowerCaseUsername;
    public String email;
}

class Timestamps{
    public Date created;
    public Date loggedin;
    public Date updated;
}

class Facebook{
}

class Google{
}

class Apple{
}

class Auth{
    public Local local;
    public Timestamps timestamps;
    public Facebook facebook;
    public Google google;
    public Apple apple;
}

class Tour{
    public int intro;
    public int classes;
    public int stats;
    public int tavern;
    public int party;
    public int guilds;
    public int challenges;
    public int market;
    public int pets;
    public int mounts;
    public int hall;
    public int equipment;
}

class Common{
    public boolean habits;
    public boolean dailies;
    public boolean todos;
    public boolean rewards;
    public boolean party;
    public boolean pets;
    public boolean gems;
    public boolean skills;
    public boolean classes;
    public boolean tavern;
    public boolean equipment;
    public boolean items;
    public boolean mounts;
    public boolean inbox;
    public boolean stats;
}

class Ios{
    public boolean addTask;
    public boolean editTask;
    public boolean deleteTask;
    public boolean filterTask;
    public boolean groupPets;
    public boolean inviteParty;
    public boolean reorderTask;
}

class Tutorial{
    public Common common;
    public Ios ios;
}

class LevelDrops{
    public boolean atom1;
    public boolean vice1;
    public boolean goldenknight1;
    public boolean moonstone1;
}

class Flags{
    public Tour tour;
    public Tutorial tutorial;
    public boolean customizationsNotification;
    public boolean showTour;
    public boolean dropsEnabled;
    public boolean itemsEnabled;
    public String lastNewStuffRead;
    public boolean rewrite;
    public boolean classSelected;
    public boolean rebirthEnabled;
    public int recaptureEmailsPhase;
    public int weeklyRecapEmailsPhase;
    public boolean communityGuidelinesAccepted;
    public int cronCount;
    public boolean welcomed;
    public boolean armoireEnabled;
    public boolean armoireOpened;
    public boolean armoireEmpty;
    public boolean cardReceived;
    public boolean warnedLowHealth;
    public boolean verifiedUsername;
    public boolean newStuff;
    public LevelDrops levelDrops;
    public Date lastWeeklyRecap;
}

class Collect{
}

class Progress{
    public double up;
    public double down;
    public int collectedItems;
    public Collect collect;
}

class Party{
    public Quest quest;
    public String _id;
}

class Hair{
    public String color;
    public int base;
    public int bangs;
    public int beard;
    public int mustache;
    public int flower;
}

class EmailNotifications{
    public boolean unsubscribeFromAll;
    public boolean newPM;
    public boolean kickedGroup;
    public boolean wonChallenge;
    public boolean giftedGems;
    public boolean giftedSubscription;
    public boolean invitedParty;
    public boolean invitedGuild;
    public boolean questStarted;
    public boolean invitedQuest;
    public boolean importantAnnouncements;
    public boolean weeklyRecaps;
    public boolean onboarding;
    public boolean majorUpdates;
    public boolean subscriptionReminders;
}

class PushNotifications{
    public boolean unsubscribeFromAll;
    public boolean newPM;
    public boolean wonChallenge;
    public boolean giftedGems;
    public boolean giftedSubscription;
    public boolean invitedParty;
    public boolean invitedGuild;
    public boolean questStarted;
    public boolean invitedQuest;
    public boolean majorUpdates;
    public boolean mentionParty;
    public boolean mentionJoinedGuild;
    public boolean mentionUnjoinedGuild;
    public boolean partyActivity;
}

class SuppressModals{
    public boolean levelUp;
    public boolean hatchPet;
    public boolean raisePet;
    public boolean streak;
}

class Tasks{
    public boolean groupByChallenge;
    public boolean confirmScoreNotes;
}

class Webhooks{
}

class Preferences{
    public Hair hair;
    public EmailNotifications emailNotifications;
    public PushNotifications pushNotifications;
    public SuppressModals suppressModals;
    public Tasks tasks;
    public int dayStart;
    public String size;
    public boolean hideHeader;
    public String skin;
    public String shirt;
    public int timezoneOffset;
    public String sound;
    public String chair;
    public String allocationMode;
    public boolean autoEquip;
    public String dateFormat;
    public boolean sleep;
    public boolean stickyHeader;
    public boolean disableClasses;
    public boolean newTaskEdit;
    public boolean dailyDueDefaultView;
    public boolean advancedCollapsed;
    public boolean toolbarCollapsed;
    public boolean reverseChatOrder;
    public boolean displayInviteToPartyWhenPartyIs1;
    public List<Object> improvementCategories;
    public String language;
    public Webhooks webhooks;
    public String background;
    public int timezoneOffsetAtLastCron;
    public boolean costume;
}

class Group{
    public String id;
    public String name;
}

class Data2{
    public String title;
    public List<String> items;
    public Group group;
    public Auth auth;
    public Flags flags;
    public Party party;
    public Preferences preferences;
    public String _id;
    public List<Notification> notifications;
    public String id;
}

class Notification{
    public String type;
    public boolean seen;
    public String id;
}

