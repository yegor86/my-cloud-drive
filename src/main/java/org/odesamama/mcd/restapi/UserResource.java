package org.odesamama.mcd.restapi;

import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.domain.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by starnakin on 07.10.2015.
 */


@RequestMapping("/users")
public interface UserResource {

    @RequestMapping(value = "", method = RequestMethod.GET)
    Iterable<User> getUserList();

    @RequestMapping(value = "/files/{id}", method = RequestMethod.GET)
    Iterable<File> getUserFileList(@PathVariable("id") Long id);

}
