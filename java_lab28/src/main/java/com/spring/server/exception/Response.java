package com.spring.server.exception;

public class Response {

    private String message;

    public Response(String message) {
        this.message = message;
    }

    //region Getter and Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    //endregion
}
