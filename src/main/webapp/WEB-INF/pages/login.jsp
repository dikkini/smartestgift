
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<fmt:requestEncoding value="utf-8" />

<html>
<head>
    <title>Sign In</title>
</head>
<body>
<h1>Spring Security - Sign In</h1>

<form class="login-form" action="j_spring_security_check" method="post">
    <label for="j_username">Username: </label>
    <input id="j_username" name="j_username" size="20" maxlength="50" type="text" />

    <label for="j_password">Password: </label>
    <input id="j_password" name="j_password" size="20" maxlength="50" type="password" />

    <label for="_spring_security_remember_me">Remeber me: </label>
    <input id="_spring_security_remember_me" type="checkbox" name="_spring_security_remember_me" />

    <input type="submit" value="Login" />
</form>
<c:if test="${error}">
    <div style="color: red">
        <c:out value="Ошибка авторизации"/>
    </div>
</c:if>
</body>
</html>
