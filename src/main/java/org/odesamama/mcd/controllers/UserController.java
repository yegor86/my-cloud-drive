package org.odesamama.mcd.controllers;

import javax.annotation.Resource;

import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.repositories.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Resource
	private UserRepository userRepository;
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
    public Iterable<User> getUserList() {
          return userRepository.findAll();
    }
}
