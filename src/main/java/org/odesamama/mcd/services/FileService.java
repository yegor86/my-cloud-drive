package org.odesamama.mcd.services;

import org.odesamama.mcd.domain.User;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by starnakin on 07.10.2015.
 */
public interface FileService {

    void uploadFileToHDFSServer(byte [] bytes, String fileName, String email) throws URISyntaxException, IOException;

    void createHomeDirectoryForUser(User user);

}
