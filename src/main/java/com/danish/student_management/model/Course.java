package com.danish.student_management.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String courseName;

    @Column(unique = true, nullable = false)
    private String courseCode;

    private Integer credits;
    private Integer semester;
    private String schedule;      // e.g. "Mon/Wed 10:00-11:30"
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    public enum Status { ACTIVE, INACTIVE }

    // Many courses can be taught by one teacher
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private User teacher;

    // One course has many enrollments
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;

    // One course has many grades
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Grade> grades;

    // One course has many attendance records
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Attendance> attendances;
}