# PROJECT CSD201: HỆ THỐNG QUẢN LÝ SINH VIÊN VÀ ĐĂNG KÝ MÔN HỌC
## Đề tài 2 (Topic 2): Student Management System

> **Môn học:** Data Structures and Algorithms (CSD201)  
> **Phương pháp tiếp cận:** Computational Thinking (Tư duy tính toán)  
> **Ngôn ngữ:** Java (Tự cài đặt cấu trúc dữ liệu từ đầu theo chuẩn Slide môn học, không sử dụng `java.util.LinkedList`, `java.util.Queue`, `java.util.TreeSet`).

---

## 1. PHÂN TÍCH COMPUTATIONAL THINKING (CT ANALYSIS)
Dựa theo tài liệu **`CT_DSA_Problems.docx`** và slide **`CSD-Computational Thinking.pptx`**, bài toán được giải quyết thông qua 4 trụ cột chính:

### 1.1. Decomposition (Phân rã bài toán)
Chia bài toán quản lý sinh viên thành 3 phân hệ độc lập tương ứng với các cấu trúc dữ liệu tối ưu:
1. **Enrollment / Master Data Management:** Lưu trữ danh sách toàn bộ sinh viên trong trường $\rightarrow$ sử dụng **Singly Linked List (`MyList`)**.
2. **Course Registration Queue:** Quản lý luồng sinh viên gửi yêu cầu đăng ký môn học $\rightarrow$ sử dụng **Queue (`MyQueue`)** tuân thủ nguyên lý FIFO (ai đến trước đăng ký trước).
3. **ID-Based Searching & Indexing:** Tối ưu hóa tra cứu sinh viên theo mã số (ID) và xuất báo cáo thứ tự ID $\rightarrow$ sử dụng **Binary Search Tree (`BSTree`)**.

### 1.2. Pattern Recognition (Nhận diện mẫu)
- **Chu kỳ đăng ký môn lặp lại:** Vào mỗi đầu kỳ học, hàng trăm sinh viên cùng đăng ký môn. Để đảm bảo công bằng, yêu cầu cần được xử lý lần lượt theo đúng thứ tự thời gian nộp $\rightarrow$ Áp dụng mẫu hàng đợi FIFO (**Queue**).
- **Tra cứu sinh viên diễn ra liên tục:** Việc tìm kiếm sinh viên trên danh sách tuyến tính tốn chi phí $O(n)$. Ta nhận thấy mã sinh viên (ID) có tính chất thứ tự tự nhiên (Comparable) $\rightarrow$ Áp dụng cây nhị phân tìm kiếm (**BST**) để giảm thời gian xuống trung bình $O(\log n)$.

### 1.3. Abstraction (Trừu tượng hóa)
Tập trung vào các thuộc tính cốt lõi của bài toán, bỏ qua các thông tin rườm rà:
- **Class `Student`:**
  - `id` (Mã số sinh viên - Khóa chính định danh)
  - `name` (Họ và tên)
  - `major` (Chuyên ngành: SE, AI, IA,...)
  - `gpa` (Điểm trung bình tích lũy)
  - `courses` (Danh sách môn đã đăng ký thành công)
- **Class `RegistrationRequest`:**
  - `studentId` (Mã sinh viên gửi yêu cầu)
  - `courseCode` (Mã môn học cần đăng ký)
  - `requestTime` (Thời điểm gửi)

### 1.4. Algorithm Design (Thiết kế thuật toán)
- **Linked List (`MyList`):**
  - Thêm sinh viên vào cuối danh sách: $O(1)$.
  - Sắp xếp sinh viên theo GPA giảm dần bằng Selection Sort: $O(n^2)$.
  - Duyệt và xóa theo ID: $O(n)$.
- **Queue (`MyQueue`):**
  - Thêm yêu cầu đăng ký (`enqueue`): $O(1)$ tại tail.
  - Xử lý yêu cầu (`dequeue`): $O(1)$ tại head theo đúng chuẩn FIFO.
