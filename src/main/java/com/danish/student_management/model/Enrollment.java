package com.danish.student_management.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
        name = "enrollments",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "student_id",
                                "course_id"
                        }
                )
        }
)
@Getter
@Setter
public class Enrollment {

    @Id
    @GeneratedValue(
            strategy =
                    GenerationType.IDENTITY
    )
    private Long id;

    // Many enrollments belong to one student
    @JsonBackReference
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "student_id",
            nullable = false
    )
    private Student student;

    // Many enrollments belong to one course
    @JsonBackReference
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "course_id",
            nullable = false
    )
    private Course course;

    private LocalDate enrolledDate =
            LocalDate.now();

    @Enumerated(
            EnumType.STRING
    )
    private Status status =
            Status.ENROLLED;

    public enum Status {

        ENROLLED,
        DROPPED,
        COMPLETED
    }
}