package org.odesamama.mcd.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.odesamama.mcd.Application;
import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.domain.FileBuilder;
import org.odesamama.mcd.domain.Group;
import org.odesamama.mcd.domain.GroupBuilder;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.domain.UserBuilder;
import org.odesamama.mcd.repositories.FileRepository;
import org.odesamama.mcd.repositories.GroupRepository;
import org.odesamama.mcd.repositories.UserRepository;
import org.odesamama.mcd.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by starnakin on 16.10.2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class FileServiceTest {

    @Autowired
    private FileService fileService;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    private final Group defaultGroup = new GroupBuilder().groupId(-1L).groupName("defaultGroup").owner(null).build();

    @Test
    public void testCreatingDirectoryForUser() throws URISyntaxException, IOException {
        User user = new User();
        user.setUserId(System.nanoTime());
        fileService.createHomeDirectoryForUser(user);
    }

    @Test(expected = Exception.class)
    public void testUpdateFileGroupNullParent() {

        User owner = new UserBuilder().userName("test").lastName("test").userEmail("test@mail.org").group(defaultGroup)
                .fileList(null).build();

        userRepository.save(owner);

        File parent = new FileBuilder().fileUid(UUID.randomUUID().toString()).owner(owner).group(defaultGroup).name("/")
                .path("/").created(new Date()).updated(new Date()).parent(null).size(0).isFolder(true).extension(null)
                .build();
        fileRepository.save(parent);

        Group fileGroup = new GroupBuilder().groupName(UUID.randomUUID().toString()).owner(owner).build();
        groupRepository.saveGroup(fileGroup);

        File file = new FileBuilder().fileUid(UUID.randomUUID().toString()).owner(owner).group(null).name("file.txt")
                .path("/testfolder").created(new Date()).updated(new Date()).parent(parent).size(100).isFolder(true)
                .extension(null).build();
        fileRepository.save(file);

        // Null out parent file
        file.setParent(null);
        fileService.updateFileGroup(file, owner.getUserEmail(), fileGroup);
    }

}
