package com.danish.student_management.repository;

import com.danish.student_management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByFirstNameContainingIgnoreCase(String name);

    Optional<Student> findByEmail(String email);

    List<Student> findBySemester(Integer semester);

    boolean existsByEmail(String email);
}