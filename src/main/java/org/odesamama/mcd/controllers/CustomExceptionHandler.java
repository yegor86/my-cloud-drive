package org.odesamama.mcd.controllers;

import javax.servlet.http.HttpServletRequest;

import org.odesamama.mcd.dto.ErrorDto;
import org.odesamama.mcd.exeptions.EmailTakenException;
import org.odesamama.mcd.exeptions.NoSuchResourceException;
import org.odesamama.mcd.exeptions.ResourceAlreadyExistsException;
import org.odesamama.mcd.exeptions.UserNotExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by starnakin on 15.10.2015.
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({ Exception.class })
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Object> defaultExceptionHandler(HttpServletRequest req, Exception exception) {
        logger.error("handle exception", exception);
        ErrorDto errorDto = new ErrorDto(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<Object>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ EmailTakenException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Email is taken")
    public void clientException(HttpServletRequest req, Exception exception) {
        logger.error("handle exception", exception);
    }

    @ExceptionHandler({ UserNotExistsException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User not exists")
    public void serviceException(HttpServletRequest req, Exception exception) {
        logger.error("handle exception", exception);
    }

    @ExceptionHandler({ NoSuchResourceException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Resource not found")
    public void notDirectoryException(HttpServletRequest req, Exception exception) {
        logger.error("handle exception", exception);
    }

    @ExceptionHandler({ ResourceAlreadyExistsException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Resource already exists")
    public void resourceAlreadyExistsException(HttpServletRequest req, Exception exception) {
        logger.error("handle exception", exception);
    }

    @ExceptionHandler({ EmptyResultDataAccessException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Resource not found")
    public void databeseResourseNotFoundException(HttpServletRequest req, Exception exception) {
        logger.error("handle exception", exception);
    }
}
