package com.example.bankofbaku.StudentManagement.controller;

import com.example.bankofbaku.StudentManagement.dto.StudentDto;
import com.example.bankofbaku.StudentManagement.entity.Student;
import com.example.bankofbaku.StudentManagement.services.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    StudentServiceImpl studentServiceImpl; // interfeysi cagirsin

    @GetMapping
    public List<StudentDto> getAllStudents(){
        return  studentServiceImpl.getAllStudents();
    }
    @PostMapping
    public StudentDto addStudent(@RequestBody Student std) throws Exception {
        return  studentServiceImpl.addStudent(std);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<StudentDto>> findById(@PathVariable Long id) throws Exception {
        return studentServiceImpl.findById(id);

    }


    @PutMapping("/{id}")
    public StudentDto updateStudent(@PathVariable Long id, @RequestBody Student newStd) throws Exception {
        return studentServiceImpl.updateStudent(id,newStd);
    }

    @PutMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) throws Exception{
        studentServiceImpl.deleteById(id);
    }

    @PutMapping("/delete-students")
    public void deleteAll(){
        studentServiceImpl.deleteAll();
    }
}
