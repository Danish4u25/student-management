package com.danish.student_management.repository;

import com.danish.student_management.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentId(Long studentId);
    List<Attendance> findByCourseId(Long courseId);
    List<Attendance> findByStudentIdAndCourseId(Long studentId, Long courseId);
    long countByStudentIdAndCourseIdAndStatus(
            Long studentId, Long courseId,
            Attendance.Status status
    );
}