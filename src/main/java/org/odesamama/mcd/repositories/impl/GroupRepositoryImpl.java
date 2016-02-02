package org.odesamama.mcd.repositories.impl;

import java.util.List;

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
        List list = entityManager
                .createNativeQuery("select * from public.groups g where g.group_name = :groupName", Group.class)
                .setParameter("groupName", groupName).getResultList();
        return !list.isEmpty() ? (Group) list.get(0) : null;
    }

    @Override
    public boolean saveGroup(Group group) {
        return entityManager
                .createNativeQuery("insert into public.groups (group_name, owner_id) values (:groupName, :ownerId)")
                .setParameter("groupName", group.getGroupName()).setParameter("ownerId", group.getOwner().getUserId())
                .executeUpdate() > 0;
    }
}
