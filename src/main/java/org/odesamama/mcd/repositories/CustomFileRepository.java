package org.odesamama.mcd.repositories;

import org.odesamama.mcd.domain.File;

import java.util.List;

/**
 * Created by starnakin on 12.11.2015.
 */
public interface CustomFileRepository {
    File getFileInfoByFilePathAndEmali(String email, String path);

    List<File> getFilesInfoByFilePathAndEmali(String email, String path);
}
