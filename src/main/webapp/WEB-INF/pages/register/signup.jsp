<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<fmt:requestEncoding value="utf-8" />

<jsp:include page="../template/top.jsp"/>

<div class="well well-lg">
    <form:form method="POST" commandName="registerUserDTO" action="/signup/register.do" cssClass="form-horizontal">
        <div class="form-group has-feedback">
            <label for="username" class="col-sm-2 control-label">
                <spring:message code="label.username"/>
                <span class="required">*</span>
            </label>
            <div class="col-xs-4">
                <form:input id="username" path="username" placeholder="Username" class="form-control"/>
                <form:errors path="username" cssClass="error" />
            </div>
        </div>
        <div class="form-group has-feedback">
            <label for="email" class="col-sm-2 control-label">
                <spring:message code="label.email"/>
                <span class="required">*</span>
            </label>
            <div class="col-xs-4">
                <form:input id="email" path="email" placeholder="Email" class="form-control"/>
                <form:errors path="email" cssClass="error" />
            </div>
        </div>
        <div class="form-group has-feedback">
            <label for="password" class="col-sm-2 control-label"><spring:message code="label.password"/>
                <span class="required">*</span>
            </label>
            <div class="col-xs-4">
                <form:input id="password" path="password" type="password" class="form-control"/>
                <form:errors path="password" cssClass="error"/>
            </div>
        </div>
        <div class="form-group has-feedback">
            <label for="firstName" class="col-sm-2 control-label">
                <spring:message code="label.firstname"/>
                <span class="required">*</span>
            </label>
            <div class="col-xs-4">
                <form:input id="firstName" path="firstName" type="text" name="firstName" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label for="lastName" class="col-sm-2 control-label">
                <spring:message code="label.lastname"/>
            </label>
            <div class="col-xs-4">
                <form:input id="lastName" path="lastName" type="text" name="lastName" class="form-control"/>
            </div>
        </div>
        <div class="form-group has-feedback">
            <label for="city" class="col-sm-2 control-label">
                <spring:message code="label.city"/>
                <span class="required">*</span>
            </label>
            <div class="col-xs-4">
                <form:input id="city" path="city" type="text" class="form-control"/>
                <form:errors path="city" cssClass="error"/>
                <div id="kladr_autocomplete"></div>
            </div>
        </div>
        <div class="submit col-sm-offset-2 col-sm-10">
            <input id="sign-up-btn" class="btn btn-default" type="submit" value="<spring:message code="label.signup"/>">
            <span id="loading-sign-up" class="loading"></span>
        </div>
        <div class="clearfix"></div>
    </form:form>
</div>

<jsp:include page="../template/bottom.jsp"/>