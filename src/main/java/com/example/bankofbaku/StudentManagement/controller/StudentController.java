package com.example.bankofbaku.StudentManagement.controller;

import com.example.bankofbaku.StudentManagement.dto.StudentDto;
import com.example.bankofbaku.StudentManagement.entity.Student;
import com.example.bankofbaku.StudentManagement.exceptions.NotFoundException;
import com.example.bankofbaku.StudentManagement.services.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
private final StudentServiceImpl studentServiceImpl;
    @GetMapping
    public List<StudentDto> getAllStudents(){
        return (List<StudentDto>) studentServiceImpl.getAllStudents();
    }
    @PostMapping("/add-new-student")
    public StudentDto addStudent(@RequestBody Student std) throws Exception {
        return  studentServiceImpl.addStudent(std);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<StudentDto>> findById(@PathVariable Long id) throws NotFoundException {
        return studentServiceImpl.findByIdAndStatusTrue(id);

    }
    @GetMapping("/email/{email}")
    public ResponseEntity<StudentDto> findByEmail(@PathVariable String email) throws NotFoundException{
        return studentServiceImpl.findByEmail(email);
    }
    //Change RequestBody StudentDto
    @GetMapping("/search/{value}")
    public List<StudentDto> findByFirstNameOrLastNameOrEmail(@PathVariable String value) throws NotFoundException{
        return studentServiceImpl.findByFirstNameOrLastnameOrEmail(value);
    }
    @PutMapping("/{id}")
    public StudentDto updateStudent(@PathVariable Long id, @RequestBody Student newStd) throws Exception {
        return studentServiceImpl.updateStudent(id,newStd);
    }

    @PutMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) throws NotFoundException{
        studentServiceImpl.deleteById(id);
    }

    @PutMapping("/delete-students")
    public void deleteAll(){
        studentServiceImpl.deleteAll();
    }
}
