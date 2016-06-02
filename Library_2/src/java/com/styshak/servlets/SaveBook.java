/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.styshak.servlets;

import com.styshak.db.DataAccess;
import com.styshak.db.MySQLDataAccessImpl;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sergey
 */
@WebServlet(name = "SaveBook", urlPatterns = {"/SaveBook"})
public class SaveBook extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");
        DataAccess da = new MySQLDataAccessImpl();
        try (OutputStream out = response.getOutputStream()) {
            int book_id = Integer.parseInt(request.getParameter("book_id"));
            byte[] content = da.getBookContent(book_id);
            String fileName = da.getBookName(book_id) + ".pdf";     
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            out.write(content);
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
