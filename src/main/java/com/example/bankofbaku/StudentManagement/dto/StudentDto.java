package com.example.bankofbaku.StudentManagement.dto;

import lombok.*;

@Data
public class StudentDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public StudentDto(String firstName, String lastName, String email, String password) {
    }
}
