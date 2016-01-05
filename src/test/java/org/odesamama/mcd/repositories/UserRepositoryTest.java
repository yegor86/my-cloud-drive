package org.odesamama.mcd.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.odesamama.mcd.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by starnakin on 15.10.2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
@Rollback(true)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    public static final String USER_EMAIL = "admin@mail.com";

    @Test
    public void getUserByEmail(){
        Assert.assertNotNull(userRepository.findByEmail(USER_EMAIL));
    }
}
