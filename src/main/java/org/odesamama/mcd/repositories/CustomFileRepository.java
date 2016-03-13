package org.odesamama.mcd.repositories;

import java.util.List;

import org.odesamama.mcd.domain.File;

/**
 * Created by starnakin on 12.11.2015.
 */
public interface CustomFileRepository {
    File getFileInfoByFilePathAndEmail(String email, String path);

    List<File> getLocalFileListByPath(String email, String path);

    List<File> getSharedFileListByPath(String email, String path);
}
