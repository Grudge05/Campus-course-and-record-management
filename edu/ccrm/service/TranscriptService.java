package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Enrollment;

public class TranscriptService {
    public String generateTranscript(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append("Transcript for ").append(student.getFullName()).append("\\n");
        sb.append("RegNo: ").append(student.getRegNo()).append("\\n");
        sb.append("Email: ").append(student.getEmail()).append("\\n\\n");
        sb.append("Courses:\\n");
        for (Enrollment e : student.getEnrolledCourses()) {
            sb.append(e.getCourse().getCode())
              .append(" - ")
              .append(e.getCourse().getTitle())
              .append(" (").append(e.getCourse().getCredits()).append(" credits)")
              .append(": ")
              .append(e.getGrade() != null ? e.getGrade().toString() : "Not graded")
              .append("\\n");
        }
        sb.append("\\nGPA: ").append(String.format("%.2f", student.getGPA()));
        return sb.toString();
    }
}
