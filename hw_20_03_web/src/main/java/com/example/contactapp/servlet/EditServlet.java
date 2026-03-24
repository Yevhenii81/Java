package com.example.contactapp.servlet;

import com.example.contactapp.dao.ContactDAO;
import com.example.contactapp.model.Contact;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private ContactDAO dao = new ContactDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("contact", dao.getById(id));
        req.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String first = req.getParameter("firstName");
        String last = req.getParameter("lastName");
        String phone = req.getParameter("phone");

        Contact c = new Contact(first, last, phone);
        c.setId(id);
        dao.update(c);
        resp.sendRedirect("contacts");
    }
}