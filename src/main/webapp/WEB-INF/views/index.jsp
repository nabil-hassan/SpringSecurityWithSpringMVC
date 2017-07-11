<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>

<head>
    <%@ include file="include/header.jsp"%>
    <spring:url value="/logout" var="logout_url"/>

    <title>Welcome to Spring MVC Java Config</title>
</head>

<body>

<div class="main">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <h2>Welcome ${pageContext.request.userPrincipal.name}</h2>
    </c:if>

    <ul class="anchorList">
        <sec:authorize access="hasAuthority('view')">
            <li> <a href="customerList">Customer List</a> </li>
        </sec:authorize>

        <sec:authorize access="hasAnyAuthority('create', 'edit')">
            <li> <a href="customerForm">Add/Edit Customer</a> </li>
        </sec:authorize>


        <sec:authorize access="hasAuthority('view') and hasAuthority('delete')">
            <li> <a href="customerForm">Remove Customer</a> </li>
        </sec:authorize>
    </ul>


    <c:url var="logoutUrl" value="/logout"/>
    <form action="${logoutUrl}" id="logout" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <a href="#" onclick="document.getElementById('logout').submit();">Logout</a>
</div>

</body>

<footer>
    <%@ include file="include/footer.jsp"%>
</footer>

</html>
