package edu.ccrm.io;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Semester;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImportExportService {

    public List<Student> importStudents(Path filePath) throws IOException {
        List<Student> students = new ArrayList<>();
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.skip(1).forEach(line -> {
                String[] parts = line.split(",");
                // Assuming CSV: id,regNo,fullName,email,dateOfBirth(yyyy-MM-dd)
                if (parts.length >= 5) {
                    Student s = new Student(parts[0], parts[1], parts[2], parts[3], java.time.LocalDate.parse(parts[4]));
                    students.add(s);
                }
            });
        }
        return students;
    }

    public List<Course> importCourses(Path filePath) throws IOException {
        List<Course> courses = new ArrayList<>();
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.skip(1).forEach(line -> {
                String[] parts = line.split(",");
                // Assuming CSV: code,title,credits,instructorId,semester,department
                if (parts.length >= 6) {
                    Course c = new Course(parts[0], parts[1], Integer.parseInt(parts[2]), null,
                            Semester.valueOf(parts[4].toUpperCase()), parts[5]);
                    courses.add(c);
                }
            });
        }
        return courses;
    }

    public void exportStudents(List<Student> students, Path filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("id,regNo,fullName,email,dateOfBirth");
        for (Student s : students) {
            lines.add(String.join(",",
                    s.getId(),
                    s.getRegNo(),
                    s.getFullName(),
                    s.getEmail(),
                    s.getDateOfBirth().toString()));
        }
        Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public void exportCourses(List<Course> courses, Path filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("code,title,credits,instructorId,semester,department");
        for (Course c : courses) {
            lines.add(String.join(",",
                    c.getCode(),
                    c.getTitle(),
                    String.valueOf(c.getCredits()),
                    c.getInstructor() != null ? c.getInstructor().getId() : "",
                    c.getSemester().toString(),
                    c.getDepartment()));
        }
        Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public void backupData(Path sourceDir, Path backupDir) throws IOException {
        if (!Files.exists(backupDir)) {
            Files.createDirectories(backupDir);
        }
        try (Stream<Path> paths = Files.walk(sourceDir)) {
            paths.forEach(source -> {
                try {
                    Path destination = backupDir.resolve(sourceDir.relativize(source));
                    if (Files.isDirectory(source)) {
                        if (!Files.exists(destination)) {
                            Files.createDirectories(destination);
                        }
                    } else {
                        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public long computeDirectorySize(Path dir) throws IOException {
        try (Stream<Path> paths = Files.walk(dir)) {
            return paths.filter(Files::isRegularFile)
                    .mapToLong(p -> {
                        try {
                            return Files.size(p);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return 0L;
                        }
                    }).sum();
        }
    }
}
