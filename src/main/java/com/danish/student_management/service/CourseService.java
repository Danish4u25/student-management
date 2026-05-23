package com.danish.student_management.service;

import com.danish.student_management.exception.DuplicateResourceException;
import com.danish.student_management.exception.ResourceNotFoundException;
import com.danish.student_management.model.Course;
import com.danish.student_management.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    // Get all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Get course by ID
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course", "id", id));
    }

    // Create new course
    public Course createCourse(Course course) {
        if (courseRepository.existsByCourseCode(course.getCourseCode())) {
            throw new DuplicateResourceException(
                    "Course", "courseCode", course.getCourseCode());
        }
        return courseRepository.save(course);
    }

    // Update course
    public Course updateCourse(Long id, Course updatedCourse) {
        Course existing = getCourseById(id);
        existing.setCourseName(updatedCourse.getCourseName());
        existing.setCourseCode(updatedCourse.getCourseCode());
        existing.setCredits(updatedCourse.getCredits());
        existing.setSemester(updatedCourse.getSemester());
        existing.setSchedule(updatedCourse.getSchedule());
        existing.setDescription(updatedCourse.getDescription());
        existing.setStatus(updatedCourse.getStatus());
        return courseRepository.save(existing);
    }

    // Delete course
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course", "id", id);
        }
        courseRepository.deleteById(id);
    }

    // Get courses by semester
    public List<Course> getCoursesBySemester(Integer semester) {
        return courseRepository.findBySemester(semester);
    }
}