package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Instructor extends Person {
    private List<Course> assignedCourses;

    public Instructor(String id, String fullName, String email, LocalDate dateOfBirth) {
        super(id, fullName, email, dateOfBirth);
        this.assignedCourses = new ArrayList<>();
    }

    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }

    public void assignCourse(Course course) {
        assignedCourses.add(course);
    }

    public void unassignCourse(Course course) {
        assignedCourses.remove(course);
    }

    @Override
    public String getProfile() {
        return "Instructor Profile: " + getFullName() + " (Courses: " + assignedCourses.size() + ")";
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "id='" + getId() + '\'' +
                ", FullName='" + getFullName() + '\'' +
                ", Email='" + getEmail() + '\'' +
                ", AssignedCourses=" + assignedCourses.size() +
                '}';
    }
}
