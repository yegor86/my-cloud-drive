package org.odesamama.mcd.services;

import org.odesamama.mcd.domain.Acl;
import org.odesamama.mcd.domain.AclBuilder;
import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.domain.Group;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.domain.enums.Permissions;
import org.odesamama.mcd.repositories.AclRepository;
import org.odesamama.mcd.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AclRepository aclRepository;

    @Override
    public Group getOrCreateGroup(File file) {
        Group group = groupRepository.findByName(file.getFileUid());
        if (group != null) {
            return group;
        }
        group = new Group();
        group.setOwner(file.getOwner());
        group.setGroupName(file.getFileUid());
        groupRepository.saveGroup(group);
        return group;
    }

    @Override
    public void addUserToGroup(User user, Group group, Permissions perms) {
        Acl acl = aclRepository.findAclByUserAndGroup(user, group);
        if (acl != null) {
            throw new IllegalStateException("Access level already exists");
        }
        acl = new AclBuilder().user(user).group(group).permissions(perms).build();
        aclRepository.saveAcl(acl);
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
