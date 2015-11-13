package org.odesamama.mcd.services;

import java.io.IOException;
import java.net.URISyntaxException;

import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.exeptions.EmailTakenException;
import org.odesamama.mcd.multitenancy.TenantManager;
import org.odesamama.mcd.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    private TenantManager tenantManager = new TenantManager();

    @Override
    public boolean checkEmailUnique(String email) {
        if (userRepository.findByEmail(email) != null) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public User createUser(User user) throws IOException, URISyntaxException {
        if (user.getUserEmail() != null && !checkEmailUnique(user.getUserEmail())) {
            throw new EmailTakenException();
        }

        String userUid = user.getUserEmail().replace('@', '_');
        user.setUserUid(userUid);

        User savedUser = userRepository.save(user);
        tenantManager.create(user.getUserUid());
        fileService.createHomeDirectoryForUser(savedUser);
        return savedUser;
    }
}
