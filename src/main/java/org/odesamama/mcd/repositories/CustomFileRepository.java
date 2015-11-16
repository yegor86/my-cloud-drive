package org.odesamama.mcd.repositories;

import org.odesamama.mcd.domain.File;

import java.util.List;

/**
 * Created by starnakin on 12.11.2015.
 */
public interface CustomFileRepository {
    File getFileInfoByFilePathAndEmail(String email, String path);

    List<File> getFilesListForGivenDirectoryPath(String email, String path);
}
