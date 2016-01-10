package org.odesamama.mcd.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.odesamama.mcd.domain.Group;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.repositories.CustomGroupRepository;

public class GroupRepositoryImpl implements CustomGroupRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long getLastGroupIdByUser(User user) {
        TypedQuery<Group> q = entityManager.createQuery("from Group g where g.user = :user", Group.class);
        q.setParameter("user", user);
        return !q.getResultList().isEmpty() ? q.getResultList().get(0).getGroupId() : 0;
    }

    @Override
    public Group findByName(String groupName) {
        TypedQuery<Group> q = entityManager.createQuery("from Group g where g.groupName = :groupName", Group.class);
        q.setParameter("groupName", groupName);
        return !q.getResultList().isEmpty() ? q.getResultList().get(0) : null;
    }
}
