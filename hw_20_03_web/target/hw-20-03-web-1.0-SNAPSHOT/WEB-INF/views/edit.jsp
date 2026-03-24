<%--
  Created by IntelliJ IDEA.
  User: major
  Date: 24.03.2026
  Time: 20:37
  To change this template use File | Settings | File Templates.
--%>
<%-- edit.jsp - простий варіант --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.contactapp.model.Contact" %>
<%
    Contact contact = (Contact) request.getAttribute("contact");
%>
<html>
<head>
    <title>Редагувати контакт</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        input[type="text"] { width: 350px; padding: 8px; margin: 8px 0; }
        input[type="submit"] { padding: 10px 20px; font-size: 16px; }
    </style>
</head>
<body>
<h2>Редагувати контакт</h2>

<form method="post" action="edit">
    <input type="hidden" name="id" value="<%= contact.getId() %>">

    Ім'я:<br>
    <input type="text" name="firstName" value="<%= contact.getFirstName() %>" required><br><br>

    Прізвище:<br>
    <input type="text" name="lastName" value="<%= contact.getLastName() %>" required><br><br>

    Номер телефону:<br>
    <input type="text" name="phone" value="<%= contact.getPhone() %>" required><br><br>

    <input type="submit" value="Зберегти зміни">
</form>

<br><br>
<a href="contacts">← Назад до списку</a>
</body>
</html>