- **Binary Search Tree (`BSTree`):**
  - Chèn sinh viên theo ID: $O(\log n)$.
  - Tìm kiếm theo ID: $O(\log n)$ (nhanh vượt trội so với tìm kiếm tuần tự $O(n)$ trên List).
  - Duyệt In-Order (Left - Root - Right): tự động xuất danh sách sinh viên tăng dần theo ID.
  - Duyệt Breadth-First (theo từng tầng cây): kết hợp sử dụng Queue để duyệt cây từ trên xuống dưới.
  - Xóa Node bằng phương pháp sao chép (Delete by Copying): chuẩn theo Slide 4B.

---

## 2. CẤU TRÚC THƯ MỤC DỰ ÁN

```text
StudentManagementSystem/
├── run.bat                     # File chạy nhanh 1-click trên Windows
├── README.md                   # Tài liệu chi tiết dự án & kịch bản thuyết trình
├── bin/                        # Thư mục chứa các file .class sau khi biên dịch
└── src/                        # Mã nguồn Java
    ├── Student.java            # Model sinh viên
    ├── RegistrationRequest.java # Model yêu cầu đăng ký môn học
    ├── ListNode.java           # Node cho Singly Linked List
    ├── MyList.java             # Danh sách liên kết đơn (chuẩn Slide 1)
    ├── QueueNode.java          # Node cho Hàng đợi Queue
    ├── MyQueue.java            # Hàng đợi đăng ký môn học (chuẩn Slide 2)
    ├── TreeNode.java           # Node cho Cây nhị phân tìm kiếm BST
    ├── TreeQueue.java          # Queue phụ trợ duyệt BFS cây nhị phân
    ├── BSTree.java             # Cây nhị phân tìm kiếm BST (chuẩn Slide 4A, 4B)
    ├── StudentManager.java     # Lớp điều phối nghiệp vụ & kết nối 3 CT data structures
    └── Main.java               # Giao diện Console Menu (CLI) tương tác
```

---

## 3. BẢNG PHÂN TÍCH ĐỘ PHỨC TẠP THUẬT TOÁN (TIME COMPLEXITY)

| Chức năng | Cấu trúc dữ liệu | Best Case | Average Case | Worst Case | Ghi chú |
| :--- | :--- | :---: | :---: | :---: | :--- |
| **Thêm sinh viên mới** | Linked List (`addLast`) | $O(1)$ | $O(1)$ | $O(1)$ | Thêm ngay vào con trỏ `tail` |
| **Lập chỉ mục sinh viên** | BST (`insert`) | $O(1)$ | $O(\log n)$ | $O(n)$ | $O(n)$ khi cây bị lệch (skewed) |
| **Tìm kiếm sinh viên** | BST (`search`) | $O(1)$ | $O(\log n)$ | $O(n)$ | Giảm số lần so sánh rõ rệt so với List |
| **Thêm yêu cầu đăng ký** | Queue (`enqueue`) | $O(1)$ | $O(1)$ | $O(1)$ | Thêm vào cuối hàng đợi |
| **Xử lý đăng ký (FIFO)** | Queue (`dequeue`) | $O(1)$ | $O(1)$ | $O(1)$ | Lấy phần tử ở đầu hàng đợi |
| **In danh sách tăng dần ID** | BST (`inOrder`) | $O(n)$ | $O(n)$ | $O(n)$ | Duyệt Left - Node - Right |
| **Duyệt cây theo tầng** | BST (`breadthFirst`) | $O(n)$ | $O(n)$ | $O(n)$ | Kết hợp hàng đợi phụ trợ `TreeQueue` |
| **Xóa sinh viên** | BST (`deleteByCopying`) | $O(1)$ | $O(\log n)$ | $O(n)$ | Tìm nút thế mạng cực phải cây con trái |
| **Sắp xếp theo GPA** | Linked List (`SelectionSort`) | $O(n^2)$ | $O(n^2)$ | $O(n^2)$ | Hoán vị dữ liệu trực tiếp trên node |

---

## 4. HƯỚNG DẪN CÀI ĐẶT & CHẠY CHƯƠNG TRÌNH

### Cách 1: Chạy nhanh bằng script `run.bat` (Khuyên dùng trên Windows)
Chỉ cần nhấp đúp vào file `run.bat` (hoặc mở Terminal gõ `.\run.bat`). Chương trình sẽ tự động biên dịch mã nguồn và mở giao diện menu.

