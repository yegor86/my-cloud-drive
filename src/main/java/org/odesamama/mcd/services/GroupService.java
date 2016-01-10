package org.odesamama.mcd.services;

import org.odesamama.mcd.domain.Group;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.domain.enums.Permissions;

public interface GroupService {

    void createGroupByEmail(String email);

    void createGroupByUser(User user);

    void addUserToGroup(User user, Group group, Permissions perms);

    void removeUserFromGroup(User user, Group group);
}
