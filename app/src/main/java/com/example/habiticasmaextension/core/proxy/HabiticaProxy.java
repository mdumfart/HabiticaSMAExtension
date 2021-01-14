package com.example.habiticasmaextension.core.proxy;

import com.example.habiticasmaextension.core.models.GroupMember;
import com.example.habiticasmaextension.core.models.User;

import java.io.IOException;
import java.util.List;

public interface HabiticaProxy {
    public User getUserDetails() throws IOException;
    public List<GroupMember> getGroupMembers(String groupId) throws IOException;
}
