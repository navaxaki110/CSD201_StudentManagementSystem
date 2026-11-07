/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UserDAO;
import dto.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "loginController", urlPatterns = {"/loginController"})
public class loginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String userID = request.getParameter("userID");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        HttpSession session = request.getSession();
        UserDTO userDTO = userDAO.checklogin(userID, password);

        if (userDTO != null) {
            session.setAttribute("USERID", userDTO.getUserID());
            session.setAttribute("USERFULLNAME", userDTO.getFullname());
            session.setAttribute("USER", userDTO);
            response.sendRedirect("showStudentController");
        } else {
            request.setAttribute("ERROR", "Sai mã đăng nhập hoặc mật khẩu! (Thử: admin / 123 hoặc SE205280 / 123)");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
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
}
