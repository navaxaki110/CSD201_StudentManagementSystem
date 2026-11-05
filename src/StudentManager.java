import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
            System.out.println("Da duyet dang ky mon " + req.getCourseCode() + " cho sinh vien " + student.getName() + " (" + student.getId() + ").");
            return true;
        } else {
            System.out.println("Sinh vien khong con ton tai trong he thong.");
            return false;
        }
    }

    public void processAllRegistrations() {
        if (registrationQueue.isEmpty()) {
            System.out.println("Hang doi dang ky trong.");
            return;
        }
        System.out.println("\n--- XU LY HANG DOI DANG KY ---");
        int count = 0;
        while (!registrationQueue.isEmpty()) {
            processNextRegistration();
            count++;
        }
        System.out.println("Hoan tat xu ly " + count + " yeu cau.");
    }

    public void displayRegistrationQueue() {
        registrationQueue.display();
    }

    public void displayStudentsSortedById() {
        System.out.println("\n--- DANH SACH SINH VIEN THEO MSSV TANG DAN ---");
        studentBST.inOrder();
    }

    public void displayTreeBreadthFirst() {
        System.out.println("\n--- DUYET CAY THEO TUNG TANG (BREADTH-FIRST) ---");
        studentBST.breadthFirst();
        System.out.println("Tong so sinh vien: " + studentBST.count() + " | Chieu cao cay: " + studentBST.height());
    }

    public void sortByGpaDescending() {
        studentList.sortByGpaDescending();
        System.out.println("Da sap xep danh sach theo GPA giam dan.");
        studentList.traverse();
    }

    public boolean deleteStudent(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("Vui long nhap ma sinh vien can xoa.");
            return false;
        }

        boolean deletedFromList = studentList.delete(id);
        boolean deletedFromBST = studentBST.deleteByCopying(id);

        if (deletedFromList && deletedFromBST) {
            System.out.println("Da xoa sinh vien co ma '" + id + "'.");
            return true;
        } else {
            System.out.println("Khong tim thay sinh vien co ma '" + id + "' de xoa.");
            return false;
        }
    }

    public void loadSampleData() {
        studentList.clear();
        studentBST.clear();
        registrationQueue.clear();

        addStudent(new Student("SE1804", "Tran Quoc Tuan", "Software Eng", 8.9, "CSD201, PRF192"));
        addStudent(new Student("SE1802", "Nguyen Thi Binh", "Artificial Intel", 7.8, "CSD201"));
        addStudent(new Student("SE1806", "Le Van Cuong", "Information Sec", 9.1, "CSD201, MAS291"));
        addStudent(new Student("SE1801", "Pham Huu An", "Software Eng", 6.5, "PRF192"));
        addStudent(new Student("SE1803", "Vu Hoang Dung", "Data Science", 8.4, "CSD201, PRO192"));
        addStudent(new Student("SE1805", "Hoang Thi Em", "Information Sec", 7.2, "PRF192"));
        addStudent(new Student("SE1807", "Do Minh Giang", "Software Eng", 8.0, "PRO192"));

        requestCourseRegistration("SE1801", "CSD201");
        requestCourseRegistration("SE1805", "CSD201");
        requestCourseRegistration("SE1807", "MAS291");
    }
}
