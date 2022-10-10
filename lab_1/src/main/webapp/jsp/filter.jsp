<%--
  Created by IntelliJ IDEA.
  User: 1395353
  Date: 16.08.2021
  Time: 12:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/filter_style.css" type="text/css">
    <script language="JavaScript" type="text/JavaScript" src="${pageContext.request.contextPath}/js/filter.js"></script>
</head>
<style>
    <%@ include file="/css/filter_style.css" %>
</style>
<body>
<form method="get" class="filter-form paginatorForm" name="numberOfRecordsPerPageForm">
    <p> Number of attributes on the page </p>
    <select id="numberOfRecordsPerPage" class="form-select" name="numberOfRecordsPerPage"
            onchange="changePagesQuality(${humanBeingsLength})">
        <option selected value="5">5</option>
        <option value="10">10</option>
        <option value="25">25</option>
    </select>
    <p> Selected Page </p>
    <div class="selectedPage"></div>
    <select id="selectedPage" name="selectedPage">
        <c:forEach var="item" items='${pagesQuality}'>
            <option value="${item}">${item}</option>
        </c:forEach>
    </select>
</form>
<ul class="nav nav-tabs filter-tabs" data-tabs="tabs" id="filter-tab">
    <li class="nav-item active">
        <a class="nav-link active" data-toggle="tab" href="#filter">Filter</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" data-toggle="tab" href="#sort">Sort</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" data-toggle="tab" href="#delete-mood">Delete mood</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" data-toggle="tab" href="#name">Name</a>
    </li>
</ul>
<%--Фильтрация--%>
<div class="tab-content">
    <div class="tab-pane fade show active" id="filter">
        <form action="filter" method="get" class="filter-form" name="filterForm">
            <p><input class="form-check-input isNameDisabled" type="checkbox"> Name:
                <input type="text" name="name" class="form-control Name" disabled/></p>

            <p>Coordinates:</p>

            <p><input class="form-check-input isXDisabled" type="checkbox"> X: </p>
            <div class="filter-form__range">
                <input type="text" name="x1" class="form-control X" disabled/> - <input type="text" name="x2"
                                                                                        class="form-control X"
                                                                                        disabled/></div>

            <p><input class="form-check-input isYDisabled" type="checkbox"> Y: </p>
            <div class="filter-form__range">
                <input type="text" name="y1" class="form-control Y" disabled/> - <input type="text" name="y2"
                                                                                        class="form-control Y"
                                                                                        disabled/></div>

            <p>Creation date:</p>

            <p><input class="form-check-input isDateDisabled" type="checkbox"> Date:</p>
            <div class="filter-form__range">
                <input type="date" name="start-creation-date" class="form-control Date" disabled> - <input type="date"
                                                                                                           name="end-creation-date"
                                                                                                           class="form-control Date"
                                                                                                           disabled>
            </div>

            <p>Time:</p>
            <div class="filter-form__range">
                <input type="time" name="start-creation-time" value="00:00" class="form-control Date" disabled> - <input
                    type="time"
                    name="end-creation-time"
                    value="23:59"
                    class="form-control Date" disabled>
            </div>

            <p><input class="form-check-input isRealHeroDisabled" type="checkbox"> Real Hero:
            <div class="form-check form-check-inline">
                <select class="form-check-input RealHero" name="realHero" disabled>
                    <option value="true">true</option>
                    <option value="false">false</option>
                </select>
            </div>
            </p>


            <p><input class="form-check-input isHasToothpickDisabled" type="checkbox"> HasToothpick:
            <div class="form-check form-check-inline">
                <select class="form-check-input HasToothpick" name="hasToothpick" disabled>
                    <option value="true">true</option>
                    <option value="false">false</option>
                </select>
            </div>
            </p>

            <p><input class="form-check-input isImpactSpeedDisabled" type="checkbox"> Impact Speed: </p>
            <div class="filter-form__range">
                <input type="text" name="impactSpeed1" class="form-control ImpactSpeed" disabled/> - <input type="text"
                                                                                                            name="impactSpeed2"
                                                                                                            class="form-control ImpactSpeed"
                                                                                                            disabled/>
            </div>


            <p><input class="form-check-input isMinutesOfWaitingDisabled" type="checkbox"> Minutes of waiting: </p>
            <div class="filter-form__range">
                <input type="text" name="minutesOfWaiting1" class="form-control MinutesOfWaiting" disabled/> - <input
                    type="text" name="minutesOfWaiting2" class="form-control MinutesOfWaiting" disabled/></div>


            <p><input class="form-check-input isWeaponTypeDisabled" type="checkbox"> Weapon Type:
            <div class="form-check form-check-inline">
                <input class="form-check-input WeaponType" name="weaponType" type="checkbox" id="weaponType1"
                       value="PISTOL" disabled>
                <label class="form-check-label" for="weaponType1">PISTOL</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input WeaponType" name="weaponType" type="checkbox" id="weaponType2"
                       value="SHOTGUN" disabled>
                <label class="form-check-label" for="weaponType2">SHOTGUN</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input WeaponType" name="weaponType" type="checkbox" id="weaponType3"
                       value="MACHINE_GUN" disabled>
                <label class="form-check-label" for="weaponType3">MACHINE GUN</label>
            </div>
            </p>

            <p><input class="form-check-input isMoodDisabled" type="checkbox"> Mood:
            <div class="form-check form-check-inline">
                <input class="form-check-input Mood" name="mood" type="checkbox" id="mood1"
                       value="LONGING" disabled>
                <label class="form-check-label" for="mood1">LONGING</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input Mood" name="mood" type="checkbox" id="mood2"
                       value="GLOOM" disabled>
                <label class="form-check-label" for="mood2">GLOOM</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input Mood" name="mood" type="checkbox" id="mood3"
                       value="APATHY" disabled>
                <label class="form-check-label" for="mood3">APATHY</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input Mood" name="mood" type="checkbox" id="mood4"
                       value="FRENZY" disabled>
                <label class="form-check-label" for="mood4">FRENZY</label>
            </div>
            </p>

            <p><input class="form-check-input isCoolDisabled" type="checkbox"> Car:
            <div class="form-check form-check-inline">
                <select class="form-check-input Cool" name="cool" disabled>
                    <option value="true">true</option>
                    <option value="false">false</option>
                </select>
            </div>
            </p>

            <input type="submit" class="btn btn-primary filter-btn" id="filter-btn" value="find"/>
        </form>
    </div>


