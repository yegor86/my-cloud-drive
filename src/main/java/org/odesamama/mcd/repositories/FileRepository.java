package org.odesamama.mcd.repositories;

import org.odesamama.mcd.domain.File;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by starnakin on 17.10.2015.
 */
public interface FileRepository extends PagingAndSortingRepository<File, Long>, CustomFileRepository {
}
