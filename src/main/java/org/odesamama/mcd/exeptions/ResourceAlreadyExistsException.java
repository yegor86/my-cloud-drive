package org.odesamama.mcd.exeptions;

/**
 * Created by starnakin on 13.11.2015.
 */
public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException() {

    }

    public ResourceAlreadyExistsException(String description) {
        super(description);
    }
}
