package org.odesamama.mcd.controllers;

import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.repositories.UserRepository;
import org.odesamama.mcd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

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

    @RequestMapping(value = "/{email:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUser(@PathVariable("email") String email) {
        System.out.println("searching user " + email);
        ResponseEntity<User> entity = new ResponseEntity<>(userRepository.findByEmail(email), HttpStatus.OK);
        return entity;
    }

    @RequestMapping(value = "/files/{email:.+}", method = RequestMethod.GET)
    public Iterable<File> getUserFileList(@PathVariable("email") String email) {
        User user = userRepository.findByEmail(email);
        if(user != null) {
            return user.getFileList();
        }
        return new ArrayList<>();
    }

    @RequestMapping(value="/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody User user) throws IOException, URISyntaxException {
        User created = userService.createUser(user);
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

}
