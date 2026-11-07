package controller;

import dto.Student;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.StudentService;
import service.StudentTreeService;

@WebServlet(name = "showStudentController", urlPatterns = {"/showStudentController"})
public class showStudentController extends HttpServlet {
    private StudentService studentService;
    private StudentTreeService treeService;

    @Override
    public void init() throws ServletException {
        studentService = (StudentService) getServletContext().getAttribute("STUDENT_SERVICE");
        if (studentService == null) {
            studentService = new StudentService(getServletContext());
            getServletContext().setAttribute("STUDENT_SERVICE", studentService);
        }
        treeService = new StudentTreeService(studentService.getStudentBST());
        System.out.println("=== StudentService loaded: " + studentService.getAllStudents().size() + " students ===");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Ensure services are loaded
        if (studentService == null) {
            init();
        }

        String action = request.getParameter("action");
        String message = null;

        // Action: Enroll course for student
        if ("enroll".equalsIgnoreCase(action)) {
            String studentId = request.getParameter("studentId");
            String courseCode = request.getParameter("courseCode");
            if (studentId != null && courseCode != null && !studentId.trim().isEmpty() && !courseCode.trim().isEmpty()) {
                boolean ok = studentService.requestCourse(studentId.trim(), courseCode.trim().toUpperCase());
                if (ok) {
                    message = "Đã đưa yêu cầu đăng ký môn " + courseCode.toUpperCase() + " của sinh viên " + studentId + " vào hàng đợi FIFO!";
                } else {
                    message = "Lỗi: Không tìm thấy sinh viên hoặc sinh viên đã đăng ký môn này rồi!";
                }
            }
        }
        // Action: Add new student
        else if ("add".equalsIgnoreCase(action)) {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String major = request.getParameter("major");
            String gpaStr = request.getParameter("gpa");
            try {
                double gpa = Double.parseDouble(gpaStr);
                boolean ok = studentService.addStudent(new Student(id.trim(), name.trim(), major.trim(), gpa));
                if (ok) {
                    message = "Thêm mới sinh viên " + id + " thành công!";
                } else {
                    message = "Lỗi: Mã sinh viên " + id + " đã tồn tại!";
                }
            } catch (Exception e) {
                message = "Lỗi định dạng dữ liệu sinh viên!";
            }
        }

        String keyword = request.getParameter("KEYWORD");
        String sortBy = request.getParameter("sortBy");
        List<Student> studentList;

        if ("gpa".equalsIgnoreCase(sortBy)) {
            studentList = studentService.getStudentsSortedByGpa();
        } else if (keyword != null && !keyword.trim().isEmpty()) {
            studentList = treeService.searchByKeyword(keyword.trim());
        } else {
            studentList = studentService.getAllStudents();
            keyword = "";
        }

        request.setAttribute("STUDENTLIST", studentList);
        request.setAttribute("KEYWORD", keyword != null ? keyword : "");
        request.setAttribute("SORTBY", sortBy != null ? sortBy : "");
        request.setAttribute("TOTALSTUDENT", studentService.getAllStudents().size());
        request.setAttribute("MESSAGE", message);

        RequestDispatcher rd = request.getRequestDispatcher("studentPage.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Student Presentation Controller";
    }
}
