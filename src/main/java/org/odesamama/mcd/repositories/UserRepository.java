package org.odesamama.mcd.repositories;

import org.odesamama.mcd.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long>,
        CrudRepository<User, Long>, CustomUserRepository{

}
