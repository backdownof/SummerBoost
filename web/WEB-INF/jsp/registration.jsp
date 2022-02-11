<%--
  Created by IntelliJ IDEA.
  User: ab
  Date: 11.02.2022
  Time: 19:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/registration" method="post">
    <label for="name">Telegram Username:
        <input type="text" name="telegram_username" id="name">
    </label>
    <br/>
    <label for="email">Email:
        <input type="text" name="email" id="email">
    </label>
    <br/>
    <label for="password">Пароль:
        <input type="password" name="password" id="password">
    </label>
    <br/>
    <label for="password2">Повторите пароль:
        <input type="password" name="password2" id="password2">
    </label>
    <br/>
    <button type="submit">Отправить</button>
</form>
</body>
</html>
