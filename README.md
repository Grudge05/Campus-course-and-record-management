# Campus Course & Records Manager (CCRM)

## Project Overview

The Campus Course & Records Manager (CCRM) is a console-based Java application designed to manage students, courses, grades, and file operations for an educational institute. It allows users to add, list, update, and deactivate students and courses, manage enrollments and grades, generate transcripts, and perform file import/export and backup operations.

### How to Run

1. Ensure you have JDK 17 or later installed (verify with `java -version`).
2. Compile the project: `javac -d bin edu/ccrm/**/*.java`
3. Run the application: `java -cp bin edu.ccrm.cli.MainCLI`
4. Follow the menu prompts to interact with the system.

## Evolution of Java

- 1995: Java 1.0 released by Sun Microsystems, introducing "Write Once, Run Anywhere."
- 1996: Java 1.1 added inner classes, JDBC, and RMI.
- 1998: Java 2 introduced Swing, Collections Framework, and improved event handling.
- 2004: Java 5 brought generics, annotations, autoboxing, and enums.
- 2014: Java 8 introduced lambdas, streams, and the new Date/Time API.
- 2017: Java 9 added modules, JShell, and improved garbage collection.
- 2018: Java 11 (LTS) included HTTP Client and removed Java EE modules.
- 2021: Java 17 (LTS) enhanced pattern matching and sealed classes.

## Java ME vs SE vs EE

| Edition | Description | Use Case |
|---------|-------------|----------|
| Java ME (Micro Edition) | Lightweight version for embedded and mobile devices. | IoT devices, mobile apps with limited resources. |
| Java SE (Standard Edition) | Core Java platform for desktop and server applications. | General-purpose applications, command-line tools. |
| Java EE (Enterprise Edition) | Extended platform for large-scale enterprise applications (now Jakarta EE). | Web servers, enterprise software with scalability needs. |

## JDK, JRE, JVM Explanation

- **JVM (Java Virtual Machine)**: The runtime environment that executes Java bytecode, providing platform independence.
- **JRE (Java Runtime Environment)**: Includes the JVM, core libraries, and other components needed to run Java applications.
- **JDK (Java Development Kit)**: Includes the JRE plus development tools like the compiler (javac), debugger, and Javadoc.

The JDK compiles source code into bytecode, which the JVM runs using the JRE's libraries.

## Windows Installation Steps

1. Download the latest JDK from the official Oracle website (https://www.oracle.com/java/technologies/downloads/).
2. Run the installer and follow the setup wizard.
3. Set the JAVA_HOME environment variable to the JDK installation path (e.g., C:\Program Files\Java\jdk-17).
4. Add %JAVA_HOME%\bin to the PATH variable.
5. Verify installation: Open Command Prompt and run `java -version` and `javac -version`.

*Screenshots of installation steps are in the screenshots folder.*

## Eclipse Setup Steps

1. Download Eclipse IDE for Java Developers from https://www.eclipse.org/downloads/.
2. Extract and run Eclipse.
3. Create a new Java Project: File > New > Java Project.
4. Name the project (e.g., CCRM) and set the location.
5. Create packages: Right-click src > New > Package (e.g., edu.ccrm.cli).
6. Add source files to the appropriate packages.
7. To run: Right-click MainCLI.java > Run As > Java Application.

*Screenshots of Eclipse setup are in the screenshots folder.*

## Mapping Table: Syllabus Topic → File/Class/Method

| Topic | File/Class/Method | Description |
|-------|-------------------|-------------|
| Java Introduction (Hello World, JVM/JRE/JDK, Variables, Data Types, Operators, Input/Output, Flow Control) | edu/ccrm/cli/MainCLI.java (main method, Scanner for input, System.out for output, variables, if/else, switch, loops) | Demonstrates basic Java syntax, input/output, and control flow in the CLI menu. |
| Java OOP (Basics: Class/Objects, Methods, Constructor, Strings, Access Modifiers, this/final, Recursion, instanceof, Anonymous Class, enum) | edu/ccrm/domain/Student.java (class, constructor, methods, private fields, getters/setters, final fields) | Shows class structure, constructors, methods, encapsulation, and enums like Semester. |
| Java OOP (Inheritance & Polymorphism: Inheritance, Overriding, super, Abstract Class/Method, Interfaces, Polymorphism, Encapsulation) | edu/ccrm/domain/Person.java (abstract class) → Student.java (inheritance, overriding), edu/ccrm/service/Persistable.java (interface) | Demonstrates inheritance, abstract classes, interfaces, and polymorphism in service methods. |
| Java OOP (Other types of classes: Nested/Inner Class, Static Class, Anonymous Class, Singleton, enum, Reflection) | edu/ccrm/config/AppConfig.java (Singleton pattern), edu/ccrm/domain/Semester.java (enum with constructors) | Singleton for configuration, enums for Semester and Grade. |
| Java Exception Handling (Exceptions, try/catch, throw/throws, Multiple Exceptions, Annotations) | edu/ccrm/service/StudentService.java (try/catch, custom exceptions), edu/ccrm/domain/Student.java (annotations if any) | Custom exceptions for business rules, exception handling in methods. |
| Multithreading (Introduction, Thread Creation, Life Cycle, Synchronization, User-defined packages) | Not directly implemented (CLI is single-threaded), but packages are used (edu.ccrm.*) | Project uses user-defined packages for organization. |
| Java List & I/O Streams (Strings, Arrays, Collections, I/O Streams, Reader/Writer) | edu/ccrm/service/StudentService.java (ArrayList for students), edu/ccrm/io/ImportExportService.java (File I/O with NIO.2) | Collections for data storage, I/O for file operations. |
| Database Applications with JDBC | Not implemented (uses file-based storage instead) | Project uses CSV files for persistence. |
| Java Persistence API (JPA architecture, ORM, JPQL, Entities, Mappings) | Not implemented (uses custom file I/O) | Project uses custom services for data management. |

## Notes on Enabling Assertions

To enable assertions, run the application with the `-ea` flag: `java -ea -cp bin edu.ccrm.cli.MainCLI`. Assertions check for invariants, e.g., non-null IDs.

## Sample Commands

- Compile: `javac -d bin edu/ccrm/**/*.java`
- Run: `java -cp bin edu.ccrm.cli.MainCLI`
- Run with assertions: `java -ea -cp bin edu.ccrm.cli.MainCLI`
- Export students: Select 5 > 3 in the menu and provide a path.
- Backup: Select 6 in the menu.

## Screenshots

Screenshots of JDK installation, Eclipse setup, program running, and folder structure are in the `screenshots` folder.

## Demo Video

[Optional: Link to a 2-5 minute demo video on YouTube or Google Drive showing the application walkthrough.]

## USAGE

### Sample Commands

- Add a student: Select 1 > 1, enter details.
- List students: Select 1 > 2.
- Print transcript: Select 1 > 5, enter student ID.
- Add a course: Select 2 > 1, enter details.
- Enroll student: Select 3 > 1, enter student ID and course code.
- Record grade: Select 4 > 1, enter student ID, course code, and grade.
- Import data: Select 5 > 1 or 2, provide CSV path.
- Export data: Select 5 > 3 or 4, provide path.
- Backup: Select 6.

### Data Files

The `test-data` folder contains sample CSV files for import:
- students.csv: Sample student data.
- courses.csv: Sample course data.

Use these for testing import functionality.
