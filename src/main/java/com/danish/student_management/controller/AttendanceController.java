package com.danish.student_management.controller;

import com.danish.student_management.model.Attendance;
import com.danish.student_management.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/mark")
    public ResponseEntity<Attendance> markAttendance(
            @RequestParam Long studentId,
            @RequestParam Long courseId,
            @RequestParam String date,
            @RequestParam Attendance.Status status,
            @RequestParam(required = false) String remarks) {

        Attendance attendance = attendanceService.markAttendance(
                studentId,
                courseId,
                LocalDate.parse(date),
                status,
                remarks
        );

        return ResponseEntity.status(201).body(attendance);
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<List<Attendance>> getByStudentAndCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceByStudentAndCourse(
                        studentId, courseId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> getByStudent(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceByStudent(studentId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Attendance>> getByCourse(
            @PathVariable Long courseId) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceByCourse(courseId));
    }

    @GetMapping("/percentage")
    public ResponseEntity<Map<String,Object>> getAttendancePercentage(
            @RequestParam Long studentId,
            @RequestParam Long courseId) {

        double percentage =
                attendanceService.getAttendancePercentage(
                        studentId,
                        courseId
                );

        boolean atRisk =
                attendanceService.isAtRisk(
                        studentId,
                        courseId
                );

        return ResponseEntity.ok(Map.of(
                "studentId", studentId,
                "courseId", courseId,
                "attendancePercentage", percentage,
                "atRisk", atRisk,
                "message", atRisk
                        ? "WARNING: Attendance below 75%"
                        : "Attendance is satisfactory"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attendance> updateAttendance(
            @PathVariable Long id,
            @RequestParam Attendance.Status status,
            @RequestParam(required = false) String remarks) {

        return ResponseEntity.ok(
                attendanceService.updateAttendance(
                        id,
                        status,
                        remarks
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(
            @PathVariable Long id) {

        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}