<%--    Сортировка--%>
        <div class="tab-pane fade" id="sort">
            <form action="sort" method="get" class="filter-form" name="sortForm">
                <input type="radio" class="form-check-input" name="sortBy" value="id" checked> id<BR>
                <input type="radio" class="form-check-input" name="sortBy" value="name"> name<BR>
                <input type="radio" class="form-check-input" name="sortBy" value="x"> x<BR>
                <input type="radio" class="form-check-input" name="sortBy" value="y"> y<BR>
                <input type="radio" class="form-check-input" name="sortBy" value="creationdate"> creationdate<BR>
                <input type="radio" class="form-check-input" name="sortBy" value="realHero"> real hero<BR>
                <input type="radio" class="form-check-input" name="sortBy" value="hasToothpick"> has toothpick<BR>
                <input type="radio" class="form-check-input" name="sortBy" value="impactSpeed"> impact speed<BR>
                <input type="radio" class="form-check-input" name="sortBy" value="minutesOfWaiting"> minutes of waiting<BR>
                <input type="radio" class="form-check-input" name="sortBy" value="weaponType"> weapon type<BR>
                <input type="radio" class="form-check-input" name="sortBy" value="mood"> mood<BR>
                <input type="radio" class="form-check-input" name="sortBy" value="car"> car<BR>

                Order: <BR>
                <input type="radio" class="form-check-input" name="order" value="ASC" checked> ASC<BR>
                <input type="radio" class="form-check-input" name="order" value="DESC"> DESC<BR>

                <input type="submit" class="btn btn-primary name-filter-btn" value="sort"/>
            </form>
        </div>
        <div class="tab-pane fade" id="delete-mood">
            <form action="filterByMood" onsubmit="return deleteByMood(event);" method="get" class="filter-form" name="filterByMood">
                    <select class="form-check-input" name="delete_by_mood">
                    <option value="LONGING">LONGING</option>
                    <option value="GLOOM">GLOOM</option>
                    <option value="APATHY">APATHY</option>
                    <option value="FRENZY">FRENZY</option>
                    </select>
                <input type="submit" class="btn btn-primary name-filter-btn" value="delete"/>
            </form>
        </div>
        <div class="tab-pane fade" id="name">
            <form action="filterByName" method="get" class="filter-form" name="filterByName">
                <p> Name: <input type="text" name="start-name" class="form-control"/></p>
                <input type="submit" class="btn btn-primary name-filter-btn" value="find"/>
            </form>
        </div>

</div>
<script>
    <%@ include file="/js/filter.js" %>
</script>
</body>
</html>
