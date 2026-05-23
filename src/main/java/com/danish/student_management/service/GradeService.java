package com.danish.student_management.service;

import com.danish.student_management.exception.ResourceNotFoundException;
import com.danish.student_management.model.Course;
import com.danish.student_management.model.Grade;
import com.danish.student_management.model.Student;
import com.danish.student_management.repository.CourseRepository;
import com.danish.student_management.repository.GradeRepository;
import com.danish.student_management.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    // Enter or update grade for a student in a course
    public Grade saveGrade(Long studentId, Long courseId,
                           Double internal, Double midterm,
                           Double finalMarks) {

        // Validate student exists
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student", "id", studentId));

        // Validate course exists
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course", "id", courseId));

        // Validate marks ranges
        if (internal < 0 || internal > 30) {
            throw new IllegalArgumentException(
                    "Internal marks must be between 0 and 30");
        }
        if (midterm < 0 || midterm > 30) {
            throw new IllegalArgumentException(
                    "Midterm marks must be between 0 and 30");
        }
        if (finalMarks < 0 || finalMarks > 40) {
            throw new IllegalArgumentException(
                    "Final marks must be between 0 and 40");
        }

        // Check if grade already exists — update it, don't create duplicate
        Grade grade = gradeRepository
                .findByStudentIdAndCourseId(studentId, courseId)
                .orElse(new Grade());

        grade.setStudent(student);
        grade.setCourse(course);
        grade.setInternalMarks(internal);
        grade.setMidtermMarks(midterm);
        grade.setFinalMarks(finalMarks);

        // Auto-calculate total
        double total = internal + midterm + finalMarks;
        grade.setTotalMarks(total);

        // Auto-calculate letter grade
        grade.setLetterGrade(calculateLetterGrade(total));

        // Auto-calculate GPA
        grade.setGpa(calculateGpa(total));

        grade.setStatus(Grade.Status.SUBMITTED);

        return gradeRepository.save(grade);
    }

    // Get all grades for a student
    public List<Grade> getGradesByStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student", "id", studentId);
        }
        return gradeRepository.findByStudentId(studentId);
    }

    // Get all grades for a course
    public List<Grade> getGradesByCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course", "id", courseId);
        }
        return gradeRepository.findByCourseId(courseId);
    }

    // Get specific grade for a student in a course
    public Grade getGradeByStudentAndCourse(Long studentId, Long courseId) {
        return gradeRepository
                .findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Grade", "studentId/courseId",
                        studentId + "/" + courseId));
    }

    // Publish grades — makes them visible to student
    public Grade publishGrade(Long studentId, Long courseId) {
        Grade grade = getGradeByStudentAndCourse(studentId, courseId);

        if (grade.getStatus() == Grade.Status.PENDING) {
            throw new IllegalArgumentException(
                    "Cannot publish grade — marks not entered yet");
        }

        grade.setStatus(Grade.Status.PUBLISHED);
        return gradeRepository.save(grade);
    }

    // Delete grade
    public void deleteGrade(Long id) {
        if (!gradeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Grade", "id", id);
        }
        gradeRepository.deleteById(id);
    }

    // ── Private helper methods ──────────────────────────────

    // Letter grade calculation (total out of 100)
    private String calculateLetterGrade(double total) {
        if (total >= 90) return "A+";
        else if (total >= 85) return "A";
        else if (total >= 80) return "A-";
        else if (total >= 75) return "B+";
        else if (total >= 70) return "B";
        else if (total >= 65) return "B-";
        else if (total >= 60) return "C+";
        else if (total >= 55) return "C";
        else if (total >= 50) return "C-";
        else return "F";
    }

    // GPA calculation (4.0 scale)
    private Double calculateGpa(double total) {
        if (total >= 90) return 4.0;
        else if (total >= 85) return 3.7;
        else if (total >= 80) return 3.3;
        else if (total >= 75) return 3.0;
        else if (total >= 70) return 2.7;
        else if (total >= 65) return 2.3;
        else if (total >= 60) return 2.0;
        else if (total >= 55) return 1.7;
        else if (total >= 50) return 1.0;
        else return 0.0;
    }
}