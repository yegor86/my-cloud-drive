package org.odesamama.mcd.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.odesamama.mcd.domain.Acl;
import org.odesamama.mcd.domain.Group;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.repositories.CustomAclRepository;

public class AclRepositoryImpl implements CustomAclRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Acl findAclByUserAndGroup(User user, Group group) {
        List<Acl> aclList = entityManager.createNamedQuery("Acl.findAclByUserAndGroup", Acl.class)
                .setParameter("user", user).setParameter("group", group).getResultList();
        return !aclList.isEmpty() ? aclList.get(0) : null;
    }

}