### Cách 2: Chạy thủ công bằng dòng lệnh (Command Line)
```bash
# Di chuyển vào thư mục dự án
cd StudentManagementSystem

# Biên dịch mã nguồn vào thư mục bin
javac --release 8 -encoding UTF-8 -d bin src/*.java

# Khởi chạy chương trình
java -cp bin Main
```

---

## 5. KỊCH BẢN DEMO THUYẾT TRÌNH LẤY ĐIỂM CAO TRƯỚC GIẢNG VIÊN

Khi lên thuyết trình trước thầy cô, nhóm nên trình bày theo 4 bước sau:

### Bước 1: Giới thiệu kiến trúc Computational Thinking
- Thưa thầy/cô, hệ thống của nhóm em giải quyết **Đề 2 - Student Management System** bằng cách phân rã bài toán thành 3 cấu trúc dữ liệu cốt lõi chuẩn slide:
  1. **Linked List (`MyList`):** Quản lý lưu trữ sinh viên.
  2. **Queue (`MyQueue`):** Xử lý hàng đợi đăng ký môn học theo đúng nguyên lý công bằng FIFO.
  3. **BST (`BSTree`):** Tối ưu tốc độ tra cứu và sắp xếp ID.

### Bước 2: Demo tính trực quan của dữ liệu mẫu
- Khi chương trình bật lên, hệ thống đã **tự động nạp sẵn 7 sinh viên mẫu** và **3 yêu cầu đang chờ trong hàng đợi**.
- Bấm **phím [2]**: Xem toàn bộ danh sách sinh viên được lưu trữ trong Linked List.
- Bấm **phím [7]**: Xem danh sách 3 sinh viên đang xếp hàng đợi đăng ký môn học (SE1801, SE1805, SE1807).

### Bước 3: Chứng minh ưu điểm của BST (O(log n) vs O(n))
- Bấm **phím [3]**, nhập mã `SE1804`:
  - Chương trình sẽ in ra số bước so sánh trên cây BST (chỉ mất 1 bước tại root).
  - So sánh đối chiếu trực tiếp với tìm kiếm tuần tự trên Linked List (mất 4 bước).
  - Đây là minh chứng rõ nhất cho việc ứng dụng BST giúp tối ưu hiệu năng.
- Bấm **phím [8]**: Trình bày thuật toán duyệt **In-Order** giúp danh sách sinh viên tự động hiển thị tăng dần theo MSSV (SE1801 $\rightarrow$ SE1807).
- Bấm **phím [9]**: Trình bày thuật toán duyệt **Breadth-First** (theo từng tầng cây nhị phân) dùng kết hợp Queue.

### Bước 4: Demo nguyên lý FIFO trên Hàng đợi (Queue)
- Bấm **phím [5]**: Xử lý 1 yêu cầu đăng ký môn học tiếp theo. Thấy rõ sinh viên SE1801 (vào hàng trước) được duyệt môn CSD201 trước.
- Bấm **phím [7]**: Thấy hàng đợi giảm còn 2 sinh viên.
- Bấm **phím [6]**: Xử lý sạch toàn bộ hàng đợi.
- Bấm **phím [2]**: Xem lại bảng sinh viên, cột "Môn đã đăng ký" đã được cập nhật chính xác cho các sinh viên vừa rồi.

---

## 6. HƯỚNG DẪN ĐẨY CODE LÊN GITHUB ĐỂ NỘP BÀI

Để lấy link GitHub nộp vào folder Google Drive theo yêu cầu của thầy:

```bash
# 1. Di chuyển vào thư mục dự án
cd StudentManagementSystem

# 2. Khởi tạo kho chứa Git
git init

# 3. Tạo file .gitignore để không đẩy file rác lên GitHub
echo bin/ > .gitignore
echo *.class >> .gitignore

# 4. Thêm toàn bộ file vào commit
git add .
git commit -m "feat: complete CSD201 Student Management System project"

# 5. Đổi tên branch chính thành main
git branch -M main

# 6. Tạo 1 repository mới trên GitHub (vd: CSD201_StudentManagementSystem) và liên kết:
git remote add origin https://github.com/<tai-khoan-cua-ban>/CSD201_StudentManagementSystem.git

# 7. Đẩy code lên GitHub
git push -u origin main
```
Sau đó copy đường link repo GitHub (ví dụ: `https://github.com/username/CSD201_StudentManagementSystem`) dán vào file nộp của nhóm!

