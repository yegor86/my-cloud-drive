package org.odesamama.mcd.dto;

/**
 * Created by starnakin on 15.10.2015.
 */
public class ErrorDto {
    public static enum ErrorType {
        CLIENT_ERROR, SERVER_ERROR
    }

    private String message;
    private ErrorType type;

    public ErrorDto(String message, ErrorType type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorType getType() {
        return type;
    }

    public void setType(ErrorType type) {
        this.type = type;
    }
}
