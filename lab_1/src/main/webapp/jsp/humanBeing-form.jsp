<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <title>HumanBeings</title>
    <script language="JavaScript" type="text/JavaScript"
            src="${pageContext.request.contextPath}/js/humanBeingForm.js"></script>
</head>
<body>
<jsp:include page="menu.jsp">
    <jsp:param name="new" value="active"/>
</jsp:include>
<style>
    <%@ include file="/css/form.css" %>
</style>
<div class="humanBeing-form">
    <caption>
        <h2>
            <c:if test="${humanBeing != null}">
                Edit HumanBeing
            </c:if>
            <c:if test="${humanBeing == null}">
                Add New HumanBeing
            </c:if>
        </h2>
    </caption>
    <div class="error-msg">
    </div>
    <c:if test="${humanBeing != null}">
    <div align="center">
        <form name="updateHumanBeingForm"></c:if>
            <c:if test="${humanBeing == null}">
            <form action="insert" method="post" align="center" name="addHumanBeingForm"></c:if>
                <c:if test="${humanBeing != null}">
                    <input type="hidden" name="id" value="<c:out value='${humanBeing.id}' />" class="form-control"/>
                </c:if>
                <p>Name:
                    <input type="text" name="name" value="<c:out value='${humanBeing.name}' />" class="form-control"/>
                </p>

                <p>X:
                    <input type="text" name="x" value="<c:out value='${humanBeing.coordinates.x}' />"
                           class="form-control"/>
                </p>

                <p>Y:
                    <input type="text" name="y" value="<c:out value='${humanBeing.coordinates.y}' />"
                           class="form-control"/>
                </p>

                <p>Real hero:
                    <select name="realHero" value="<c:out value='${humanBeing.realHero}' />" class="form-control">
                        <option value="true">true</option>
                        <option value="false">false</option>
                    </select></p>

                <p>Has Toothpick:
                    <select name="hasToothpick" value="<c:out value='${humanBeing.hasToothpick}' />"
                            class="form-control">
                        <option value="true">true</option>
                        <option value="false">false</option>
                    </select></p>

                <p>Impact speed:
                    <input type="text" name="impactSpeed" value="<c:out value='${humanBeing.impactSpeed}' />"
                           class="form-control"/>
                </p>

                <p>Weapon type:
                    <select name="weaponType" value="<c:out value='${humanBeing.weaponType}' />" class="form-control">
                        <option value=" "></option>
                        <option value="PISTOL">PISTOL</option>
                        <option value="SHOTGUN">SHOTGUN</option>
                        <option value="MACHINE_GUN">MACHINE_GUN</option>
                    </select></p>

                <p>Mood:
                    <select name="mood" value="<c:out value='${humanBeing.mood}' />"
                            class="form-control">
                        <option value="LONGING">LONGING</option>
                        <option value="GLOOM">GLOOM</option>
                        <option value="APATHY">APATHY</option>
                        <option value="FRENZY">FRENZY</option>
                    </select></p>

                <p>Car cool:
                    <select name="cool" value="<c:out value='${humanBeing.car.cool}' />" class="form-control">
                        <option value="true">true</option>
                        <option value="false">false</option>
                    </select>
                </p>
                <p>Minutes of waiting:
                    <input type="text" name="minutesOfWaiting" value="<c:out value='${humanBeing.minutesOfWaiting}' />"
                           class="form-control"/>
                </p>

                <p>CreationDate:</p>
                <p>Date: <input type="date" name="creation-date"
                                value='${humanBeing.getCustomFormatterDate()}'
                                class="form-control"></p>
                <p>Time: <input type="time" name="creation-time"
                                value='${humanBeing.getCustomFormatterTime()}' required
                                class="form-control"></p>

                <c:if test="${humanBeing == null}">
                    <input type="submit" class="btn btn-primary mx-auto mt-2" value="Add"/></c:if>
            </form>
            <c:if test="${humanBeing != null}">
            <button class="btn btn-primary mx-auto mt-2" onclick="updateHumanBeing()">Update</button>
            </c:if>
    </div>
</div>
<script>
    <%@ include file="/js/humanBeingForm.js" %>
</script>
</body>
</html>
