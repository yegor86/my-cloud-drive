package org.odesamama.mcd.repositories;

import org.odesamama.mcd.domain.Acl;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by starnakin on 17.10.2015.
 */
public interface AclRepository extends PagingAndSortingRepository<Acl, Long>, CustomAclRepository {
}
