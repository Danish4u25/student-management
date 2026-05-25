package com.danish.student_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "students")
@Data
public class Student {

    @Id
    @GeneratedValue(
            strategy =
                    GenerationType.IDENTITY
    )
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(
            unique = true,
            nullable = false
    )
    private String email;

    private String phone;

    private String rollNumber;

    private Integer semester;

    private LocalDate dateOfBirth;

    @Enumerated(
            EnumType.STRING
    )
    @Column(nullable = false)
    private Status status =
            Status.ACTIVE;

    public enum Status {

        ACTIVE,
        INACTIVE
    }

    // One student has many enrollments
    @JsonIgnore
    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    private List<Enrollment> enrollments;

    // One student has many grades
    @JsonIgnore
    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    private List<Grade> grades;

    // One student has many attendance records
    @JsonIgnore
    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    private List<Attendance> attendances;

    // One student has one user account
    @JsonIgnore
    @OneToOne(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    private User user;
}