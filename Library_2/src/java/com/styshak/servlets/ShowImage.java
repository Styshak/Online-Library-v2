package com.styshak.servlets;

import com.styshak.db.DataAccess;
import com.styshak.db.MySQLDataAccessImpl;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowImage extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("image/jpeg");
        DataAccess da = new MySQLDataAccessImpl();
        try (OutputStream out = response.getOutputStream();) {
            int book_id = Integer.parseInt(request.getParameter("book_id"));
            byte[] image = da.getBookImage(book_id);
            response.setContentLength(image.length);
            out.write(image);
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
