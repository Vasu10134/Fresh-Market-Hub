package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import utils.DBConnection;

public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE email = ? AND password = ?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            PrintWriter out = response.getWriter();
            if (rs.next()) {
                out.println("<h2>Login Successful! Welcome, " + rs.getString("name") + "</h2>");
            } else {
                out.println("<h2>Invalid Credentials. Try Again.</h2>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
