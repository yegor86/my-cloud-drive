package org.odesamama.mcd.repositories.impl;

import org.apache.commons.lang3.StringUtils;
import org.odesamama.mcd.domain.File;
import org.odesamama.mcd.repositories.CustomFileRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by starnakin on 12.11.2015.
 */

@Repository
public class FileRepositoryImpl implements CustomFileRepository{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public File getFileInfoByFilePathAndEmali(String email, String path) {
        path = StringUtils.trimToEmpty(path);
        List<File> fileList = entityManager.createNamedQuery("File.getFileInfoByFilePathAndEmali", File.class)
                .setParameter("email",email)
                .setParameter("path", path)
                .getResultList();
        if(!fileList.isEmpty()){
            return fileList.get(0);
        }
        return null;
    }

    @Override
    public List<File> getFilesListForGivenDirectoryPath(String email, String path) {
        path = StringUtils.trimToEmpty(path);
        int dashCount = StringUtils.countMatches(path,"/");
        if(!path.isEmpty()){
            dashCount++;
        }
        return entityManager.createNamedQuery("File.getFilesListForGivenFolderWithoutRoot", File.class)
                .setParameter("email",email)
                .setParameter("path",path + "%")
                .setParameter("rootpath", path)
                .setParameter("dashCount",dashCount)
                .getResultList();
    }
}
