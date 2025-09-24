package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Grade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class StudentService {
    private List<Student> students;

    public StudentService() {
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> listStudents() {
        return students;
    }

    public Optional<Student> findStudentById(String id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    public void updateStudent(Student updatedStudent) {
        findStudentById(updatedStudent.getId()).ifPresent(student -> {
            student.setFullName(updatedStudent.getFullName());
            student.setEmail(updatedStudent.getEmail());
            student.setActive(updatedStudent.isActive());
        });
    }

    public void deactivateStudent(String id) {
        findStudentById(id).ifPresent(student -> student.setActive(false));
    }

    public void enrollStudent(Student student, Course course) throws Exception {
        // Business rule: max credits per semester (e.g., 18)
        int maxCredits = 18;
        double currentCredits = student.getTotalCredits();
        if (currentCredits + course.getCredits() > maxCredits) {
            throw new Exception("Max credit limit exceeded");
        }
        Enrollment enrollment = new Enrollment(student, course, java.time.LocalDate.now());
        student.enroll(enrollment);
    }

    public void unenrollStudent(Student student, Course course) {
        student.getEnrolledCourses().removeIf(e -> e.getCourse().equals(course));
    }

    public void recordGrade(Student student, Course course, Grade grade) {
        student.getEnrolledCourses().stream()
                .filter(e -> e.getCourse().equals(course))
                .findFirst()
                .ifPresent(e -> e.setGrade(grade));
    }

    public String printTranscript(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append("Transcript for ").append(student.getFullName()).append("\\n");
        for (Enrollment e : student.getEnrolledCourses()) {
            sb.append(e.getCourse().getCode())
              .append(" - ")
              .append(e.getCourse().getTitle())
              .append(": ")
              .append(e.getGrade() != null ? e.getGrade().toString() : "Not graded")
              .append("\\n");
        }
        sb.append("GPA: ").append(String.format("%.2f", student.getGPA()));
        return sb.toString();
    }
}
