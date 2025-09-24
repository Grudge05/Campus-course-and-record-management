package edu.ccrm.domain;

public class Course {
    private String code;
    private String title;
    private int credits;
    private Instructor instructor;
    private Semester semester;
    private String department;
    private boolean active;

    public Course(String code, String title, int credits, Instructor instructor, Semester semester, String department) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.instructor = instructor;
        this.semester = semester;
        this.department = department;
        this.active = true;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Course{" +
                "Code='" + code + '\'' +
                ", Title='" + title + '\'' +
                ", Credits=" + credits +
                ", Instructor=" + (instructor != null ? instructor.getFullName() : "None") +
                ", Semester=" + semester +
                ", Department='" + department + '\'' +
                ", Active=" + active +
                '}';
    }
}
