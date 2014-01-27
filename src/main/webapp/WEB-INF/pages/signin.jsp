<%--
  Created by IntelliJ IDEA.
  User: akarapetov
  Date: 27.01.14
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign In</title>
</head>
<body>
<h1>Spring Security - Sign In</h1>

<div style="color: red">${message}</div>

<form class="login-form" action="j_spring_security_check" method="post">
    <label for="j_username">Username: </label>
    <input id="j_username" name="j_username" size="20" maxlength="50" type="text" />

    <label for="j_password">Password: </label>
    <input id="j_password" name="j_password" size="20" maxlength="50" type="password" />

    <input type="submit" value="Login" />
</form>
</body>
</html>
