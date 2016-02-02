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
        List list = entityManager
                .createNativeQuery("select * from public.acl where user_id = :userid and group_id = :groupId",
                        Acl.class)
                .setParameter("userid", user.getUserId()).setParameter("groupId", group.getGroupId()).getResultList();
        return !list.isEmpty() ? (Acl) list.get(0) : null;
    }

    @Override
    public boolean saveAcl(Acl acl) {
        return entityManager
                .createNativeQuery(
                        "insert into public.acl (user_id, group_id, permissions) values (:userId, :groupId, :persmissions)")
                .setParameter("userId", acl.getUser().getUserId()).setParameter("groupId", acl.getGroup().getGroupId())
                .setParameter("persmissions", acl.getPermissions().getCode()).executeUpdate() > 0;
    }

}
