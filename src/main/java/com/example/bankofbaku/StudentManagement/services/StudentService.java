package com.example.bankofbaku.StudentManagement.services;

import com.example.bankofbaku.StudentManagement.dto.StudentDto;
import com.example.bankofbaku.StudentManagement.entity.Student;
import com.example.bankofbaku.StudentManagement.exceptions.NotFoundException;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface StudentService {
    List<StudentDto> getAllStudents();
    StudentDto addStudent(Student std) throws Exception;
     StudentDto convertIntoDto(Student student);
     ResponseEntity<Optional<StudentDto>> findByIdAndStatusTrue(Long id) throws NotFoundException;
    List<StudentDto> getByNameOrLastnameOrEmail(String keyword) throws NotFoundException;
    ResponseEntity<StudentDto> findByEmail(String email) throws NotFoundException;
     StudentDto updateStudent(Long id, Student newStd) throws Exception;
     void deleteById( Long id) throws Exception;
     void deleteAll();
}
