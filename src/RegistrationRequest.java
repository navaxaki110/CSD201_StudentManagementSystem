public class RegistrationRequest {
    private String studentId;
    private String courseCode;
    private String requestTime;

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
