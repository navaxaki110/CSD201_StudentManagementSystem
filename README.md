# Student Management System

Dự án môn Data Structures and Algorithms (CSD201) - Topic 2.

## 1. Giới thiệu bài toán
Hệ thống quản lý sinh viên và xử lý hàng đợi đăng ký môn học:
- **Linked List (`MyList`):** Quản lý danh sách sinh viên.
- **Queue (`MyQueue`):** Hàng đợi xử lý yêu cầu đăng ký môn học (FIFO).
- **Binary Search Tree (`BSTree`):** Tối ưu hóa tìm kiếm sinh viên theo MSSV và duyệt danh sách theo thứ tự khóa.

## 2. Cấu trúc thư mục NetBeans
Dự án được cấu hình chuẩn NetBeans Ant Project:
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
    ├── Student.java
    ├── RegistrationRequest.java
    ├── ListNode.java
    ├── MyList.java
    ├── QueueNode.java
    ├── MyQueue.java
    ├── TreeNode.java
    ├── TreeQueue.java
    ├── BSTree.java
    ├── StudentManager.java
    └── Main.java
```

## 3. Cách mở và chạy dự án
- **Bằng NetBeans IDE:**
  1. Mở NetBeans $\rightarrow$ chọn `File` > `Open Project`.
  2. Chọn thư mục `StudentManagementSystem` (sẽ có biểu tượng ly cà phê).
  3. Nhấn nút **Run** (F6) để chạy.
- **Bằng dòng lệnh hoặc run.bat:**
  - Nhấp đúp vào file `run.bat`.
