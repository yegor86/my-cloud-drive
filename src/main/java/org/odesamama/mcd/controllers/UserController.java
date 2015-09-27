package org.odesamama.mcd.controllers;

import javax.annotation.Resource;

import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.repositories.UserRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Resource
	private UserRepository userRepository;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<User> getUserList() {
          return userRepository.findAll();
    }

    @RequestMapping(value = "/files/{id}", method = RequestMethod.GET)
    public Iterable<File> getUserFileList(@PathVariable("id") Long id) {
        return userRepository.findOne(id).getFileList();
    }

}
