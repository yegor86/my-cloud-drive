package org.odesamama.mcd.dto;

/**
 * Created by starnakin on 15.10.2015.
 */
public class ErrorDto {
    private String message;
    private int status;

    public ErrorDto(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
