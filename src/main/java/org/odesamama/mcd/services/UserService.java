package org.odesamama.mcd.services;

/**
 * Created by starnakin on 15.10.2015.
 */
public interface UserService {
    /**
     * Check if email is present in system
     * @return true if current email not registered
     */
    boolean checkEmailUnique(String email);
}
