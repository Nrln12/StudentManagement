package com.example.bankofbaku.StudentManagement.services;

import com.example.bankofbaku.StudentManagement.dto.StudentDto;
import com.example.bankofbaku.StudentManagement.entity.Student;
import com.example.bankofbaku.StudentManagement.exceptions.AlreadyExistsException;
import com.example.bankofbaku.StudentManagement.exceptions.NotFoundException;
import com.example.bankofbaku.StudentManagement.repositories.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentDto> getAllStudents() {
        List<StudentDto> stdDtos = studentRepository.findByStatus(true).
                stream().map(this::convertIntoDto).collect(Collectors.toList());
        if (stdDtos.size() == 0) {
            throw new NotFoundException("No data found");
        }
        return stdDtos;
    }

    public StudentDto convertIntoDto(Student student) {
        return new StudentDto(student.getFirstName(), student.getLastName(), student.getEmail());
    }

    public StudentDto addStudent(Student std) throws Exception {

        Optional<Student> currStd = studentRepository.findByEmail(std.getEmail());
        if (currStd.isPresent()) {
            throw new AlreadyExistsException("Email has already exists");
        } else {
            studentRepository.save(std);
        }
        return convertIntoDto(std);
    }

    public ResponseEntity<Optional<StudentDto>> findById(Long id) throws NotFoundException {
        Optional<StudentDto> std = Optional.ofNullable(studentRepository.findById(id).
                orElseThrow(() -> new NotFoundException(id + " Student not found"))).stream().map(this::convertIntoDto).findFirst();
        return ResponseEntity.ok().body(std);
    }

    @Override
    public ResponseEntity<StudentDto> findByEmail(String email) throws NotFoundException {

        Optional<Student> byEmail = studentRepository.findByEmail(email);
        if(byEmail.isEmpty()){
            throw new NotFoundException("student not found");
        }
        StudentDto studentDto = convertIntoDto(byEmail.get());
        return ResponseEntity.ok().body(studentDto);
    }


    public StudentDto updateStudent(Long id, Student newStd) throws Exception {
//        Optional<Student> currStd = studentRepository.findById(id);
//        if(currStd.isPresent()){
//            Student std=currStd.get();
//            std.setFirstName(newStd.getFirstName());
//            std.setLastName(newStd.getLastName());
//            std.setEmail(newStd.getEmail());
//            std.setId(id);
//            studentRepository.save(std);
//        }

        Optional<Student> currStd = studentRepository.findById(id);
        if (currStd.isEmpty()) {
            throw new NotFoundException("Student not found");
        }
        Student student = currStd.get();
        try {
            student.setFirstName(newStd.getFirstName());
            student.setLastName(newStd.getLastName());
            student.setEmail(newStd.getEmail());
            student.setId(id);
            studentRepository.save(student);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return convertIntoDto(student);
    }

    public void deleteById(Long id) throws NotFoundException {
        Optional<Student> currStd = studentRepository.findById(id);
        Student student = (Student) currStd.get(); // duzelis
        try {
            if (currStd.isPresent()) { // duzelis
                student.setStatus(false);
                studentRepository.save(student);
            }
        } catch (Exception e) {
            throw new NotFoundException("Data not found");
        }
    }

    public void deleteAll() {
        List<Student> allStds = studentRepository.findAll();
        for (Student allStd : allStds) {
            allStd.setStatus(false);
            studentRepository.save(allStd);
        }
    }


}
