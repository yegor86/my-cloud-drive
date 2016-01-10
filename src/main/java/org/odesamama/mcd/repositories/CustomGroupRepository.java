package org.odesamama.mcd.repositories;

import org.odesamama.mcd.domain.Group;
import org.odesamama.mcd.domain.User;

public interface CustomGroupRepository {
    /**
     * Get the last record's PK of groups table
     */
    long getLastGroupIdByUser(User user);

    Group findByName(String groupName);

}
