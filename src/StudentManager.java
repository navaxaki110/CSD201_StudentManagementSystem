import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Lớp điều phối nghiệp vụ (Business Logic & Computational Thinking Controller):
 * Kết nối và đồng bộ 3 cấu trúc dữ liệu cốt lõi:
 * 1. MyList (Linked List) -> Quản lý danh sách sinh viên nội bộ
 * 2. MyQueue (Queue FIFO) -> Xử lý hàng đợi đăng ký môn học
 * 3. BSTree (Binary Search Tree) -> Tối ưu hóa tìm kiếm theo mã sinh viên (ID)
 */
public class StudentManager {
    private MyList studentList;          // Danh sách liên kết đơn
    private MyQueue registrationQueue;   // Hàng đợi đăng ký môn học (FIFO)
    private BSTree studentBST;           // Cây nhị phân tìm kiếm theo ID

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

    /**
     * Thêm sinh viên mới vào hệ thống:
     * - Kiểm tra trùng lặp ID qua BST (O(log n))
     * - Thêm vào Linked List (O(1))
     * - Chèn vào BST (O(log n))
     */
    public boolean addStudent(Student student) {
        if (student == null || student.getId() == null || student.getId().trim().isEmpty()) {
            System.out.println("[!] Loi: Ma sinh vien khong hop le.");
            return false;
        }

        // Kiểm tra xem ID đã tồn tại trong BST chưa
        if (studentBST.search(student.getId()) != null) {
            System.out.println("[!] Loi: Sinh vien co ma '" + student.getId() + "' da ton tai trong he thong.");
            return false;
        }

        // Thêm vào cả Linked List và BST
        studentList.addLast(student);
        studentBST.insert(student);
        System.out.println("[OK] Them sinh vien '" + student.getName() + " (" + student.getId() + ")' thanh cong!");
        return true;
    }

    /**
     * Hiển thị toàn bộ danh sách sinh viên (duyệt Linked List)
     */
    public void displayStudentList() {
        System.out.println("\n--- DANH SACH SINH VIEN (Luu tru tren Linked List) ---");
        studentList.traverse();
    }

    /**
     * Tìm kiếm sinh viên theo ID:
     * So sánh hiệu năng giữa Tìm kiếm tuần tự (Linked List - O(n)) và Tìm kiếm nhị phân (BST - O(log n))
     */
    public Student searchStudent(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("[!] Vui long nhap ma sinh vien can tim.");
            return null;
        }

        System.out.println("\n--- KET QUA TIM KIEM SINH VIEN: " + id.toUpperCase() + " ---");
        
        // 1. Tìm trên BST
        BSTree.SearchResult bstResult = studentBST.searchWithStats(id);

        // 2. Tìm trên Linked List để đếm số bước so sánh (dùng cho thuyết trình minh họa CT)
        int listSteps = 0;
        ListNode p = studentList.getHead();
        while (p != null) {
            listSteps++;
            if (p.info.getId().equalsIgnoreCase(id)) break;
            p = p.next;
        }

