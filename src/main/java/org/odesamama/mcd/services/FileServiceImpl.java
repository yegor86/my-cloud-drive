package org.odesamama.mcd.services;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.Validate;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.odesamama.mcd.domain.Acl;
import org.odesamama.mcd.domain.AclBuilder;
import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.domain.FileBuilder;
import org.odesamama.mcd.domain.Group;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.domain.enums.Permissions;
import org.odesamama.mcd.exeptions.ResourceAlreadyExistsException;
import org.odesamama.mcd.exeptions.UserNotExistsException;
import org.odesamama.mcd.repositories.AclRepository;
import org.odesamama.mcd.repositories.FileRepository;
import org.odesamama.mcd.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by starnakin on 07.10.2015.
 */

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AclRepository aclRepository;

    @Autowired
    private Configuration conf;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${hdfs.namenode.url}")
    private String nameNodeUrl;

    @Override
    public void uploadFileToHDFSServer(byte[] bytes, String filePath, String email)
            throws IOException, URISyntaxException {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotExistsException();
        }

        if (fileRepository.getFileInfoByFilePathAndEmail(email, filePath) != null) {
            throw new ResourceAlreadyExistsException("File already exists");
        }

        saveFileMetadata(user, filePath, bytes.length, false);

        Path path = new Path(String.format("%s/%s/%s", nameNodeUrl, email, filePath));
        saveFileToHDFS(path, conf, bytes);
    }

    @Override
    public void createFolder(String relativePath, String email) throws IOException, URISyntaxException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotExistsException();
        }

        if (fileRepository.getFileInfoByFilePathAndEmail(email, relativePath) != null) {
            throw new ResourceAlreadyExistsException();
        }

        saveFileMetadata(user, relativePath, 0, true);

        Path hdfsPath = new Path(String.format("%s/%s/%s", nameNodeUrl, email, relativePath));
        mkDirsHDFS(hdfsPath, conf);
    }

    // don't return error if folder exists
    private void mkDirsHDFS(Path filePath, Configuration conf) throws URISyntaxException, IOException {
        try (FileSystem fileSystem = FileSystem.get(new URI(nameNodeUrl), conf)) {
            fileSystem.mkdirs(filePath);
        }
    }

    private void saveFileToHDFS(Path filePath, Configuration conf, byte[] bytes)
            throws URISyntaxException, IOException {
        try (FileSystem fileSystem = FileSystem.get(new URI(nameNodeUrl), conf)) {

            OutputStream os = fileSystem.create(filePath, () -> logger.debug("File loaded {}", filePath.getName()));
            try (BufferedOutputStream bw = new BufferedOutputStream(os)) {
                bw.write(bytes);
            }
        }
    }

    private File saveFileMetadata(User user, String filePath, int fileSize, boolean isFolder) {
        if (!filePath.startsWith("/")) {
            throw new IllegalArgumentException("File path must start with '/'");
        }

        int index = filePath.lastIndexOf("/");
        String fileName = filePath.substring(index + 1);
        String parentPath = filePath.substring(0, index);

        // Handle root file
        parentPath = parentPath.isEmpty() ? "/" : parentPath;

        File parent = fileRepository.getFileInfoByFilePathAndEmail(user.getUserEmail(), parentPath);
        validateAccess(user, parent);

        // Inherit parent's group
        File file = new FileBuilder().owner(user).group(parent.getGroup()).name(fileName).path(filePath).parent(parent)
                .size(fileSize).isFolder(isFolder).extension(FilenameUtils.getExtension(fileName)).build();
        return fileRepository.save(file);
    }

    @Override
    public void createHomeDirectoryForUser(User user) throws URISyntaxException, IOException {
        Path directoryPath = new Path(String.format("%s/%s/", nameNodeUrl, user.getUserId()));

        try (FileSystem fileSystem = FileSystem.get(new URI(nameNodeUrl), conf)) {
            fileSystem.mkdirs(directoryPath);
        }
    }

    @Override
    public void saveUserRights(File file, User user, Permissions permissions) {
        Acl acl = new AclBuilder().user(user).permissions(permissions).build();
        aclRepository.save(acl);
    }

    @Override
    public InputStream getFile(String filePath, String email) throws URISyntaxException, IOException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotExistsException();
        }

        Path path = new Path(String.format("%s/%s/%s", nameNodeUrl, email, filePath));

        FileSystem fileSystem = FileSystem.get(new URI(nameNodeUrl), conf);
        return fileSystem.open(path);
    }

    @Override
    public void updateFileGroup(File dir, String email, Group group) {
        Validate.notNull(dir.getParent());
        Validate.notNull(dir.getPath());

        dir.setGroup(group);
        fileRepository.save(dir);

        List<File> fileList = fileRepository.getFileListByFilePathAndEmail(email, dir.getPath());
        for (File file : fileList) {
            file.setGroup(group);
            fileRepository.save(file);
        }
    }

    void validateAccess(User user, File folder) {
        if (!user.equals(folder.getOwner()) && aclRepository.findAclByUserAndGroup(user, folder.getGroup()) == null) {
            throw new SecurityException("User is not allowed to perform operation");
        }

    }
}
