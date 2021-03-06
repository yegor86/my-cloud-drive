package org.odesamama.mcd.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.domain.Group;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.domain.enums.Permissions;

/**
 * Created by starnakin on 07.10.2015.
 */
public interface FileService {

    void uploadFileToHDFSServer(byte[] bytes, String filePath, String email) throws URISyntaxException, IOException;

    void createFolder(String Path, String email) throws IOException, URISyntaxException;

    void createHomeDirectoryForUser(User user) throws URISyntaxException, IOException;

    void saveUserRights(File file, User user, Permissions permissions);

    InputStream getFile(String filePath, String email) throws URISyntaxException, IOException;

    void updateFileGroup(File file, String email, Group group);
}
