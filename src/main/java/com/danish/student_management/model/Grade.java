package com.danish.student_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    // Many grades belong to one student
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "student_id",
            nullable = false
    )
    private Student student;

    // Many grades belong to one course
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "course_id",
            nullable = false
    )
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