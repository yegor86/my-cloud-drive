package org.odesamama.mcd.repositories.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.domain.Group;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.exeptions.NoSuchResourceException;
import org.odesamama.mcd.exeptions.UserNotExistsException;
import org.odesamama.mcd.repositories.CustomFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by starnakin on 12.11.2015.
 */

@Repository
public class FileRepositoryImpl implements CustomFileRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public File getFileInfoByFilePathAndEmail(String email, String path) {
        path = StringUtils.trimToEmpty(path);

        List<Object[]> resultList = entityManager
                .createNativeQuery("select * from public.get_file_info_by_filepath_and_email(:email, :path)")
                .setParameter("email", email).setParameter("path", path).getResultList();

        return !resultList.isEmpty() ? recordToFile(resultList.get(0)) : null;
    }

    @Override
    public List<File> getFilesListForGivenDirectoryPath(String email, String path) {

        File parentFolder = getFileInfoByFilePathAndEmail(email, path);

        if (parentFolder == null || !parentFolder.isFolder()) {
            throw new NoSuchResourceException();
        }

        return entityManager.createNamedQuery("File.getFilesListForGivenFolder", File.class)
                .setParameter("parent", parentFolder).getResultList();
    }

    @Override
    public List<File> listFiles(String email, String path) {
        return Collections.emptyList();
    }

    @Override
    public List<File> getListByPath(String email, String path) {

        List<User> userList = jdbcTemplate.query("select * from public.users where user_email = ?",
                new Object[] { email }, (rs, rowNum) -> {
                    User user = new User();
                    user.setUserId(rs.getLong("user_id"));
                    user.setUserUid(rs.getString("user_uid"));
                    user.setUserName(rs.getString("user_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setUserEmail(email);

                    return user;
                });
        if (userList.isEmpty()) {
            throw new UserNotExistsException("User was not found with email: " + email);
        }
        User user = userList.get(0);

        File parentFolder = getFileInfoByFilePathAndEmail(email, path);
        if (parentFolder == null || !parentFolder.isFolder()) {
            throw new NoSuchResourceException();
        }

        List<Object[]> resultList = entityManager
                .createNativeQuery("select * from public.select_all_files(:userId,:parentFileId)")
                .setParameter("userId", user.getUserId()).setParameter("parentFileId", parentFolder.getId())
                .getResultList();
        List<File> fileList = new ArrayList<>();

        for (Object[] objectArray : resultList) {

            File file = recordToFile(objectArray);
            file.getOwner().setUserId(user.getUserId());
            file.getOwner().setUserEmail(email);
            file.getGroup().setGroupName(email);
            fileList.add(file);
        }

        return fileList;
    }

    private File recordToFile(Object[] record) {
        User owner = new User();
        owner.setUserId(((BigInteger) record[10]).longValue());
        if (record.length > 11) {
            owner.setUserUid((String) record[11]);
        }
        if (record.length > 12) {
            owner.setUserName((String) record[12]);
        }
        if (record.length > 13) {
            owner.setLastName((String) record[13]);
        }

        Group group = new Group();
        group.setGroupId(((BigInteger) record[9]).longValue());
        group.setOwner(owner);

        File file = new File();
        file.setId(((BigInteger) record[0]).longValue());
        file.setFileUid((String) record[1]);
        file.setName((String) record[2]);
        file.setPath((String) record[3]);
        file.setSize(((BigInteger) record[4]).intValue());
        file.setCreated((Date) record[5]);
        file.setUpdated((Date) record[6]);
        file.setFolder((Boolean) record[7]);
        file.setExtension((String) record[8]);
        file.setOwner(owner);
        file.setGroup(group);

        return file;
    }
}