        if (bstResult.found) {
            System.out.println("[OK] Da tim thay sinh vien!");
            MyList.printTableHeader();
            System.out.println(bstResult.student);
            MyList.printTableFooter();
            System.out.println("-> Phan tich thuat toan (Computational Thinking Analysis):");
            System.out.println("   + So buoc so sanh tren cay BST:         " + bstResult.steps + " buoc  (Do phuc tap: O(log n))");
            System.out.println("   + So buoc neu tim tren Linked List:    " + listSteps + " buoc  (Do phuc tap: O(n))");
            System.out.println("   => BST giup tiet kiem: " + (listSteps - bstResult.steps) + " lan so sanh!");
            return bstResult.student;
        } else {
            System.out.println("[X] Khong tim thay sinh vien co ma: " + id);
            System.out.println("-> BST da kiem tra sau " + bstResult.steps + " buoc va xac nhan khong ton tai.");
            return null;
        }
    }

    /**
     * Gửi yêu cầu đăng ký môn học vào Hàng đợi (Enqueue)
     */
    public boolean requestCourseRegistration(String studentId, String courseCode) {
        if (studentId == null || courseCode == null || studentId.trim().isEmpty() || courseCode.trim().isEmpty()) {
            System.out.println("[!] Ma sinh vien va ma mon hoc khong duoc de trong.");
            return false;
        }

        // Kiểm tra xem sinh viên có tồn tại trong hệ thống hay không
        Student student = studentBST.search(studentId);
        if (student == null) {
            System.out.println("[!] Khong the dang ky: Sinh vien co ma '" + studentId + "' khong ton tai!");
            return false;
        }

        // Kiểm tra xem sinh viên đã học môn này chưa
        if (student.hasCourse(courseCode)) {
            System.out.println("[!] Sinh vien '" + student.getName() + "' da dang ky mon " + courseCode.toUpperCase() + " roi!");
            return false;
        }

        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"));
        RegistrationRequest req = new RegistrationRequest(student.getId(), courseCode.toUpperCase().trim(), currentTime);
        registrationQueue.enqueue(req);
        System.out.println("[OK] Da them vao Hang doi dang ky: " + req);
        System.out.println("     Vi tri hien tai trong hang doi: " + registrationQueue.size());
        return true;
    }

    /**
     * Xử lý 1 yêu cầu ở đầu hàng đợi (Dequeue - FIFO)
     */
    public boolean processNextRegistration() {
        if (registrationQueue.isEmpty()) {
            System.out.println("[!] Hang doi dang ky trong. Khong co yeu cau nao can xu ly.");
            return false;
        }

        RegistrationRequest req = registrationQueue.dequeue();
        Student student = studentBST.search(req.getStudentId());

        if (student != null) {
            student.addCourse(req.getCourseCode());
            System.out.println("[OK] Xu ly thanh cong yeu cau theo FIFO:");
            System.out.println("     + Sinh vien: " + student.getName() + " (" + student.getId() + ")");
            System.out.println("     + Mon da duyet: " + req.getCourseCode());
            System.out.println("     + Cac mon hien co: " + student.getCourses());
            return true;
        } else {
            System.out.println("[!] Sinh vien khong con ton tai trong he thong. Bo qua yeu cau nay.");
            return false;
        }
    }

    /**
     * Xử lý toàn bộ yêu cầu trong hàng đợi
     */
    public void processAllRegistrations() {
        if (registrationQueue.isEmpty()) {
            System.out.println("[!] Hang doi dang ky trong.");
            return;
        }
        System.out.println("\n--- BAT DAU XU LY TOAN BO HANG DOI DANG KY ---");
        int count = 0;
        while (!registrationQueue.isEmpty()) {
            processNextRegistration();
            count++;
        }
        System.out.println("--> Hoan tat xu ly " + count + " yeu cau trong hang doi!");
    }

    /**
     * Hiển thị hàng đợi đăng ký hiện tại
     */
    public void displayRegistrationQueue() {
        registrationQueue.display();
    }

    /**
     * Hiển thị sinh viên theo thứ tự tăng dần của ID (Duyệt cây BST In-Order)
     */
    public void displayStudentsSortedById() {
        System.out.println("\n--- DANH SACH SINH VIEN TANG DAN THEO ID (BST In-Order Traversal) ---");
        studentBST.inOrder();
    }

    /**
     * Hiển thị cây sinh viên theo từng tầng (BST Breadth-First Traversal)
     */
    public void displayTreeBreadthFirst() {
        System.out.println("\n--- CAY SINH VIEN DUYET THEO TUNG TANG (BST Breadth-First Traversal) ---");
        studentBST.breadthFirst();
        System.out.println("Tong so Node: " + studentBST.count() + " | Chieu cao cay (Height): " + studentBST.height());
    }

    /**
     * Sắp xếp danh sách sinh viên theo GPA giảm dần (Selection Sort trên Linked List)
     */
    public void sortByGpaDescending() {
        studentList.sortByGpaDescending();
        System.out.println("[OK] Da sap xep danh sach sinh vien theo GPA giam dan.");
        studentList.traverse();
    }

    /**
     * Xóa sinh viên theo mã ID (Đồng bộ xóa trên cả Linked List và BST)
     */
    public boolean deleteStudent(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("[!] Vui long nhap ma sinh vien can xoa.");
            return false;
        }

        boolean deletedFromList = studentList.delete(id);
        boolean deletedFromBST = studentBST.deleteByCopying(id);

        if (deletedFromList && deletedFromBST) {
            System.out.println("[OK] Da xoa sinh vien co ma '" + id + "' khoi ca Linked List va Cay BST.");
            return true;
        } else if (deletedFromList || deletedFromBST) {
            System.out.println("[!] Canh bao: Du lieu khong dong nhat giua List va BST khi xoa.");
            return true;
        } else {
            System.out.println("[X] Khong tim thay sinh vien co ma '" + id + "' de xoa.");
            return false;
        }
    }

    /**
     * Nạp dữ liệu mẫu ban đầu để nhóm có thể demo chạy ngay lập tức khi thuyết trình
     */
    public void loadSampleData() {
        studentList.clear();
        studentBST.clear();
        registrationQueue.clear();

        // Thêm 7 sinh viên mẫu để cây BST cân bằng đẹp mắt
        addStudent(new Student("SE1804", "Tran Quoc Tuan", "Software Eng", 8.9, "CSD201, PRF192"));
        addStudent(new Student("SE1802", "Nguyen Thi Binh", "Artificial Intel", 7.8, "CSD201"));
        addStudent(new Student("SE1806", "Le Van Cuong", "Information Sec", 9.1, "CSD201, MAS291"));
        addStudent(new Student("SE1801", "Pham Huu An", "Software Eng", 6.5, "PRF192"));
        addStudent(new Student("SE1803", "Vu Hoang Dung", "Data Science", 8.4, "CSD201, PRO192"));
        addStudent(new Student("SE1805", "Hoang Thi Em", "Information Sec", 7.2, "PRF192"));
        addStudent(new Student("SE1807", "Do Minh Giang", "Software Eng", 8.0, "PRO192"));

        // Thêm 3 yêu cầu đăng ký vào hàng đợi Queue
        requestCourseRegistration("SE1801", "CSD201");
        requestCourseRegistration("SE1805", "CSD201");
        requestCourseRegistration("SE1807", "MAS291");

        System.out.println("\n[OK] Da nap thanh cong 7 sinh vien mau va 3 yeu cau vao hang doi!");
    }
}

