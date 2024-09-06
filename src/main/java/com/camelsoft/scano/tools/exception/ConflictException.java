package com.camelsoft.scano.tools.exception;


import com.camelsoft.scano.tools.error.ApiBaseException;
import org.springframework.http.HttpStatus;

public class ConflictException extends ApiBaseException {

    public  ConflictException(String message){
        super(message);
    }
    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.CONFLICT;
    }
}
