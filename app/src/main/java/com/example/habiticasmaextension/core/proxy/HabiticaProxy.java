package com.example.habiticasmaextension.core.proxy;

import android.content.Context;

import com.example.habiticasmaextension.core.models.GroupMember;
import com.example.habiticasmaextension.core.models.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface HabiticaProxy {
    public User getUserDetails() throws IOException;
    public List<GroupMember> getGroupMembers(String groupId, Context ctx) throws IOException;
    public Map<String, String> getApiKeysFromChat(String groupId, Context ctx) throws IOException;
}
