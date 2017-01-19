<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<fmt:requestEncoding value="utf-8" />

<jsp:include page="partitial/top.jsp"/>

<div class="login">
    <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">

    </c:if>
    <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
        <div id="signup-error" class="alert alert-danger alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <fmt:message key="BadCredentialsException"/>
            <c:remove var = "SPRING_SECURITY_LAST_EXCEPTION" scope = "session" />
        </div>
    </c:if>
    <h2><spring:message code="label.login"/></h2>
    <form id="login-form" action="${pageContext.request.contextPath}/security_check" method="POST">
        <div class="row">
            <input id="email-input" type="text" name="username" placeholder="<fmt:message key="label.email"/>" class="form-control">
        </div>
        <div class="row lower-top-10">
            <input id="password-input" type="password" name="password" placeholder="<fmt:message key="label.password"/>" class="form-control">
        </div>
        <div class="row lower-top-10">
            <button class="btn btn-danger" type="submit"><fmt:message key="label.btn.login"/></button>
        </div>
        <hr>
        <p class="register-text text-center"><fmt:message key="label.text.not_registered"/> <a href="${pageContext.request.contextPath}/signup"><fmt:message key="label.link.signup"/></a></p>
    </form>
</div>

<jsp:include page="partitial/bottom.jsp"/>