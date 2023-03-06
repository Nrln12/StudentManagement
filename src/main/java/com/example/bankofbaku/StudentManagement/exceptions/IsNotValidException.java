package com.example.bankofbaku.StudentManagement.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class IsNotValidException extends CustomException{
  public IsNotValidException(String message){
      super(message, HttpStatus.FORBIDDEN);
  }

  public IsNotValidException(String message, Map<String,String> params){
      super(message, HttpStatus.FORBIDDEN, params);
  }

}
