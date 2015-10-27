package org.odesamama.mcd.controllers;

import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.repositories.UserRepository;
import org.odesamama.mcd.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/users")
@RestController
public class UserController{

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Resource
	private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<User> getUserList() {
          return userRepository.findAll();
    }

    @RequestMapping(value = "/files/{id}", method = RequestMethod.GET)
    public Iterable<File> getUserFileList(@PathVariable("id") Long id) {
        return userRepository.findOne(id).getFileList();
    }

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user){

        User created = userService.createUser(user);

        return new ResponseEntity<>(created, HttpStatus.OK);
    }

}
