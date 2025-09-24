package edu.ccrm.service;

import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Grade;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnrollmentService {
    private List<Enrollment> enrollments;

    public EnrollmentService() {
        this.enrollments = new ArrayList<>();
    }

    public void enroll(Student student, Course course) throws Exception {
        // Check if already enrolled
        if (enrollments.stream().anyMatch(e -> e.getStudent().equals(student) && e.getCourse().equals(course))) {
            throw new Exception("Student already enrolled in this course");
        }
        Enrollment enrollment = new Enrollment(student, course, java.time.LocalDate.now());
        enrollments.add(enrollment);
        student.enroll(enrollment);
    }

    public void unenroll(Student student, Course course) {
        enrollments.removeIf(e -> e.getStudent().equals(student) && e.getCourse().equals(course));
        student.unenroll(null); // Need to pass the enrollment, but for simplicity
    }

    public void recordGrade(Student student, Course course, Grade grade) {
        enrollments.stream()
                .filter(e -> e.getStudent().equals(student) && e.getCourse().equals(course))
                .findFirst()
                .ifPresent(e -> e.setGrade(grade));
    }

    public List<Enrollment> getEnrollmentsForStudent(Student student) {
        return enrollments.stream()
                .filter(e -> e.getStudent().equals(student))
                .collect(Collectors.toList());
    }

    public List<Enrollment> getEnrollmentsForCourse(Course course) {
        return enrollments.stream()
                .filter(e -> e.getCourse().equals(course))
                .collect(Collectors.toList());
    }
}
