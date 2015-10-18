package org.odesamama.mcd.services;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.odesamama.mcd.ErrorMessages;
import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.domain.FilesUsersRights;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.domain.enums.Permissions;
import org.odesamama.mcd.exeptions.ClientException;
import org.odesamama.mcd.exeptions.ServiceException;
import org.odesamama.mcd.repositories.FileRepository;
import org.odesamama.mcd.repositories.FileUserRightsRepository;
import org.odesamama.mcd.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by starnakin on 07.10.2015.
 */

@Service
public class FileServiceImpl implements FileService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileUserRightsRepository rightsRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${hdfs.namenode.url}")
    private String nameNodeUrl;

    @Override
    public void uploadFileToHDFSServer(byte[] bytes, String fileName, String email) {

        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new ClientException(ErrorMessages.USER_NOT_EXISTS + user.getUserEmail());
        }

        Configuration conf = new Configuration();
        // save file
        Path filePath = new Path(String.format("%s/%s/%s",nameNodeUrl , user.getUserId(), fileName));

        try (FileSystem fileSystem = FileSystem.get(new URI(nameNodeUrl), conf)) {

            OutputStream os = fileSystem.create(filePath, () -> LOGGER.debug("File loaded {}", filePath.getName()));
            try (BufferedOutputStream bw = new BufferedOutputStream(os)) {
                bw.write(bytes);
            }

            //save file metadata
            File file = new File(user,fileName,user.getUserId().toString(),bytes.length);
            File savedFile = fileRepository.save(file);
            //save owner access to file
            FilesUsersRights rights = new FilesUsersRights(savedFile, user, Permissions.READ_MODIFY);
            rightsRepository.save(rights);
        }
        catch (Exception ex){
            //TODO remove hadoop exception first
            LOGGER.error("error", ex);
            //throw new ServiceException(ErrorMessages.ERROR_SAVING_FILE);
        }
    }

    @Override
    public void createHomeDirectoryForUser(User user) {
        Configuration conf = new Configuration();
        Path directoryPath = new Path(String.format("%s/%s/",nameNodeUrl,user.getUserId()));

        try (FileSystem fileSystem = FileSystem.get(new URI(nameNodeUrl), conf)) {
            fileSystem.mkdirs(directoryPath);
        }catch (Exception ex){
            throw new ServiceException(ErrorMessages.UNABLE_CREATE_USER_DIRECTORY, ex);
        }
    }

}
