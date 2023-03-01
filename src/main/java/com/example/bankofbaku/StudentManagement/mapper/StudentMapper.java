package com.example.bankofbaku.StudentManagement.mapper;

import com.example.bankofbaku.StudentManagement.dto.StudentDto;
import com.example.bankofbaku.StudentManagement.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentDto studentDto(Student student){
        return new StudentDto(student.getFirstName(),student.getLastName());
    };
}
