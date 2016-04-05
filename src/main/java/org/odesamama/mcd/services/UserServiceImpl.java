package org.odesamama.mcd.services;

import java.io.IOException;
import java.net.URISyntaxException;

import org.odesamama.mcd.domain.Group;
import org.odesamama.mcd.domain.GroupBuilder;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.exeptions.EmailTakenException;
import org.odesamama.mcd.repositories.GroupRepository;
import org.odesamama.mcd.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by starnakin on 15.10.2015.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public boolean checkEmailUnique(String email) {
        if (userRepository.findByEmail(email) != null) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User createUser(User user) throws IOException, URISyntaxException {
        if (user.getUserEmail() != null && !checkEmailUnique(user.getUserEmail())) {
            throw new EmailTakenException();
        }

        userRepository.saveUser(user);
        Group group = new GroupBuilder().owner(user).groupName(user.getUserEmail()).build();
        groupRepository.saveGroup(group);
        user.setGroup(group);
        userRepository.updateUser(user);

        fileService.createHomeDirectoryForUser(user);
        return user;
    }
}
