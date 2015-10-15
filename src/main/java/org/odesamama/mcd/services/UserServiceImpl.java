package org.odesamama.mcd.services;

import org.odesamama.mcd.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by starnakin on 15.10.2015.
 */

@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserRepository userRepository;

    @Override
    public boolean checkEmailUnique(String email) {
        if(userRepository.findByEmail(email) != null){
            return false;
        }
        return true;
    }
}
