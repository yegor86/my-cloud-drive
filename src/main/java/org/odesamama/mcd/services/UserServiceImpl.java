package org.odesamama.mcd.services;

import org.odesamama.mcd.ErrorMessages;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.exeptions.EmailTakenException;
import org.odesamama.mcd.exeptions.ServiceException;
import org.odesamama.mcd.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by starnakin on 15.10.2015.
 */

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    FileService fileService;

    @Resource
    private UserRepository userRepository;

    @Override
    public boolean checkEmailUnique(String email) {
        if(userRepository.findByEmail(email) != null){
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public User createUser(User user) {
        if(user.getUserEmail() != null && !checkEmailUnique(user.getUserEmail())){
            throw new EmailTakenException();
        }

        try {
            User savedUser = userRepository.save(user);
            fileService.createHomeDirectoryForUser(savedUser);
        }catch (Exception ex){
            throw new ServiceException(ErrorMessages.ERROR_CREATING_USER, ex);
        }

        return null;
    }
}
