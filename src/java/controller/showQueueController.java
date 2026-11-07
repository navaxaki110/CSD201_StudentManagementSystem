package controller;

import dto.RegistrationRequest;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.StudentService;

@WebServlet(name = "showQueueController", urlPatterns = {"/showQueueController"})
public class showQueueController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        StudentService studentService = (StudentService) getServletContext().getAttribute("STUDENT_SERVICE");
        if (studentService == null) {
            studentService = new StudentService(getServletContext());
            getServletContext().setAttribute("STUDENT_SERVICE", studentService);
        }

        String action = request.getParameter("action");
        String message = null;

        // Process / Approve next request in FIFO order
        if ("approve".equalsIgnoreCase(action)) {
            RegistrationRequest approved = studentService.approveNextRequest();
            if (approved != null) {
                message = "Đã phê duyệt thành công yêu cầu: MSSV " + approved.getStudentId() 
                        + " đăng ký môn " + approved.getCourseCode() + "!";
            } else {
                message = "Hàng đợi đang rỗng, không có yêu cầu nào cần duyệt!";
            }
        }

        HttpSession session = request.getSession();
        session.setAttribute("LISTQUEUE", studentService.getPendingRequests());
        request.setAttribute("MESSAGE", message);
        request.setAttribute("QUEUE_COUNT", studentService.getPendingRequests().size());
        request.getRequestDispatcher("queuePage.jsp").forward(request, response);
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
        return "Registration Queue Controller";
    }
}
