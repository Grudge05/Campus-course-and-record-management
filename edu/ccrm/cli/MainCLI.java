package edu.ccrm.cli;

import edu.ccrm.domain.*;
import edu.ccrm.service.*;
import edu.ccrm.io.*;
import edu.ccrm.util.Validators;

import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class MainCLI {
    private static StudentService studentService = new StudentService();
    private static CourseService courseService = new CourseService();
    private static EnrollmentService enrollmentService = new EnrollmentService();
    private static TranscriptService transcriptService = new TranscriptService();
    private static ImportExportService importExportService = new ImportExportService();
    private static BackupService backupService = new BackupService(Paths.get("data"));

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Campus Course & Records Manager (CCRM)");
        // Load initial data
        try {
            var students = importExportService.importStudents(Paths.get("test-data/students.csv"));
            students.forEach(studentService::addStudent);
            var courses = importExportService.importCourses(Paths.get("test-data/courses.csv"));
            courses.forEach(courseService::addCourse);
            System.out.println("Initial data loaded from CSV files.");
        } catch (Exception e) {
            System.out.println("Error loading initial data: " + e.getMessage());
        }
        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> manageStudents();
                case "2" -> manageCourses();
                case "3" -> manageEnrollment();
                case "4" -> manageGrades();
                case "5" -> importExportData();
                case "6" -> backupData();
                case "7" -> {
                    printPlatformNote();
                    running = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
        System.out.println("Exiting CCRM. Goodbye!");
    }

    private static void printMainMenu() {
        System.out.println("\\nMain Menu:");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollment");
        System.out.println("4. Manage Grades");
        System.out.println("5. Import/Export Data");
        System.out.println("6. Backup & Show Backup Size");
        System.out.println("7. Exit");
        System.out.print("Enter choice: ");
    }

    private static void manageStudents() {
        System.out.println("\\nStudent Management:");
        System.out.println("1. Add Student");
        System.out.println("2. List Students");
        System.out.println("3. Update Student");
        System.out.println("4. Deactivate Student");
        System.out.println("5. Print Student Profile & Transcript");
        System.out.println("6. Back to Main Menu");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> addStudent();
            case "2" -> listStudents();
            case "3" -> updateStudent();
            case "4" -> deactivateStudent();
            case "5" -> printStudentProfileTranscript();
            case "6" -> {}
            default -> System.out.println("Invalid option.");
        }
    }

    private static void addStudent() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter RegNo: ");
        String regNo = scanner.nextLine();
        System.out.print("Enter Full Name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
        String dobStr = scanner.nextLine();

        if (!Validators.isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }

        try {
            var dob = java.time.LocalDate.parse(dobStr);
            var student = new Student(id, regNo, fullName, email, dob);
            studentService.addStudent(student);
            System.out.println("Student added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    private static void listStudents() {
        List<Student> students = studentService.listStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(s -> System.out.println(s));
        }
    }

    private static void updateStudent() {
        System.out.print("Enter Student ID to update: ");
        String id = scanner.nextLine();
        var optStudent = studentService.findStudentById(id);
        if (optStudent.isEmpty()) {
            System.out.println("Student not found.");
            return;
        }
        Student student = optStudent.get();
        System.out.print("Enter new Full Name (" + student.getFullName() + "): ");
        String fullName = scanner.nextLine();
        System.out.print("Enter new Email (" + student.getEmail() + "): ");
        String email = scanner.nextLine();

        if (!email.isEmpty() && !Validators.isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }

        if (!fullName.isEmpty()) student.setFullName(fullName);
        if (!email.isEmpty()) student.setEmail(email);

        studentService.updateStudent(student);
        System.out.println("Student updated.");
    }

    private static void deactivateStudent() {
        System.out.print("Enter Student ID to deactivate: ");
        String id = scanner.nextLine();
        studentService.deactivateStudent(id);
        System.out.println("Student deactivated if existed.");
    }

    private static void printStudentProfileTranscript() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        var optStudent = studentService.findStudentById(id);
        if (optStudent.isEmpty()) {
            System.out.println("Student not found.");
            return;
        }
        Student student = optStudent.get();
        System.out.println(student.getProfile());
        System.out.println(transcriptService.generateTranscript(student));
    }

    private static void manageCourses() {
        System.out.println("\\nCourse Management:");
        System.out.println("1. Add Course");
        System.out.println("2. List Courses");
        System.out.println("3. Update Course");
        System.out.println("4. Deactivate Course");
        System.out.println("5. Search Courses");
        System.out.println("6. Filter Courses");
        System.out.println("7. Back to Main Menu");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> addCourse();
            case "2" -> listCourses();
            case "3" -> updateCourse();
            case "4" -> deactivateCourse();
            case "5" -> searchCourses();
            case "6" -> filterCourses();
            case "7" -> {}
            default -> System.out.println("Invalid option.");
        }
    }

    private static void addCourse() {
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Credits: ");
        int credits = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Semester (SPRING, SUMMER, FALL): ");
        Semester semester = Semester.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();

        var course = new edu.ccrm.domain.Course(code, title, credits, null, semester, department);
        courseService.addCourse(course);
        System.out.println("Course added.");
    }

    private static void listCourses() {
        List<edu.ccrm.domain.Course> courses = courseService.listCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            courses.forEach(c -> System.out.println(c));
        }
    }

    private static void updateCourse() {
        System.out.print("Enter Course Code to update: ");
        String code = scanner.nextLine();
        var optCourse = courseService.findCourseByCode(code);
        if (optCourse.isEmpty()) {
            System.out.println("Course not found.");
            return;
        }
        edu.ccrm.domain.Course course = optCourse.get();
        System.out.print("Enter new Title (" + course.getTitle() + "): ");
        String title = scanner.nextLine();
        System.out.print("Enter new Credits (" + course.getCredits() + "): ");
        String creditsStr = scanner.nextLine();
        System.out.print("Enter new Semester (" + course.getSemester() + "): ");
        String semesterStr = scanner.nextLine();
        System.out.print("Enter new Department (" + course.getDepartment() + "): ");
        String department = scanner.nextLine();

        if (!title.isEmpty()) course.setTitle(title);
        if (!creditsStr.isEmpty()) course.setCredits(Integer.parseInt(creditsStr));
        if (!semesterStr.isEmpty()) course.setSemester(Semester.valueOf(semesterStr.toUpperCase()));
        if (!department.isEmpty()) course.setDepartment(department);

        courseService.updateCourse(course);
        System.out.println("Course updated.");
    }

    private static void deactivateCourse() {
        System.out.print("Enter Course Code to deactivate: ");
        String code = scanner.nextLine();
        courseService.deactivateCourse(code);
        System.out.println("Course deactivated if existed.");
    }

    private static void searchCourses() {
        System.out.print("Enter search query: ");
        String query = scanner.nextLine();
        var results = courseService.searchCourses(query);
        if (results.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            results.forEach(c -> System.out.println(c));
        }
    }

    private static void filterCourses() {
        System.out.println("Filter by:");
        System.out.println("1. Instructor");
        System.out.println("2. Department");
        System.out.println("3. Semester");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> {
                System.out.print("Enter Instructor ID: ");
                String id  = scanner.nextLine();
                // For simplicity, no instructor service, so just print no results
                System.out.println("Instructor filtering not implemented.");
            }
            case "2" -> {
                System.out.print("Enter Department: ");
                String dept = scanner.nextLine();
                var results = courseService.filterByDepartment(dept);
                if (results.isEmpty()) {
                    System.out.println("No courses found.");
                } else {
                    results.forEach(c -> System.out.println(c));
                }
            }
            case "3" -> {
                System.out.print("Enter Semester (SPRING, SUMMER, FALL): ");
                String semStr = scanner.nextLine();
                try {
                    var sem = Semester.valueOf(semStr.toUpperCase());
                    var results = courseService.filterBySemester(sem);
                    if (results.isEmpty()) {
                        System.out.println("No courses found.");
                    } else {
                        results.forEach(c -> System.out.println(c));
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid semester.");
                }
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private static void manageEnrollment() {
        System.out.println("\\nEnrollment Management:");
        System.out.println("1. Enroll Student in Course");
        System.out.println("2. Unenroll Student from Course");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> enrollStudentInCourse();
            case "2" -> unenrollStudentFromCourse();
            case "3" -> {}
            default -> System.out.println("Invalid option.");
        }
    }

    private static void enrollStudentInCourse() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        var optStudent = studentService.findStudentById(studentId);
        var optCourse = courseService.findCourseByCode(courseCode);
        if (optStudent.isEmpty() || optCourse.isEmpty()) {
            System.out.println("Student or Course not found.");
            return;
        }
        try {
            enrollmentService.enroll(optStudent.get(), optCourse.get());
            System.out.println("Enrollment successful.");
        } catch (Exception e) {
            System.out.println("Enrollment failed: " + e.getMessage());
        }
    }

    private static void unenrollStudentFromCourse() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        var optStudent = studentService.findStudentById(studentId);
        var optCourse = courseService.findCourseByCode(courseCode);
        if (optStudent.isEmpty() || optCourse.isEmpty()) {
            System.out.println("Student or Course not found.");
            return;
        }
        enrollmentService.unenroll(optStudent.get(), optCourse.get());
        System.out.println("Unenrollment successful.");
    }

    private static void manageGrades() {
        System.out.println("\\nGrades Management:");
        System.out.println("1. Record Grade");
        System.out.println("2. Back to Main Menu");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> recordGrade();
            case "2" -> {}
            default -> System.out.println("Invalid option.");
        }
    }

    private static void recordGrade() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter Grade (S, A, B, C, D, E, F): ");
        String gradeStr = scanner.nextLine();
        var optStudent = studentService.findStudentById(studentId);
        var optCourse = courseService.findCourseByCode(courseCode);
        if (optStudent.isEmpty() || optCourse.isEmpty()) {
            System.out.println("Student or Course not found.");
            return;
        }
        try {
            var grade = Grade.valueOf(gradeStr.toUpperCase());
            enrollmentService.recordGrade(optStudent.get(), optCourse.get(), grade);
            System.out.println("Grade recorded.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid grade.");
        }
    }

    private static void importExportData() {
        System.out.println("\\nImport/Export Data:");
        System.out.println("1. Import Students");
        System.out.println("2. Import Courses");
        System.out.println("3. Export Students");
        System.out.println("4. Export Courses");
        System.out.println("5. Back to Main Menu");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine();
        try {
            switch (choice) {
                case "1" -> {
                    System.out.print("Enter path to students CSV: ");
                    var path = Paths.get(scanner.nextLine());
                    var students = importExportService.importStudents(path);
                    students.forEach(studentService::addStudent);
                    System.out.println("Students imported.");
                }
                case "2" -> {
                    System.out.print("Enter path to courses CSV: ");
                    var path = Paths.get(scanner.nextLine());
                    var courses = importExportService.importCourses(path);
                    courses.forEach(courseService::addCourse);
                    System.out.println("Courses imported.");
                }
                case "3" -> {
                    System.out.print("Enter path to export students CSV: ");
                    var path = Paths.get(scanner.nextLine());
                    importExportService.exportStudents(studentService.listStudents(), path);
                    System.out.println("Students exported.");
                }
                case "4" -> {
                    System.out.print("Enter path to export courses CSV: ");
                    var path = Paths.get(scanner.nextLine());
                    importExportService.exportCourses(courseService.listCourses(), path);
                    System.out.println("Courses exported.");
                }
                case "5" -> {}
                default -> System.out.println("Invalid option.");
            }
        } catch (Exception e) {
            System.out.println("Error during import/export: " + e.getMessage());
        }
    }

    private static void backupData() {
        try {
            backupService.performBackup();
            System.out.println("Backup completed.");
        } catch (Exception e) {
            System.out.println("Backup failed: " + e.getMessage());
        }
    }

    private static void printPlatformNote() {
        System.out.println("\\nJava Platform Note:");
        System.out.println("Java SE (Standard Edition) is the core Java platform for desktop and server applications.");
        System.out.println("Java ME (Micro Edition) is for embedded and mobile devices.");
        System.out.println("Java EE (Enterprise Edition) is for large-scale enterprise applications.");
    }
}
