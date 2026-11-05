import java.util.Scanner;

/**
 * Giao diện tương tác người dùng (Console Menu / CLI)
 * Đề tài: Student Management System (Đề 2 - CT_DSA_Problems)
 * Môn học: Data Structures and Algorithms (CSD201)
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentManager manager = new StudentManager();

    public static void main(String[] args) {
        // Tự động nạp dữ liệu mẫu ban đầu để sẵn sàng chạy thử nghiệm
        manager.loadSampleData();

        int choice = -1;
        do {
            printMenu();
            choice = readInt("Lua chon cua ban: ");
            switch (choice) {
                case 1:
                    handleAddStudent();
                    break;
                case 2:
                    manager.displayStudentList();
                    break;
                case 3:
                    handleSearchStudent();
                    break;
                case 4:
                    handleRequestRegistration();
                    break;
                case 5:
                    manager.processNextRegistration();
                    break;
                case 6:
                    manager.processAllRegistrations();
                    break;
                case 7:
                    manager.displayRegistrationQueue();
                    break;
                case 8:
                    manager.displayStudentsSortedById();
                    break;
                case 9:
                    manager.displayTreeBreadthFirst();
                    break;
                case 10:
                    manager.sortByGpaDescending();
                    break;
                case 11:
                    handleDeleteStudent();
                    break;
                case 12:
                    manager.loadSampleData();
                    break;
                case 0:
                    System.out.println("\n[!] Cam on ban da su dung chuong trinh. Chuc nhom thuyet trinh dat diem cao!");
                    break;
                default:
                    System.out.println("[!] Lua chon khong hop le. Vui long chon tu 0 den 12.");
            }
            if (choice != 0) {
                System.out.println("\nNhan Enter de tiep tuc...");
                scanner.nextLine();
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("\n==================================================================");
        System.out.println("     HE THONG QUAN LY SINH VIEN & DANG KY MON HOC (CSD201)      ");
        System.out.println("     (Computational Thinking: Linked List + Queue + BST)         ");
        System.out.println("==================================================================");
        System.out.println(" [1]  Them sinh vien moi (Them vao Linked List & Cay BST)");
        System.out.println(" [2]  Xem toan bo sinh vien (Duyet danh sach Linked List)");
        System.out.println(" [3]  Tim kiem sinh vien theo MSSV (Dung cay BST - O(log n))");
        System.out.println(" [4]  Them yeu cau dang ky mon vao Hang doi (Queue Enqueue)");
        System.out.println(" [5]  Xu ly 1 yeu cau dang ky mon tiep theo (Queue Dequeue FIFO)");
        System.out.println(" [6]  Xu ly toan bo yeu cau trong hang doi dang ky");
        System.out.println(" [7]  Xem danh sach sinh vien dang cho trong Hang doi (Queue Display)");
        System.out.println(" [8]  In danh sach sinh vien tang dan theo MSSV (BST In-Order)");
        System.out.println(" [9]  In cay sinh vien theo tung tang (BST Breadth-First Traversal)");
        System.out.println(" [10] Sap xep sinh vien theo GPA giam dan (Selection Sort tren List)");
        System.out.println(" [11] Xoa sinh vien theo MSSV (Dong bo xoa tren ca List va BST)");
        System.out.println(" [12] Nap lai du lieu mau mac dinh (Sample Demo Data)");
        System.out.println(" [0]  Thoat chuong trinh");
        System.out.println("==================================================================");
    }

    private static void handleAddStudent() {
        System.out.println("\n--- THEM SINH VIEN MOI ---");
        System.out.print("Nhap Ma so sinh vien (MSSV): ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("[!] MSSV khong duoc de trong.");
            return;
        }

        System.out.print("Nhap Ho va ten sinh vien: ");
        String name = scanner.nextLine().trim();

        System.out.print("Nhap Chuyen nganh (SE, IA, AI, DS,...): ");
        String major = scanner.nextLine().trim();

        double gpa = readDouble("Nhap Diem GPA (0.0 -> 10.0): ");
        while (gpa < 0.0 || gpa > 10.0) {
            System.out.println("[!] GPA phai nam trong khoang tu 0.0 den 10.0.");
            gpa = readDouble("Nhap lai Diem GPA: ");
        }

        Student s = new Student(id, name, major, gpa);
        manager.addStudent(s);
    }

    private static void handleSearchStudent() {
        System.out.println("\n--- TIM KIEM SINH VIEN THEO MSSV ---");
        System.out.print("Nhap MSSV can tra cuu: ");
        String id = scanner.nextLine().trim();
        manager.searchStudent(id);
    }

    private static void handleRequestRegistration() {
        System.out.println("\n--- DANG KY MON HOC (VAO HANG DOI QUEUE) ---");
        System.out.print("Nhap MSSV muon dang ky mon: ");
        String id = scanner.nextLine().trim();

        System.out.print("Nhap Ma mon hoc (vd: CSD201, PRF192, MAS291): ");
        String course = scanner.nextLine().trim();

        manager.requestCourseRegistration(id, course);
    }

    private static void handleDeleteStudent() {
        System.out.println("\n--- XOA SINH VIEN ---");
        System.out.print("Nhap MSSV can xoa: ");
        String id = scanner.nextLine().trim();
        manager.deleteStudent(id);
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("[!] Vui long nhap so nguyen hop le.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine().trim();
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("[!] Vui long nhap so thuc hop le.");
            }
        }
    }
}

