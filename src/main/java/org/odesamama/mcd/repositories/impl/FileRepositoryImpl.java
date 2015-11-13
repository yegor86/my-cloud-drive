package org.odesamama.mcd.repositories.impl;

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
    public List<File> getFilesInfoByFilePathAndEmali(String email, String path) {
        return entityManager.createNamedQuery("File.getFilesListWithoutRoot", File.class)
                .setParameter("email",email)
                .setParameter("path",path + "%")
                .setParameter("rootpath", path)
                .getResultList();
    }
}
