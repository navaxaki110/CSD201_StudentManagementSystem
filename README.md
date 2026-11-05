# Student Management System

Dự án môn Data Structures and Algorithms (CSD201) - Topic 2.

## 1. Giới thiệu bài toán
Hệ thống quản lý sinh viên và xử lý hàng đợi đăng ký môn học:
- **Linked List (`MyList`):** Quản lý danh sách sinh viên nội trú (thêm, cập nhật, xóa, sắp xếp, duyệt).
- **Queue (`MyQueue`):** Hàng đợi xử lý yêu cầu đăng ký môn học (FIFO).
- **Binary Search Tree (`BSTree`):** Tối ưu hóa tìm kiếm sinh viên theo MSSV và duyệt danh sách theo thứ tự khóa.

## 2. Cấu trúc thư mục NetBeans
Dự án được phân chia theo từng package chuẩn NetBeans Ant Project:
```text
StudentManagementSystem/
├── nbproject/                  # Cấu hình project NetBeans
│   ├── project.xml
│   ├── project.properties
│   └── build-impl.xml
├── build.xml                   # Ant build script
├── manifest.mf
├── run.bat                     # Chạy nhanh trên Windows
├── push_to_git.bat             # Đẩy code lên GitHub
└── src/
    ├── model/                  # Package chứa các thực thể dữ liệu
    │   ├── Student.java
    │   └── RegistrationRequest.java
    ├── list/                   # Package cài đặt Singly Linked List
    │   ├── ListNode.java
    │   └── MyList.java
    ├── queue/                  # Package cài đặt Queue FIFO
    │   ├── QueueNode.java
    │   └── MyQueue.java
    ├── tree/                   # Package cài đặt Binary Search Tree (BST)
    │   ├── TreeNode.java
    │   ├── TreeQueueNode.java
    │   ├── TreeQueue.java
    │   └── BSTree.java
    ├── manager/                # Package điều phối nghiệp vụ
    │   └── StudentManager.java
    └── view/                   # Package giao diện Console Menu
        └── Main.java
```

## 3. Cách mở và chạy dự án
- **Bằng NetBeans IDE:**
  1. Mở NetBeans $\rightarrow$ chọn `File` > `Open Project`.
  2. Chọn thư mục `StudentManagementSystem` (biểu tượng ly cà phê).
  3. Nhấn **F6 (Run)** để chạy.
- **Bằng dòng lệnh hoặc run.bat:**
  - Nhấp đúp vào file `run.bat`.
