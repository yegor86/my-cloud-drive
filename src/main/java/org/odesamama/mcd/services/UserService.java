package org.odesamama.mcd.services;

import java.io.IOException;
import java.net.URISyntaxException;

import org.odesamama.mcd.domain.User;

/**
 * Created by starnakin on 15.10.2015.
 */
public interface UserService {
    /**
     * Check if email is present in system
     * 
     * @return true if current email not registered
     */
    boolean checkEmailUnique(String email);

    User createUser(User user) throws IOException, URISyntaxException;
}
