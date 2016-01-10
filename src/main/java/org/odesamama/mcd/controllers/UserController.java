package org.odesamama.mcd.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.repositories.UserRepository;
import org.odesamama.mcd.services.GroupService;
import org.odesamama.mcd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<String> getUserList() {
        return userRepository.findUsers();
    }

    @RequestMapping(value = "/{email:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("email") String email) {
        System.out.println("searching user " + email);
        return new ResponseEntity<User>(userRepository.findByEmail(email), HttpStatus.OK);
    }

    @RequestMapping(value = "/files/{email:.+}", method = RequestMethod.GET)
    public Iterable<File> getUserFileList(@PathVariable("email") String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return user.getFileList();
        }
        return new ArrayList<>();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User user) throws IOException, URISyntaxException {
        User created = userService.createUser(user);
        groupService.getOrCreateGroup(user);
        return new ResponseEntity<User>(created, HttpStatus.OK);
    }

}
