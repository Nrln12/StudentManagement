package com.example.bankofbaku.StudentManagement.exceptions;

import org.springframework.http.HttpStatus;
import java.util.Map;

public abstract class CustomException extends RuntimeException{
    private HttpStatus status;
    private Map<String, String> params;
    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    public CustomException(String message, HttpStatus status, Map<String, String> params){
        super(message);
        this.status=status;
        this.params=params;
    }
    public HttpStatus getStatus(){
        return this.status;
    }
    public Map<String, String> getParams(){
        return params;
    }

}
