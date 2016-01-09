package org.odesamama.mcd.services;

import org.odesamama.mcd.domain.Acl;
import org.odesamama.mcd.domain.AclBuilder;
import org.odesamama.mcd.domain.Group;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.domain.enums.Permissions;
import org.odesamama.mcd.repositories.AclRepository;
import org.odesamama.mcd.repositories.GroupRepository;
import org.odesamama.mcd.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AclRepository aclRepository;

    @Override
    public void createGroup(String email) {
        User user = userRepository.findByEmail(email);

        Group group = new Group();
        group.setOwner(user);
        group.setGroupName(email);
        groupRepository.save(group);

        Acl acl = new AclBuilder().user(user).group(group).permissions(Permissions.USER_WRITE).build();
        aclRepository.save(acl);
    }

    @Override
    public void addUserToGroup(User user, Group group, Permissions perms) {
        Acl acl = aclRepository.findAclByUserAndGroup(user, group);
        if (acl != null) {
            throw new IllegalStateException("Access level already exists");
        }
        acl = new AclBuilder().user(user).group(group).permissions(perms).build();
        aclRepository.save(acl);
    }

    @Override
    public void removeUserFromGroup(User user, Group group) {
        Acl acl = aclRepository.findAclByUserAndGroup(user, group);
        if (acl == null) {
            throw new IllegalStateException("Access level does not exist");
        }
        aclRepository.delete(acl);
    }
}
