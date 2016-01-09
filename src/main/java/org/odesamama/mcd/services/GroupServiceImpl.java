package org.odesamama.mcd.services;

import org.odesamama.mcd.domain.Group;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.repositories.GroupRepository;
import org.odesamama.mcd.repositories.UserRepository;
import org.odesamama.mcd.repositories.UsersGroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UsersGroupsRepository usersGroupsRepository;

    @Override
    public void createGroup(String email) {
        User user = userRepository.findByEmail(email);
        long groupId = groupRepository.getLastGroupIdByUser(user);

        Group group = new Group();
        group.setUser(user);
        // Set a new group name as concatenation of user's email and next
        // groupId
        group.setGroupName(String.format("%s%d", email, groupId + 1));
        groupRepository.save(group);
    }
}
