package org.odesamama.mcd.repositories;

import org.odesamama.mcd.domain.File;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FileRepository extends PagingAndSortingRepository<File, Long> {

}
