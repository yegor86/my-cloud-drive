package org.odesamama.mcd.exeptions;

import org.odesamama.mcd.ErrorMessages;
import org.springframework.http.HttpStatus;

/**
 * Created by starnakin on 15.10.2015.
 */
public class ServiceException extends RuntimeException{
    private static final int DEFAULT_STATUS_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private static final long serialVersionUID = -1835864569529933508L;
    private int statusCode;

    public ServiceException(){
        this(ErrorMessages.SERVICE_IS_ANAVAILABlE);
    }

    public ServiceException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ServiceException(String message, int statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public ServiceException(String message, Throwable cause){
        super(message, cause);
    }

    public ServiceException(String message) {
        this(message, DEFAULT_STATUS_CODE);
    }

    public int getStatusCode() {
        return statusCode;
    }

}
