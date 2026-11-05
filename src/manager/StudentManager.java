package manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import list.MyList;
import model.RegistrationRequest;
import model.Student;
import queue.MyQueue;
import tree.BSTree;

public class StudentManager {
    private MyList studentList;
    private MyQueue registrationQueue;
    private BSTree studentBST;

    public StudentManager() {
        this.studentList = new MyList();
        this.registrationQueue = new MyQueue();
        this.studentBST = new BSTree();
    }

    public MyList getStudentList() {
        return studentList;
    }

    public MyQueue getRegistrationQueue() {
        return registrationQueue;
    }

    public BSTree getStudentBST() {
        return studentBST;
    }

    public boolean addStudent(Student student) {
        if (student == null || student.getId() == null || student.getId().trim().isEmpty()) {
            System.out.println("Loi: Ma sinh vien khong hop le.");
            return false;
        }

        if (studentBST.search(student.getId()) != null) {
            System.out.println("Loi: Sinh vien co ma '" + student.getId() + "' da ton tai.");
            return false;
        }

        studentList.addLast(student);
        studentBST.insert(student);
        System.out.println("Them sinh vien '" + student.getName() + " (" + student.getId() + ")' thanh cong.");
        return true;
    }

    public void displayStudentList() {
        System.out.println("\n--- DANH SACH SINH VIEN ---");
        studentList.traverse();
    }

    public Student searchStudent(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("Vui long nhap ma sinh vien can tim.");
            return null;
        }

        Student student = studentBST.search(id);
        if (student != null) {
            System.out.println("\n--- KET QUA TIM KIEM: " + id.toUpperCase() + " ---");
            MyList.printTableHeader();
            System.out.println(student);
            MyList.printTableFooter();
            return student;
        } else {
            System.out.println("Khong tim thay sinh vien co ma: " + id);
            return null;
        }
    }

    public boolean requestCourseRegistration(String studentId, String courseCode) {
        if (studentId == null || courseCode == null || studentId.trim().isEmpty() || courseCode.trim().isEmpty()) {
            System.out.println("Ma sinh vien va ma mon hoc khong duoc de trong.");
            return false;
        }

        Student student = studentBST.search(studentId);
        if (student == null) {
            System.out.println("Loi: Sinh vien co ma '" + studentId + "' khong ton tai.");
            return false;
        }

        if (student.hasCourse(courseCode)) {
            System.out.println("Sinh vien '" + student.getName() + "' da dang ky mon " + courseCode.toUpperCase() + ".");
            return false;
        }

        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"));
        RegistrationRequest req = new RegistrationRequest(student.getId(), courseCode.toUpperCase().trim(), currentTime);
        registrationQueue.enqueue(req);
        System.out.println("Da them vao hang doi dang ky: " + req);
        return true;
    }

    public boolean processNextRegistration() {
        if (registrationQueue.isEmpty()) {
            System.out.println("Hang doi dang ky trong.");
            return false;
        }

        RegistrationRequest req = registrationQueue.dequeue();
        Student student = studentBST.search(req.getStudentId());

        if (student != null) {
            student.addCourse(req.getCourseCode());
            studentList.updateCourses(req.getStudentId(), req.getCourseCode());
            System.out.println("Da duyet dang ky mon " + req.getCourseCode() + " cho sinh vien " + student.getName() + " (" + student.getId() + ").");
            return true;
        } else {
            System.out.println("Sinh vien khong con ton tai trong he thong.");
            return false;
        }
    }

    public void displayRegistrationQueue() {
        registrationQueue.display();
    }

    public void loadDataFromFiles() {
        studentList.clear();
        studentBST.clear();
        registrationQueue.clear();

        File sFile = findFile("students.txt");
        if (sFile != null && sFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(sFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) continue;
                    String[] parts = line.split("\\|");
                    if (parts.length >= 4) {
                        String id = parts[0].trim();
                        String name = parts[1].trim();
                        String major = parts[2].trim();
                        double gpa = Double.parseDouble(parts[3].trim());
                        String courses = parts.length > 4 ? parts[4].trim() : "Chua dang ky";
                        Student s = new Student(id, name, major, gpa, courses);
                        studentList.addLast(s);
                        studentBST.insert(s);
                    }
                }
            } catch (Exception e) {
                System.out.println("Loi khi doc file students.txt: " + e.getMessage());
            }
        } else {
            loadDefaultStudents();
        }

        File rFile = findFile("requests.txt");
        if (rFile != null && rFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(rFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) continue;
                    String[] parts = line.split("\\|");
                    if (parts.length >= 2) {
                        String studentId = parts[0].trim();
                        String courseCode = parts[1].trim();
                        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"));
                        registrationQueue.enqueue(new RegistrationRequest(studentId, courseCode, time));
                    }
                }
            } catch (Exception e) {
                System.out.println("Loi khi doc file requests.txt: " + e.getMessage());
            }
        }
    }

    private File findFile(String fileName) {
        File f = new File(fileName);
        if (f.exists()) return f;
        f = new File("StudentManagementSystem" + File.separator + fileName);
        if (f.exists()) return f;
        f = new File(".." + File.separator + fileName);
        if (f.exists()) return f;
        return null;
    }

    private void loadDefaultStudents() {
        addStudent(new Student("SE1804", "Tran Quoc Tuan", "Software Eng", 8.9, "CSD201, PRF192"));
        addStudent(new Student("SE1802", "Nguyen Thi Binh", "Artificial Intel", 7.8, "CSD201"));
        addStudent(new Student("SE1806", "Le Van Cuong", "Information Sec", 9.1, "CSD201, MAS291"));
        addStudent(new Student("SE1801", "Pham Huu An", "Software Eng", 6.5, "PRF192"));
        addStudent(new Student("SE1803", "Vu Hoang Dung", "Data Science", 8.4, "CSD201, PRO192"));
        addStudent(new Student("SE1805", "Hoang Thi Em", "Information Sec", 7.2, "PRF192"));
        addStudent(new Student("SE1807", "Do Minh Giang", "Software Eng", 8.0, "PRO192"));
        addStudent(new Student("SE205280", "Le Trong Hieu", "SE", 9.0, "Chua dang ky"));
    }
}
