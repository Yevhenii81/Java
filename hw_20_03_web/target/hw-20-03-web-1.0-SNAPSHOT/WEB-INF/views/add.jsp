<%--
  Created by IntelliJ IDEA.
  User: major
  Date: 24.03.2026
  Time: 20:36
  To change this template use File | Settings | File Templates.
--%>
<%-- add.jsp - простий варіант --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Додати контакт</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 30px; }
    input[type="text"] { width: 350px; padding: 8px; margin: 8px 0; }
    input[type="submit"] { padding: 10px 20px; font-size: 16px; }
  </style>
</head>
<body>
<h2>Додати новий контакт</h2>

<form method="post" action="add">
  Ім'я:<br>
  <input type="text" name="firstName" required><br><br>

  Прізвище:<br>
  <input type="text" name="lastName" required><br><br>

  Номер телефону:<br>
  <input type="text" name="phone" required><br><br>

  <input type="submit" value="Додати контакт">
</form>

<br><br>
<a href="contacts">← Назад до списку</a>
</body>
</html>