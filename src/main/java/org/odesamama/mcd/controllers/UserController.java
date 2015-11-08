package org.odesamama.mcd.controllers;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.annotation.Resource;

import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.repositories.UserRepository;
import org.odesamama.mcd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {

    @Resource
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<String> getUserList() {
        return userRepository.findUsers();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) throws IOException, URISyntaxException {
        User created = userService.createUser(user);
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

}
