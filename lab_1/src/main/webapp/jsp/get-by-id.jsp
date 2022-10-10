<%--
  Created by IntelliJ IDEA.
  User: 1395353
  Date: 12.08.2021
  Time: 19:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <title>GetByID</title>
</head>
<style>
    <%@ include file="/css/main.css" %>
</style>
<body>
<jsp:include page="menu.jsp">
    <jsp:param name="id" value="active" />
</jsp:include>
<form align="center" action="/lab1/humanBeings" method="get" style="margin-top: 20px">
    <caption><h2>Get humanBeing by id</h2></caption>
    <input class="form-control mt-3" type="text" name="id" value="0" style="width: 30%; margin: 0 auto;"/>
    <c:if test="${humanBeing == null && msg != null}">
        <div class="mx-auto" style="color: red">
            <h7>${msg}</h7>       </div>
    </c:if>
    <input type="submit" name="get" class="btn btn-primary mx-auto mt-3" value="find"/>
</form>
<c:if test="${humanBeing != null}">
    <div align="center" class="mx-5">
        <table class="table">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>X</th>
                <th>Y</th>
                <th>Creation Date</th>
                <th>Real Hero</th>
                <th>Has Toothpick</th>
                <th>Impact Speed</th>
                <th>Minutes Of Waiting</th>
                <th>Weapon Type</th>
                <th>Mood</th>
                <th>Car</th>
            </tr>
            <tr>
                <td>${humanBeing.id}</td>
                <td>${humanBeing.name}</td>
                <td>${humanBeing.coordinates.x}</td>
                <td>${humanBeing.coordinates.y}</td>
                <td>${humanBeing.getCustomFormatter()}</td>
                <td>${humanBeing.realHero}</td>
                <td>${humanBeing.hasToothpick}</td>
                <td>${humanBeing.impactSpeed}</td>
                <td>${humanBeing.minutesOfWaiting}</td>
                <td>${humanBeing.weaponType}</td>
                <td>${humanBeing.mood}</td>
                <td>${humanBeing.car.cool}</td>
            </tr>
        </table>
    </div>
</c:if>
</body>
</html>
