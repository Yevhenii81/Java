package com.example.contactapp.servlet;

import com.example.contactapp.dao.ContactDAO;
import com.example.contactapp.model.Contact;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/add")
public class AddServlet extends HttpServlet {
    private ContactDAO dao = new ContactDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String first = req.getParameter("firstName");
        String last = req.getParameter("lastName");
        String phone = req.getParameter("phone");

        dao.add(new Contact(first, last, phone));
        resp.sendRedirect("contacts");
    }
}