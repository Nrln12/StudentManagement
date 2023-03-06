package com.example.bankofbaku.StudentManagement.advice;

import com.example.bankofbaku.StudentManagement.exceptions.AlreadyExistsException;
import com.example.bankofbaku.StudentManagement.exceptions.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)

    public @ResponseBody ResponseEntity<?> handleException(final CustomException exception, WebRequest request){
        log.error("CustomException -> ExceptionHandler -> {} {}", exception, request);
        ErrorDetail errorDetail = new ErrorDetail(LocalDateTime.now(),
                exception.getStatus().getReasonPhrase(),
                exception.getMessage(),
                request.getDescription(false),
                exception.getStatus(),
                exception.getStatus().value()
                );
        return new ResponseEntity<>(errorDetail,exception.getStatus());
    }
    @ExceptionHandler(Exception.class)
    public @ResponseBody ResponseEntity<?> handleException(Exception exception, WebRequest request){
        log.error("GlobalExceptionHandler -> ExceptionHandler -> {} {}", exception, request);
        exception.getStackTrace();
        ErrorDetail errorDetail = new ErrorDetail(LocalDateTime.now(),
                exception.toString(),
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR, 500);
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException exception, WebRequest request) {
        log.error("GlobalExceptionHandler -> ExceptionHandler -> {} {}", exception, request);
        exception.printStackTrace();
        ErrorDetail errorDetail = new ErrorDetail(LocalDateTime.now(),
                exception.toString(),
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.BAD_REQUEST, 400);
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }
    }

