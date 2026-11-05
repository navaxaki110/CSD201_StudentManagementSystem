public class RegistrationRequest {
    private String studentId;     // Mã sinh viên gửi yêu cầu
    private String courseCode;    // Mã môn học muốn đăng ký (vd: CSD201, PRF192)
    private String requestTime;   // Thời gian gửi yêu cầu

    public RegistrationRequest(String studentId, String courseCode, String requestTime) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.requestTime = requestTime;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    @Override
    public String toString() {
        return String.format("[Yeu cau] MSSV: %-9s | Mon: %-8s | Luc: %s", 
                studentId, courseCode, requestTime);
    }
}

