package org.odesamama.mcd.exeptions;

/**
 * Created by starnakin on 12.11.2015.
 */
public class NoSuchResourceException extends RuntimeException {

    public NoSuchResourceException() {

    }

    public NoSuchResourceException(String description) {
        super(description);
    }
}
