<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <meta charset="utf-8">
</head>
<style>
    <%@ include file="/css/main.css" %>
</style>
<body>
<jsp:include page="menu.jsp">
    <jsp:param name="list" value="active"/>
</jsp:include>
<div class="main-page">
    <div class="table" align="center">
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
                <th>Action</th>
            </tr>
            <c:forEach var="humanBeing" items="${humanBeings}">
            <c:if test="${humanBeing != null}">
            <tr class="table-rows">
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

                <td>
                    <a href="pages/edit-form?id=${humanBeing.id}">Edit</a>
                    <button class="btn btn-primary mx-auto mt-2" onclick="deleteHumanBeing(${humanBeing.id});">Delete</button>
                </td>
            </tr>
            </c:if>
            </c:forEach>
        </table>
    </div>
        <div class="filter">
            <jsp:include page="filter.jsp"/>
        </div>
</div>
</body>
</html>