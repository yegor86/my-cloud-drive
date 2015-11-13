package org.odesamama.mcd.controllers;

import org.odesamama.mcd.exeptions.EmailTakenException;
import org.odesamama.mcd.exeptions.UserNotExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by starnakin on 15.10.2015.
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({Exception.class})
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="Service is unavailable")
    public void defaultExceptionHandler(HttpServletRequest req, Exception exception) {
        LOGGER.error("handle exception",exception);
    }


    @ExceptionHandler({EmailTakenException.class})
    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Email is taken")
    public void clientException(HttpServletRequest req, Exception exception) {
        LOGGER.error("handle exception",exception);
    }

    @ExceptionHandler({UserNotExistsException.class})
    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="User not exists")
    public void serviceException(HttpServletRequest req, Exception exception) {
        LOGGER.error("handle exception",exception);
    }
}
