package com.danish.student_management.service;

import com.danish.student_management.exception.DuplicateResourceException;
import com.danish.student_management.exception.ResourceNotFoundException;
import com.danish.student_management.model.Course;
import com.danish.student_management.model.Enrollment;
import com.danish.student_management.model.Student;
import com.danish.student_management.repository.CourseRepository;
import com.danish.student_management.repository.EnrollmentRepository;
import com.danish.student_management.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    // Enroll a student into a course
    public Enrollment enrollStudent(Long studentId, Long courseId) {

        // Check student exists
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student", "id", studentId));

        // Check course exists
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course", "id", courseId));

        // Check if already enrolled
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new DuplicateResourceException(
                    "Enrollment", "studentId/courseId",
                    studentId + "/" + courseId);
        }

        // Create enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrolledDate(LocalDate.now());
        enrollment.setStatus(Enrollment.Status.ENROLLED);

        return enrollmentRepository.save(enrollment);
    }

    // Get all enrollments
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    // Get all enrollments for a student
    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        // Verify student exists first
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student", "id", studentId);
        }
        return enrollmentRepository.findByStudentId(studentId);
    }

    // Get all enrollments for a course
    public List<Enrollment> getEnrollmentsByCourse(Long courseId) {
        // Verify course exists first
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course", "id", courseId);
        }
        return enrollmentRepository.findByCourseId(courseId);
    }

    // Drop a student from a course (soft delete — status = DROPPED)
    public Enrollment dropStudent(Long studentId, Long courseId) {
        Enrollment enrollment = enrollmentRepository
                .findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enrollment", "studentId/courseId",
                        studentId + "/" + courseId));

        if (enrollment.getStatus() == Enrollment.Status.DROPPED) {
            throw new IllegalArgumentException(
                    "Student is already dropped from this course");
        }

        enrollment.setStatus(Enrollment.Status.DROPPED);
        return enrollmentRepository.save(enrollment);
    }

    // Complete a course enrollment
    public Enrollment completeEnrollment(Long studentId, Long courseId) {
        Enrollment enrollment = enrollmentRepository
                .findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enrollment", "studentId/courseId",
                        studentId + "/" + courseId));

        enrollment.setStatus(Enrollment.Status.COMPLETED);
        return enrollmentRepository.save(enrollment);
    }

    // Hard delete enrollment
    public void deleteEnrollment(Long id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Enrollment", "id", id);
        }
        enrollmentRepository.deleteById(id);
    }
}