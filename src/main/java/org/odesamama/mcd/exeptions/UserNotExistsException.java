package org.odesamama.mcd.exeptions;

/**
 * Created by starnakin on 15.10.2015.
 */
public class UserNotExistsException extends RuntimeException {

    public UserNotExistsException() {
    }

    public UserNotExistsException(String message) {
        super(message);
    }
}
