<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<fmt:requestEncoding value="utf-8" />

<jsp:useBean id="registerUser" scope="request" class="com.paymybill.controller.model.UserDTO"/>
<jsp:useBean id="SIGNUP_ERROR" scope="request" class="java.lang.String"/>

<jsp:include page="partitial/top.jsp"/>

<div class="login">
    <h2><spring:message code="label.signup"/></h2>
    <form id="signup-form" modelAttribute="registerUser" action="${pageContext.request.contextPath}/signup" method="POST">
        <c:if test="${not empty SIGNUP_ERROR}">
            <div id="signup-error" class="alert alert-danger alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <c:out value="${SIGNUP_ERROR}"/>
                <c:remove var = "SIGNUP_ERROR" scope = "session" />
            </div>
        </c:if>
        <div class="row lower-top-10">
            <input id="username-input" type="text" name="username" placeholder="<fmt:message key="label.username"/>" class="form-control" value="<c:out value="${registerUser.username}"/>" minlength="3" maxlength="200" required>
        </div>
        <div class="row lower-top-10">
            <input id="firstname-input" type="text" name="firstName" placeholder="<fmt:message key="label.firstName"/>" class="form-control" value="<c:out value="${registerUser.firstName}"/>" minlength="3" maxlength="200" required>
        </div>
        <div class="row lower-top-10">
            <input id="email-input" type="email" name="email" placeholder="<fmt:message key="label.email"/>" class="form-control" value="<c:out value="${registerUser.email}"/>" required>
        </div>
        <div class="row lower-top-10">
            <input id="password-input" type="password" name="password" placeholder="<fmt:message key="label.password"/>" class="form-control" value="<c:out value="${registerUser.password}"/>" minlength="6" maxlength="200" required>
        </div>
        <div class="row lower-top-10">
            <input id="password_repeat-input" type="password" name="repeatPassword" placeholder="<fmt:message key="label.password_repeat"/>" class="form-control" value="<c:out value="${registerUser.repeatPassword}"/>" equalTo="#password-input" required>
        </div>
        <div class="row lower-top-10">
            <button class="btn btn-danger" type="submit"><fmt:message key="label.btn.register"/></button>
        </div>
        <hr>
    </form>
    <p class="register-text text-center"><fmt:message key="label.text.registered"/> <a href="${pageContext.request.contextPath}/login"><fmt:message key="label.link.login"/></a></p>
</div>

<jsp:include page="partitial/bottom.jsp"/>