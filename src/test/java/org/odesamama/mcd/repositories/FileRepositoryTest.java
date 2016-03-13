package org.odesamama.mcd.repositories;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.odesamama.mcd.Application;
import org.odesamama.mcd.domain.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by starnakin on 14.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
@Rollback(true)
public class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;

    public static final String USER_EMAIL = "admin@mail.com";

    @Test
    public void testGetLocalFileListByPath() {
        List<File> files = fileRepository.getLocalFileListByPath(USER_EMAIL, "/");
        Assert.assertNotNull(files);
    }
}
