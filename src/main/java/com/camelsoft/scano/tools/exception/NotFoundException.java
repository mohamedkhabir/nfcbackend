package com.camelsoft.scano.tools.exception;


import com.camelsoft.scano.tools.error.ApiBaseException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiBaseException {
    public NotFoundException(String message){
        super(message);
    }
    @Override
    public HttpStatus getStatusCode(){
        return HttpStatus.NOT_FOUND;
    }
}
