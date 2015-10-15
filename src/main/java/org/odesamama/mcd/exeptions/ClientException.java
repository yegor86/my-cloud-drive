package org.odesamama.mcd.exeptions;

import org.springframework.http.HttpStatus;

/**
 * Created by starnakin on 15.10.2015.
 */
public class ClientException extends ServiceException {
    public ClientException(String message) {
        super(message, HttpStatus.BAD_REQUEST.value());
    }

    public ClientException(String message, int statusCode) {
        super(message, statusCode);
    }

    public ClientException(String message, int statusCode, Throwable cause) {
        super(message, statusCode, cause);
    }

    public ClientException(String message, Throwable cause) {
        this(message, HttpStatus.BAD_REQUEST.value(), cause);
    }
}
