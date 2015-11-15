package org.odesamama.mcd.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.odesamama.mcd.Application;
import org.odesamama.mcd.domain.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by starnakin on 14.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;

    public static final String USER_EMAIL = "admin@mail.com";

    @Test
    public void getFilesByUserAntPath(){
        List<File> files = fileRepository.getFilesListForGivenDirectoryPath(USER_EMAIL, null);
        Assert.assertNotNull(files);
    }
}
