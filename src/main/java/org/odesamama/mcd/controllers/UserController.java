package org.odesamama.mcd.controllers;

import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.repositories.UserRepository;
import org.odesamama.mcd.restapi.UserResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController implements UserResource{
	
	@Resource
	private UserRepository userRepository;
	
    public Iterable<User> getUserList() {
          return userRepository.findAll();
    }

    public Iterable<File> getUserFileList(@PathVariable("id") Long id) {
        return userRepository.findOne(id).getFileList();
    }

}
