package org.odesamama.mcd.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.odesamama.mcd.domain.Group;
import org.odesamama.mcd.domain.User;

public class GroupRepositoryImpl implements CustomGroupRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long getLastGroupIdByUser(User user) {
        TypedQuery<Group> q = entityManager.createQuery("from Group g where g.user = :user", Group.class);
        q.setParameter("user", user);
        return !q.getResultList().isEmpty() ? q.getResultList().get(0).getGroupId() : 0;
    }
}
