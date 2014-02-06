<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<fmt:requestEncoding value="utf-8" />

<jsp:include page="template/top.jsp"/>

<div class="container">
    <div class="well well-lg">
        <div>
            <form class="form-horizontal login-form" action="j_spring_security_check" method="post">
                <div class="form-group">
                    <label for="j_username" class="col-sm-2 control-label">Email</label>
                    <div class="col-xs-4">
                        <input id="j_username" name="j_username" type="email" class="form-control" placeholder="Email">
                    </div>
                </div>
                <div class="form-group">
                    <label for="j_password" class="col-sm-2 control-label">Password</label>
                    <div class="col-xs-4">
                        <input id="j_password" name="j_password" type="password" class="form-control" placeholder="Password">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-xs-4">
                        <div class="checkbox">
                            <label>
                                <input id="_spring_security_remember_me" name="_spring_security_remember_me" type="checkbox">Remember me</label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <input type="submit" value="Login" class="btn btn-default"/>
                        <c:if test="${error}">
                            <div style="color: red">
                                <c:out value="Ошибка авторизации"/>
                            </div>
                        </c:if>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="template/bottom.jsp"/>