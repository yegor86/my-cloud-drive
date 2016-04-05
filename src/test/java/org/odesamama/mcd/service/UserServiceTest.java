package org.odesamama.mcd.service;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.odesamama.mcd.Application;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.domain.UserBuilder;
import org.odesamama.mcd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by starnakin on 04.04.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void createUserTest() throws IOException, URISyntaxException {
        userService.createUser(createUser());
    }

    private User createUser() {
        return new UserBuilder().lastName("Last Name").userName("First Name").userEmail("test@email.com").build();
    }
}
