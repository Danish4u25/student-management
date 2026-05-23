package com.danish.student_management.service;

import com.danish.student_management.exception.DuplicateResourceException;
import com.danish.student_management.exception.ResourceNotFoundException;
import com.danish.student_management.model.Student;
import com.danish.student_management.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get single student by ID
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
    }

    // Create new student
    public Student createStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new DuplicateResourceException("Student", "email", student.getEmail());
        }
        return studentRepository.save(student);
    }

    // Update existing student
    public Student updateStudent(Long id, Student updatedStudent) {
        Student existing = getStudentById(id);
        existing.setFirstName(updatedStudent.getFirstName());
        existing.setLastName(updatedStudent.getLastName());
        existing.setEmail(updatedStudent.getEmail());
        existing.setPhone(updatedStudent.getPhone());
        existing.setRollNumber(updatedStudent.getRollNumber());
        existing.setSemester(updatedStudent.getSemester());
        existing.setDateOfBirth(updatedStudent.getDateOfBirth());
        existing.setStatus(updatedStudent.getStatus());
        return studentRepository.save(existing);
    }

    // Delete student
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    // Search students by name
    public List<Student> searchStudents(String name) {
        return studentRepository.findByFirstNameContainingIgnoreCase(name);
    }

    // Get students by semester
    public List<Student> getStudentsBySemester(Integer semester) {
        return studentRepository.findBySemester(semester);
    }
}