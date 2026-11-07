/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

public class RegistrationRequest {
    private String studentId;
    private String courseCode;
    private String requestTime;

    public RegistrationRequest() {
    }

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
        return String.format("%s | %s | %s", studentId, courseCode, requestTime);
    }
}
