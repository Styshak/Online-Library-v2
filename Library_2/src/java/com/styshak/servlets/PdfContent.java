package com.styshak.servlets;

import com.styshak.beans.Book;
import com.styshak.controllers.BooksListController;
import com.styshak.db.DataAccess;
import com.styshak.db.MySQLDataAccessImpl;
import com.styshak.utils.BookUtils;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PdfContent extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");
        DataAccess da = new MySQLDataAccessImpl();
        try (OutputStream out = response.getOutputStream()) {
            int book_id = Integer.valueOf(request.getParameter("book_id"));
            BooksListController searchController = (BooksListController)request.getSession(false).getAttribute("booksListController");
            Book book = BookUtils.getBookById(searchController.getCurrentBookList(), book_id);
            byte[] content = da.getBookContent(book_id);
            if(book != null) {
                book.setContent(content);
            }          
            response.setContentLength(content.length);
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
