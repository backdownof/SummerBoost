<%--
  Created by IntelliJ IDEA.
  User: ab
  Date: 11.02.2022
  Time: 19:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Игроки сервера</h1>
<c:if test="${not empty players}">
    <ul>
        <c:forEach var="player" items="${requestScope.players}">
            <li>${fn:toUpperCase(player.nickname)}</li>
        </c:forEach>
    </ul>
</c:if>
</body>
</html>
