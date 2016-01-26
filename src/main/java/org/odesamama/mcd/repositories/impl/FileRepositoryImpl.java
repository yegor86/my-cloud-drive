package org.odesamama.mcd.repositories.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.domain.Group;
import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.exeptions.NoSuchResourceException;
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
        List<File> fileList = jdbcTemplate.query(
                "select * from files join public.users on user_id = owner_id where user_email = ? and file_path = ?",
                new Object[] { email, path }, (rs, rowNum) -> {
                    User owner = new User();
                    owner.setUserId(rs.getLong("user_id"));
                    owner.setUserUid(rs.getString("user_uid"));
                    owner.setUserName(rs.getString("user_name"));
                    owner.setLastName(rs.getString("last_name"));
                    owner.setUserEmail(email);

                    Group group = new Group();
                    group.setGroupId(rs.getLong("group_id"));
                    group.setGroupName(email);
                    group.setOwner(owner);

                    File file = new File();
                    file.setId(rs.getLong("file_id"));
                    file.setFileUid(rs.getString("file_uid"));
                    file.setName(rs.getString("file_name"));
                    file.setPath(rs.getString("file_path"));
                    file.setSize(rs.getInt("file_size"));
                    file.setCreated(rs.getDate("create_date"));
                    file.setUpdated(rs.getDate("update_date"));
                    file.setFolder(rs.getBoolean("is_folder"));
                    file.setExtension(rs.getString("extension"));
                    file.setOwner(owner);
                    file.setGroup(group);

                    return file;
                });

        return !fileList.isEmpty() ? (File) fileList.get(0) : null;
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
}
