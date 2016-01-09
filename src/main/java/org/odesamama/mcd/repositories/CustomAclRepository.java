package org.odesamama.mcd.repositories;

import org.odesamama.mcd.domain.Acl;
import org.odesamama.mcd.domain.Group;
import org.odesamama.mcd.domain.User;

public interface CustomAclRepository {

    Acl findAclByUserAndGroup(User user, Group group);
}
