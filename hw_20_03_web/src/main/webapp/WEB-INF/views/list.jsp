<%--
  Created by IntelliJ IDEA.
  User: major
  Date: 24.03.2026
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%-- list.jsp - простий варіант без JSTL --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.contactapp.model.Contact" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Список контактів</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
        }
        table {
            border-collapse: collapse;
            width: 80%;
            margin: 20px 0;
        }
        th, td {
            border: 1px solid #999;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f4f4f4;
        }
        a {
            text-decoration: none;
            color: blue;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<h2>Список контактів</h2>

<p><a href="add">➕ Додати новий контакт</a></p>

<table>
    <tr>
        <th>ID</th>
        <th>Ім'я</th>
        <th>Прізвище</th>
        <th>Телефон</th>
        <th>Дії</th>
    </tr>

    <%
        List<Contact> contacts = (List<Contact>) request.getAttribute("contacts");
        if (contacts != null && !contacts.isEmpty()) {
            for (Contact c : contacts) {
    %>
    <tr>
        <td><%= c.getId() %></td>
        <td><%= c.getFirstName() %></td>
        <td><%= c.getLastName() %></td>
        <td><%= c.getPhone() %></td>
        <td>
            <a href="edit?id=<%= c.getId() %>">Редагувати</a> |
            <a href="delete?id=<%= c.getId() %>"
               onclick="return confirm('Видалити цей контакт?')">Видалити</a>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="5" style="text-align:center; color:gray;">
            Контактів поки немає. Додайте перший!
        </td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>