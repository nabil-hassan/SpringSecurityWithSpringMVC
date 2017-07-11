<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <title>Login</title>
    <%@ include file="include/header.jsp" %>
    <spring:url var="login_form_submit" value="/loginForm/submit"/>
    <spring:url var="create_user_form_submit" value="/createUserForm/submit"/>
    <spring:url var="loginUrl" value="/login"/>
</head>

<body>
<div id="loginForm" class="smallForm">
    <form action="${loginUrl}" method="post">
        <div id="loginMessages">
            <c:if test="${param.error != null}">
                <p>
                    Invalid username and password.
                </p>
            </c:if>
            <c:if test="${param.logout != null}">
                <p>
                    You have been logged out.
                </p>
            </c:if>
        </div>

        <div id="loginFields">
            <div id="usernameField" class="formField">
                 <span class="formField">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username"/>
                 </span>
            </div>

            <div id="passwordField" class="formField">
                 <span class="formField">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password"/>
                 </span>
            </div>

            <div id="submitField" class="formSubmitField">
                <button type="submit" class="btn">Log in</button>
            </div>
        </div>

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>
</body>

</html>