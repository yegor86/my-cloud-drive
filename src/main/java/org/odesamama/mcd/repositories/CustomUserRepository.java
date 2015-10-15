package org.odesamama.mcd.repositories;

import org.odesamama.mcd.domain.User;

/**
 * Created by starnakin on 27.09.2015.
 */
public interface CustomUserRepository {
    User findByEmail(String email);
}
