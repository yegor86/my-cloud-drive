package org.odesamama.mcd.controllers;

import org.odesamama.mcd.ErrorMessages;
import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.exeptions.EmailTakenException;
import org.odesamama.mcd.exeptions.ServiceException;
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

        if(user.getUserEmail() != null && !userService.checkEmailUnique(user.getUserEmail())){
            throw new EmailTakenException();
        }

        try {
            userRepository.save(user);
        }catch (Exception ex){
            throw new ServiceException(ErrorMessages.ERROR_CREATING_USER, ex);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
