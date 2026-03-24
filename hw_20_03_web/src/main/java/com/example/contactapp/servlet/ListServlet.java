package com.example.contactapp.servlet;

import com.example.contactapp.dao.ContactDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/contacts")
public class ListServlet extends HttpServlet {
    private ContactDAO dao = new ContactDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        dao.createTableIfNotExists();
        req.setAttribute("contacts", dao.getAll());
        req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
    }
}