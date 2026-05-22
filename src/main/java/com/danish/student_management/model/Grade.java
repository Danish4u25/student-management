package com.danish.student_management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "grades")
@Getter
@Setter
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private Double internalMarks;

    private Double midtermMarks;

    private Double finalMarks;

    private Double totalMarks;

    private Double gpa;

    private String letterGrade;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING,
        SUBMITTED,
        PUBLISHED
    }
}