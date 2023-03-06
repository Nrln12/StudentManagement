package com.example.bankofbaku.StudentManagement.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetail {
    private LocalDateTime timestamp;
    private String exception;
    private String message;
    private String path;
    private HttpStatus status;
    private int code;

}
