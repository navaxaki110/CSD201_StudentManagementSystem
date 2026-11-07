/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.StudentDAO;
import ds.Queue;
import ds.StudentBST;
import ds.StudentList;
import dto.RegistrationRequest;
import dto.Student;
import fileio.RequestFile;
import fileio.StudentFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletContext;

public class StudentService {
    private StudentList studentList;
    private Queue queueRequests;
    private StudentBST studentBST;
    private StudentDAO dao;

    public StudentService() {
        this(null);
    }

    public StudentService(ServletContext context) {
        studentList = new StudentList();
        queueRequests = new Queue();
        studentBST = new StudentBST();
        dao = new StudentDAO();

        // 1. Load students
        List<Student> students = StudentFile.loadFromFile(context);
        if (students.isEmpty()) {
            students = dao.downloadStudents();
        }
        for (Student s : students) {
            studentList.addLast(s);
            studentBST.insert(s);
        }

        // 2. Load requests into Queue
        List<RegistrationRequest> reqs = RequestFile.loadFromFile(context);
        if (reqs.isEmpty()) {
            reqs = dao.downloadRequests();
        }
        for (RegistrationRequest r : reqs) {
            queueRequests.enqueue(r);
        }
    }

    public StudentList getStudentList() {
        return studentList;
    }

    public Queue getQueueRequests() {
        return queueRequests;
    }

    public StudentBST getStudentBST() {
        return studentBST;
    }

    public List<Student> getAllStudents() {
        return studentList.toList();
    }

    public List<Student> getStudentsSortedByGpa() {
        StudentList copyList = new StudentList();
        for (Student s : studentList.toList()) {
            copyList.addLast(new Student(s.getId(), s.getName(), s.getMajor(), s.getGpa(), s.getCourses()));
        }
        copyList.sortByGpaDescending();
        return copyList.toList();
    }

    public Student searchStudent(String id) {
        // Fast search on BSTree: O(log n)
        return studentBST.search(id);
    }

    public boolean addStudent(Student s) {
        if (s == null || studentBST.search(s.getId()) != null) {
            return false;
        }
        studentList.addLast(s);
        studentBST.insert(s);
        try {
            dao.uploadStudents(studentList.toList());
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
        return true;
    }

    public boolean requestCourse(String studentId, String courseCode) {
        Student s = searchStudent(studentId);
        if (s == null) {
            return false;
        }
        if (s.hasCourse(courseCode)) {
            return false; // Already enrolled
        }
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        RegistrationRequest req = new RegistrationRequest(studentId, courseCode, timestamp);
        queueRequests.enqueue(req);
        try {
            dao.uploadRequests(queueRequests.getList());
        } catch (IOException e) {
            System.out.println("Error saving requests: " + e.getMessage());
        }
        return true;
    }

    public RegistrationRequest approveNextRequest() {
        if (queueRequests.isEmpty()) {
            return null;
        }
        RegistrationRequest req = queueRequests.dequeue();
        if (req != null) {
            Student sList = studentList.search(req.getStudentId());
            if (sList != null) {
                sList.addCourse(req.getCourseCode());
            }
            Student sBST = studentBST.search(req.getStudentId());
            if (sBST != null) {
                sBST.addCourse(req.getCourseCode());
            }
            try {
                dao.uploadStudents(studentList.toList());
                dao.uploadRequests(queueRequests.getList());
            } catch (IOException e) {
                System.out.println("Error persisting approval: " + e.getMessage());
            }
        }
        return req;
    }

    public List<RegistrationRequest> getPendingRequests() {
        return queueRequests.getList();
    }
}
