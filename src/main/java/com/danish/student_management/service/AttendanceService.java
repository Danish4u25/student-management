package com.danish.student_management.service;

import com.danish.student_management.exception.ResourceNotFoundException;
import com.danish.student_management.model.Attendance;
import com.danish.student_management.model.Course;
import com.danish.student_management.model.Student;
import com.danish.student_management.repository.AttendanceRepository;
import com.danish.student_management.repository.CourseRepository;
import com.danish.student_management.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    // Mark attendance for a student
    public Attendance markAttendance(Long studentId, Long courseId,
                                     LocalDate date,
                                     Attendance.Status status,
                                     String remarks) {

        // Validate student exists
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student", "id", studentId));

        // Validate course exists
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course", "id", courseId));

        // Check if attendance already marked for this date
        boolean alreadyMarked = attendanceRepository
                .findByStudentIdAndCourseId(studentId, courseId)
                .stream()
                .anyMatch(a -> a.getDate().equals(date));

        if (alreadyMarked) {
            throw new IllegalArgumentException(
                    "Attendance already marked for student "
                            + studentId + " on " + date);
        }

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setCourse(course);
        attendance.setDate(date);
        attendance.setStatus(status);
        attendance.setRemarks(remarks);

        return attendanceRepository.save(attendance);
    }

    // Get all attendance for a student across all courses
    public List<Attendance> getAttendanceByStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student", "id", studentId);
        }
        return attendanceRepository.findByStudentId(studentId);
    }

    // Get all attendance for a course
    public List<Attendance> getAttendanceByCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course", "id", courseId);
        }
        return attendanceRepository.findByCourseId(courseId);
    }

    // Get attendance for a student in a specific course
    public List<Attendance> getAttendanceByStudentAndCourse(
            Long studentId, Long courseId) {

        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student", "id", studentId);
        }
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course", "id", courseId);
        }
        return attendanceRepository
                .findByStudentIdAndCourseId(studentId, courseId);
    }

    // Calculate attendance percentage for a student in a course
    public double getAttendancePercentage(Long studentId, Long courseId) {
        List<Attendance> all = attendanceRepository
                .findByStudentIdAndCourseId(studentId, courseId);

        if (all.isEmpty()) return 0.0;

        long presentCount = attendanceRepository
                .countByStudentIdAndCourseIdAndStatus(
                        studentId, courseId,
                        Attendance.Status.PRESENT);

        // Late counts as half present
        long lateCount = attendanceRepository
                .countByStudentIdAndCourseIdAndStatus(
                        studentId, courseId,
                        Attendance.Status.LATE);

        double effectivePresent = presentCount + (lateCount * 0.5);
        double percentage = (effectivePresent / all.size()) * 100;

        // Round to 1 decimal place
        return Math.round(percentage * 10.0) / 10.0;
    }

    // Check if student attendance is below 75% (at risk)
    public boolean isAtRisk(Long studentId, Long courseId) {
        return getAttendancePercentage(studentId, courseId) < 75.0;
    }

    // Update an existing attendance record
    public Attendance updateAttendance(Long attendanceId,
                                       Attendance.Status status,
                                       String remarks) {
        Attendance attendance = attendanceRepository
                .findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Attendance", "id", attendanceId));

        attendance.setStatus(status);
        attendance.setRemarks(remarks);
        return attendanceRepository.save(attendance);
    }

    // Delete attendance record
    public void deleteAttendance(Long id) {
        if (!attendanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Attendance", "id", id);
        }
        attendanceRepository.deleteById(id);
    }
}