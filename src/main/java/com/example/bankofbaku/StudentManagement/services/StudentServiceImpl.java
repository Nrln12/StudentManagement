package com.example.bankofbaku.StudentManagement.services;

import com.example.bankofbaku.StudentManagement.dto.StudentDto;
import com.example.bankofbaku.StudentManagement.entity.Student;
import com.example.bankofbaku.StudentManagement.exceptions.AlreadyExistsException;
import com.example.bankofbaku.StudentManagement.exceptions.IsNotValidException;
import com.example.bankofbaku.StudentManagement.exceptions.NotFoundException;
import com.example.bankofbaku.StudentManagement.repositories.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        return new StudentDto(student.getFirstName(), student.getLastName(), student.getEmail(), student.getPassword());
    }

    public StudentDto addStudent(Student std) throws Exception {

        Optional<Student> currStd = studentRepository.findByEmail(std.getEmail());

        if (currStd.isPresent()) {
            throw new AlreadyExistsException("Email has already exists");
        } else {
            if(!checkEmail(std)){
                throw new IsNotValidException("Your email is not valid");
            }else if(!isValidPassword(std)){
                throw new IsNotValidException("The password is not valid");
            }
            else{
                studentRepository.save(std);
            }
        }
        return convertIntoDto(std);
    }

    public ResponseEntity<Optional<StudentDto>> findByIdAndStatusTrue(Long id) throws NotFoundException {
        Optional<StudentDto> std = Optional.ofNullable(studentRepository.findByIdAndStatusTrue(id).
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
        Optional<Student> currStd = studentRepository.findByIdAndStatusTrue(id);
        if (currStd.isEmpty()) {
            throw new NotFoundException("Student not found");
        }
        Student student = currStd.get();
        try {
            if(!checkEmail(newStd)){
                throw new IsNotValidException("Email is not valid");

            }
            if(!isValidPassword(newStd)){
                throw new IsNotValidException("Password is not valid");
            }
           else{
                student.setFirstName(newStd.getFirstName());
                student.setLastName(newStd.getLastName());
                student.setEmail(newStd.getEmail());
                student.setPassword(newStd.getPassword());
                student.setId(id);
                studentRepository.save(student);
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return convertIntoDto(student);
    }

    public void deleteById(Long id) throws NotFoundException {
        Optional<Student> currStd = studentRepository.findByIdAndStatusTrue(id);
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
public boolean checkEmail(Student std){
        boolean isValid=true;
        if(!(std.getEmail().contains("@") && std.getEmail().contains(".")))
            isValid=false;
        return isValid;
}

public boolean isValidPassword(Student std){
    String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
    Pattern p = Pattern.compile(regex);
    if(std.getPassword()==null){
        return false;
    }
    Matcher m =p.matcher(std.getPassword());
    return m.matches();
}
//public HashMap<Boolean, String> isValidPassword(Student std){
//       boolean isValid=true;
//       String message="";
//       HashMap<Boolean, String> response= new HashMap<Boolean, String>();
//       if(std.getPassword().length()>=8 && std.getPassword().length()<17){
//           isValid=true;
//           message="Length of the password must be between 8 and 16";
//       }
//       String upperCaseChars =  "(.*[A-Z].*)";
//       if(!std.getPassword().contains(upperCaseChars)){
//           isValid=false;
//           message="Password must contain at least one upper case character";
//       }
//        String lowerCaseChars="(.*[a-z].*)";
//       if(!std.getPassword().contains(lowerCaseChars)){
//           isValid=false;
//           message="Password must contain at least one lower case character";
//       }
//       String numbers="(.*[0-9].*)";
//       if(!std.getPassword().contains(numbers)){
//           isValid=false;
//           message="Password must contain at least one number";
//       }
//       String specialChars="(.*[@,#,$,%].*$)";
//       if(!std.getPassword().contains(specialChars)){
//           isValid=false;
//           message="Password must contain at least one special character";
//       }
//       response.put(isValid,message);
//       return response;
//}
}
