package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private String regNo;
    private boolean active;
    private List<Enrollment> enrolledCourses;

    public Student(String id, String regNo, String fullName, String email, LocalDate dateOfBirth) {
        super(id, fullName, email, dateOfBirth);
        this.regNo = regNo;
        this.active = true;
        this.enrolledCourses = new ArrayList<>();
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Enrollment> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void enroll(Enrollment enrollment) {
        enrolledCourses.add(enrollment);
    }

    public void unenroll(Enrollment enrollment) {
        enrolledCourses.remove(enrollment);
    }

    public double getTotalCredits() {
        return enrolledCourses.stream()
                .mapToDouble(e -> e.getCourse().getCredits())
                .sum();
    }

    public double getGPA() {
        if (enrolledCourses.isEmpty()) return 0.0;
        double totalPoints = enrolledCourses.stream()
                .filter(e -> e.getGrade() != null)
                .mapToDouble(e -> e.getGrade().getGradePoint() * e.getCourse().getCredits())
                .sum();
        double totalCredits = enrolledCourses.stream()
                .filter(e -> e.getGrade() != null)
                .mapToDouble(e -> e.getCourse().getCredits())
                .sum();
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    @Override
    public String getProfile() {
        return "Student Profile: " + getFullName() + " (RegNo: " + regNo + ", Active: " + active + ")";
    }

    @Override
    public String toString() {
        return "Student{" +
                "Id='" + getId() + '\'' +
                ", RegNo='" + regNo + '\'' +
                ", FullName='" + getFullName() + '\'' +
                ", Email='" + getEmail() + '\'' +
                ", Active=" + active +
                ", EnrolledCourses=" + enrolledCourses.size() +
                '}';
    }
}
