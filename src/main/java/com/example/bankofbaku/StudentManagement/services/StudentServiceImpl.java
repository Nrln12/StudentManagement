package com.example.bankofbaku.StudentManagement.services;

import com.example.bankofbaku.StudentManagement.dto.StudentDto;
import com.example.bankofbaku.StudentManagement.entity.Student;
import com.example.bankofbaku.StudentManagement.repositories.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl { // implement etsin

    private final StudentRepository studentRepository; // best practice construction injection

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentDto> getAllStudents(){

        return studentRepository.findByStatus(true).
                                                  stream().map(this::convertIntoDto).
                                                  collect(Collectors.toList());
    }
    private StudentDto convertIntoDto(Student student){
        return new StudentDto(student.getFirstName(),student.getLastName());
    }

    public StudentDto addStudent(Student std) throws Exception {
        try {
            studentRepository.save(std);
        } catch (Exception ex) {
            throw new Exception();
        }
        return convertIntoDto(std);
    }

    public ResponseEntity<Optional<StudentDto>> findById(Long id) throws Exception {
        Optional<StudentDto> std = Optional.ofNullable(studentRepository.findById(id).
                orElseThrow(() -> new Exception(id + " Student not found"))).stream().map(this::convertIntoDto).findFirst();
        return ResponseEntity.ok().body(std);
    }
    public StudentDto updateStudent(Long id, Student newStd) throws Exception {
        Optional<Student> currStd=studentRepository.findById(id);
        if(currStd.isEmpty()){
            throw new Exception("Not found");
        }
        Student student = currStd.get();
        try{
                student.setFirstName(newStd.getFirstName());
                student.setLastName(newStd.getLastName());
                student.setId(id);
                studentRepository.save(student);

        }catch (Exception e){
            throw new Exception(e);
        }
        return convertIntoDto(student);
    }
    public void deleteById( Long id) throws Exception{
        Optional<Student> currStd=studentRepository.findById(id);
        Student student = (Student) currStd.get(); // duzelis
        try{
            if(currStd.isPresent()) { // duzelis
                student.setStatus(false);
                studentRepository.save(student);
            }
        }catch (Exception e){
            throw new Exception(e);
        }
    }
    public void deleteAll(){
        List<Student> allStds=studentRepository.findAll();
        for (Student allStd : allStds) {
            allStd.setStatus(false);
            studentRepository.save(allStd);
        }
    }


}
