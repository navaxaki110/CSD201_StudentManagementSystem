/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

public class Student {
    private String id;
    private String name;
    private String major;
    private double gpa;
    private String courses;

    public Student() {
        this.courses = "Chua dang ky";
    }

    public Student(String id, String name, String major, double gpa) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.gpa = gpa;
        this.courses = "Chua dang ky";
    }

    public Student(String id, String name, String major, double gpa, String courses) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.gpa = gpa;
        this.courses = (courses == null || courses.trim().isEmpty()) ? "Chua dang ky" : courses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public void addCourse(String courseCode) {
        if (courses == null || courses.trim().isEmpty() || courses.equalsIgnoreCase("Chua dang ky")) {
            this.courses = courseCode;
        } else {
            String[] arr = courses.split(",");
            for (String c : arr) {
                if (c.trim().equalsIgnoreCase(courseCode.trim())) {
                    return;
                }
            }
            this.courses += ", " + courseCode;
        }
    }

    public boolean hasCourse(String courseCode) {
        if (courses == null || courses.equalsIgnoreCase("Chua dang ky")) {
            return false;
        }
        String[] arr = courses.split(",");
        for (String c : arr) {
            if (c.trim().equalsIgnoreCase(courseCode.trim())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %.2f | %s", id, name, major, gpa, courses);
    }
}
