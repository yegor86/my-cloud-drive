package org.odesamama.mcd.services;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by starnakin on 07.10.2015.
 */
public interface FileService {

    void uploadFileToHDFSServer(byte [] bytes, String fileName) throws URISyntaxException, IOException;

}
