<%--
  Created by IntelliJ IDEA.
  User: 1395353
  Date: 12.08.2021
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-sm">
    <ul class="navbar-nav">
        <li class="nav-item ${param.list}">
            <a class="nav-link" href="/lab1/humanBeings">HumanBeing List</a>
        </li>
        <li class="nav-item ${param.new}">
            <a class="nav-link" href="/lab1/pages/add-humanBeing-form">New HumanBeing</a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${param.id}" href="/lab1/pages/get-by-id-form">Get HumanBeing by id</a>
        </li>
    </ul>
</nav>
</body>
</html>
