public class Student {
    private String id;        // Mã sinh viên (Khóa định danh để tìm kiếm và lập chỉ mục trong BST)
    private String name;      // Họ và tên sinh viên
    private String major;     // Chuyên ngành (SE, IA, AI, CS,...)
    private double gpa;       // Điểm trung bình tích lũy
    private String courses;   // Danh sách môn học đã đăng ký thành công (cách nhau bởi dấu phẩy)

    public Student() {
        this.courses = "Chua dang ky";
    }

    public Student(String id, String name, String major, double gpa) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.gpa = gpa;
        this.courses = "Chua dang ky";
    }

    public Student(String id, String name, String major, double gpa, String courses) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.gpa = gpa;
        this.courses = (courses == null || courses.trim().isEmpty()) ? "Chua dang ky" : courses;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    // Phương thức thêm môn học đã đăng ký
    public void addCourse(String courseCode) {
        if (this.courses.equals("Chua dang ky") || this.courses.isEmpty()) {
            this.courses = courseCode;
        } else {
            // Kiểm tra xem môn đã có trong danh sách chưa
            if (!hasCourse(courseCode)) {
                this.courses += ", " + courseCode;
            }
        }
    }

    public boolean hasCourse(String courseCode) {
        if (this.courses == null || this.courses.equals("Chua dang ky")) {
            return false;
        }
        String[] list = this.courses.split(",\\s*");
        for (String c : list) {
            if (c.equalsIgnoreCase(courseCode)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("| %-10s | %-20s | %-12s | %-5.2f | %-22s |", 
                id, name, major, gpa, courses);
    }
}

