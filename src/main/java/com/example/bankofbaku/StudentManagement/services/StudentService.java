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
    public StudentDto convertIntoDto(Student student);
    public ResponseEntity<Optional<StudentDto>> findByIdAndStatusTrue(Long id) throws NotFoundException;
    public ResponseEntity<StudentDto> findByEmail(String email) throws NotFoundException;
    public StudentDto updateStudent(Long id, Student newStd) throws Exception;
    public void deleteById( Long id) throws Exception;
    public void deleteAll();
}
