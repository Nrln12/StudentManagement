package com.example.bankofbaku.StudentManagement.repositories;

import com.example.bankofbaku.StudentManagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByStatus(boolean isTrue);
    Optional<Student> findByEmail(String email);
    Optional<Student> findByIdAndStatusTrue(Long id); // bunu istifade et


}
