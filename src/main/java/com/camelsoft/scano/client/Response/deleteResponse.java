package com.camelsoft.scano.client.Response;

public class deleteResponse {
    private String message;

    public deleteResponse() {
    }

    public deleteResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
