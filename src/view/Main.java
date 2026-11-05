package view;

import java.util.Scanner;
import manager.StudentManager;
import model.Student;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentManager manager = new StudentManager();

    public static void main(String[] args) {
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
                    handleUpdateStudent();
                    break;
                case 5:
                    handleRequestRegistration();
                    break;
                case 6:
                    manager.processNextRegistration();
                    break;
                case 7:
                    manager.processAllRegistrations();
                    break;
                case 8:
                    manager.displayRegistrationQueue();
                    break;
                case 9:
                    manager.displayStudentsSortedById();
                    break;
                case 10:
                    manager.displayTreeBreadthFirst();
                    break;
                case 11:
                    manager.sortByGpaDescending();
                    break;
                case 12:
                    handleDeleteStudent();
                    break;
                case 13:
                    manager.loadSampleData();
                    System.out.println("Da nap lai du lieu mau.");
                    break;
                case 0:
                    System.out.println("Tam biet!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
            if (choice != 0) {
                System.out.println("\nNhan Enter de tiep tuc...");
                scanner.nextLine();
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("\n=======================================================");
        System.out.println("       HE THONG QUAN LY SINH VIEN & DANG KY MON        ");
        System.out.println("=======================================================");
        System.out.println(" [1]  Them sinh vien moi");
        System.out.println(" [2]  Xem danh sach sinh vien");
        System.out.println(" [3]  Tim kiem sinh vien theo MSSV (BST)");
        System.out.println(" [4]  Cap nhat thong tin sinh vien (Linked List Update)");
        System.out.println(" [5]  Gui yeu cau dang ky mon hoc (Queue Enqueue)");
        System.out.println(" [6]  Duyet 1 yeu cau dang ky (Queue Dequeue FIFO)");
        System.out.println(" [7]  Duyet tat ca yeu cau dang ky");
        System.out.println(" [8]  Xem hang doi dang ky mon");
        System.out.println(" [9]  In danh sach theo MSSV tang dan (BST In-Order)");
        System.out.println(" [10] In cay sinh vien theo tang (BST Breadth-First)");
        System.out.println(" [11] Sap xep danh sach theo GPA giam dan");
        System.out.println(" [12] Xoa sinh vien theo MSSV");
        System.out.println(" [13] Nap lai du lieu mau");
        System.out.println(" [0]  Thoat");
        System.out.println("=======================================================");
    }

    private static void handleAddStudent() {
        System.out.println("\n--- THEM SINH VIEN MOI ---");
        System.out.print("Nhap MSSV: ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("MSSV khong duoc de trong.");
            return;
        }

        System.out.print("Nhap Ho va ten: ");
        String name = scanner.nextLine().trim();

        System.out.print("Nhap Chuyen nganh: ");
        String major = scanner.nextLine().trim();

        double gpa = readDouble("Nhap GPA (0.0 - 10.0): ");
        while (gpa < 0.0 || gpa > 10.0) {
            System.out.println("GPA khong hop le.");
            gpa = readDouble("Nhap lai GPA: ");
        }

        Student s = new Student(id, name, major, gpa);
        manager.addStudent(s);
    }

    private static void handleSearchStudent() {
        System.out.println("\n--- TIM KIEM SINH VIEN ---");
        System.out.print("Nhap MSSV can tim: ");
        String id = scanner.nextLine().trim();
        manager.searchStudent(id);
    }

    private static void handleUpdateStudent() {
        System.out.println("\n--- CAP NHAT THONG TIN SINH VIEN ---");
        System.out.print("Nhap MSSV can cap nhat: ");
        String id = scanner.nextLine().trim();

        System.out.print("Nhap Ho ten moi: ");
        String name = scanner.nextLine().trim();

        System.out.print("Nhap Chuyen nganh moi: ");
        String major = scanner.nextLine().trim();

        double gpa = readDouble("Nhap GPA moi (0.0 - 10.0): ");
        while (gpa < 0.0 || gpa > 10.0) {
            System.out.println("GPA khong hop le.");
            gpa = readDouble("Nhap lai GPA: ");
        }

        manager.updateStudent(id, name, major, gpa);
    }

    private static void handleRequestRegistration() {
        System.out.println("\n--- DANG KY MON HOC ---");
        System.out.print("Nhap MSSV: ");
        String id = scanner.nextLine().trim();

        System.out.print("Nhap Ma mon hoc: ");
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
                System.out.println("Vui long nhap so hop le.");
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
                System.out.println("Vui long nhap so thuc hop le.");
            }
        }
    }
}
