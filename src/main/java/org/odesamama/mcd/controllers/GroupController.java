package org.odesamama.mcd.controllers;

import org.odesamama.mcd.domain.Group;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.repositories.GroupRepository;
import org.odesamama.mcd.repositories.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by starnakin on 27.09.2015.
 */

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Resource
    private GroupRepository groupRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Group> getUserList() {
        return groupRepository.findAll();
    }
}
