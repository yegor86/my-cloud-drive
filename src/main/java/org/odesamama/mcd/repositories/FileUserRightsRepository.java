package org.odesamama.mcd.repositories;

import org.odesamama.mcd.domain.UsersGroups;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by starnakin on 17.10.2015.
 */
public interface FileUserRightsRepository extends PagingAndSortingRepository<UsersGroups, Long> {
}
