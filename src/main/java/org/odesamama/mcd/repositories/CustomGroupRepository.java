package org.odesamama.mcd.repositories;

import org.odesamama.mcd.domain.Group;

public interface CustomGroupRepository {
    Group findByName(String groupName);

    Long saveGroup(Group group);

}
