package org.odesamama.mcd.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.odesamama.mcd.Application;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by starnakin on 16.10.2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class FileServiceTest {

    @Autowired
    private FileService fileService;

    @Test
    public void testCreatingDirectoryForUser() throws URISyntaxException, IOException {
        User user = new User();
        user.setUserId(System.nanoTime());
        fileService.createHomeDirectoryForUser(user);
    }

}
