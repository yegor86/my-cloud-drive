package org.odesamama.mcd.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.repositories.CustomFileRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by starnakin on 12.11.2015.
 */

@Repository
public class FileRepositoryImpl implements CustomFileRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public File getFileInfoByFilePathAndEmail(String email, String path) {
        path = StringUtils.trimToEmpty(path);
        List<File> fileList = entityManager.createNamedQuery("File.getFileInfoByFilePathAndEmali", File.class)
                .setParameter("email", email).setParameter("path", path).getResultList();
        return !fileList.isEmpty() ? fileList.get(0) : null;
    }

    @Override
    public List<File> getFilesListForGivenDirectoryPath(String email, String path) {
        path = StringUtils.trimToEmpty(path);
        int dashCount = StringUtils.countMatches(path, "/");
        if (!path.isEmpty()) {
            dashCount++;
        }
        return entityManager.createNamedQuery("File.getFilesListForGivenFolderWithoutRoot", File.class)
                .setParameter("email", email).setParameter("path", path + "%").setParameter("rootpath", path)
                .setParameter("dashCount", dashCount).getResultList();
    }
}
