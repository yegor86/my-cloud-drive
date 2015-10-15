package org.odesamama.mcd;

import org.odesamama.mcd.dto.ErrorDto;
import org.odesamama.mcd.exeptions.ClientException;
import org.odesamama.mcd.exeptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by starnakin on 15.10.2015.
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<Object> serviceException(HttpServletRequest req, Exception exception) {
        LOGGER.error("handle exception",exception);
        ErrorDto error = new ErrorDto(((ServiceException)exception).getMessage(), ErrorDto.ErrorType.SERVER_ERROR);
        return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ClientException.class})
    public ResponseEntity<Object> clientException(HttpServletRequest req, Exception exception) {
        LOGGER.error("handle exception",exception);
        ErrorDto error = new ErrorDto(((ServiceException)exception).getMessage(), ErrorDto.ErrorType.CLIENT_ERROR);
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }
}
