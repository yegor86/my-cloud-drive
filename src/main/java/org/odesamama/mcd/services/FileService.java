package org.odesamama.mcd.services;

import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.domain.enums.Permissions;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by starnakin on 07.10.2015.
 */
public interface FileService {

    void uploadFileToHDFSServer(byte [] bytes, String fileName, String email) throws URISyntaxException, IOException;

    void createHomeDirectoryForUser(User user) throws URISyntaxException, IOException;

    void saveUserRights(File file, User user, Permissions permissions);
}
