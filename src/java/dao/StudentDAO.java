/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.RegistrationRequest;
import dto.Student;
import fileio.FileUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String STUDENTS_FILE = "web/WEB-INF/students.txt";
    private static final String REQUESTS_FILE = "web/WEB-INF/requests.txt";

    public List<Student> downloadStudents() {
        List<Student> list = new ArrayList<>();
        List<String> lines = FileUtils.readAllLines(STUDENTS_FILE);
        if (lines.isEmpty()) {
            lines = FileUtils.readAllLines("students.txt");
        }
        for (String line : lines) {
            String[] p = line.split("\\|");
            if (p.length >= 4) {
                String id = p[0].trim();
                String name = p[1].trim();
                String major = p[2].trim();
                double gpa = Double.parseDouble(p[3].trim());
                String courses = (p.length >= 5) ? p[4].trim() : "Chua dang ky";
                list.add(new Student(id, name, major, gpa, courses));
            }
        }
        return list;
    }

    public void uploadStudents(List<Student> students) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Student s : students) {
            lines.add(String.format("%s | %s | %s | %.2f | %s", 
                    s.getId(), s.getName(), s.getMajor(), s.getGpa(), s.getCourses()));
        }
        FileUtils.writeAllLines(STUDENTS_FILE, lines);
        FileUtils.writeAllLines("students.txt", lines);
    }

    public List<RegistrationRequest> downloadRequests() {
        List<RegistrationRequest> list = new ArrayList<>();
        List<String> lines = FileUtils.readAllLines(REQUESTS_FILE);
        if (lines.isEmpty()) {
            lines = FileUtils.readAllLines("requests.txt");
        }
        for (String line : lines) {
            String[] p = line.split("\\|");
            if (p.length >= 2) {
                String id = p[0].trim();
                String course = p[1].trim();
                String time = (p.length >= 3) ? p[2].trim() : "";
                list.add(new RegistrationRequest(id, course, time));
            }
        }
        return list;
    }

    public void uploadRequests(List<RegistrationRequest> requests) throws IOException {
        List<String> lines = new ArrayList<>();
        for (RegistrationRequest r : requests) {
            lines.add(String.format("%s | %s | %s", r.getStudentId(), r.getCourseCode(), r.getRequestTime()));
        }
        FileUtils.writeAllLines(REQUESTS_FILE, lines);
        FileUtils.writeAllLines("requests.txt", lines);
    }
}
