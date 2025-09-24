package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Semester;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CourseService {
    private List<Course> courses;

    public CourseService() {
        this.courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public List<Course> listCourses() {
        return courses;
    }

    public Optional<Course> findCourseByCode(String code) {
        return courses.stream()
                .filter(c -> c.getCode().equals(code))
                .findFirst();
    }

    public void updateCourse(Course updatedCourse) {
        findCourseByCode(updatedCourse.getCode()).ifPresent(course -> {
            course.setTitle(updatedCourse.getTitle());
            course.setCredits(updatedCourse.getCredits());
            course.setInstructor(updatedCourse.getInstructor());
            course.setSemester(updatedCourse.getSemester());
            course.setDepartment(updatedCourse.getDepartment());
            course.setActive(updatedCourse.isActive());
        });
    }

    public void deactivateCourse(String code) {
        findCourseByCode(code).ifPresent(course -> course.setActive(false));
    }

    public List<Course> searchCourses(String query) {
        return courses.stream()
                .filter(c -> c.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                             c.getCode().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Course> filterByInstructor(Instructor instructor) {
        return courses.stream()
                .filter(c -> instructor.equals(c.getInstructor()))
                .collect(Collectors.toList());
    }

    public List<Course> filterByDepartment(String department) {
        return courses.stream()
                .filter(c -> department.equals(c.getDepartment()))
                .collect(Collectors.toList());
    }

    public List<Course> filterBySemester(Semester semester) {
        return courses.stream()
                .filter(c -> semester.equals(c.getSemester()))
                .collect(Collectors.toList());
    }

    public void assignInstructor(Course course, Instructor instructor) {
        course.setInstructor(instructor);
        instructor.assignCourse(course);
    }
}
