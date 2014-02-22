<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<jsp:useBean id="smartUserDetails" class="com.smartestgift.dao.model.SmartUserDetails" scope="request"/>
<jsp:useBean id="errors" type="java.lang.String[]" scope="request"/>

<jsp:include page="template/top.jsp"/>
<div class="container">
    <div class="alert alert-warning alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <c:forEach items="${errors}" var="error">
            <c:if test="${error eq 'email'}">
                <c:set var="errorEmail" value="true"/>
                <strong><spring:message code="label.error"/></strong> <spring:message code="label.signupSocialEmailError"/>
            </c:if>
            <c:if test="${error eq 'username'}">
                <c:set var="errorUsername" value="true"/>
                <strong><spring:message code="label.error"/></strong> <spring:message code="label.signupSocialUsernameError"/>
            </c:if>
        </c:forEach>
    </div>
    <div class="well well-lg">
        <div>
            <form class="form-horizontal" action="/signup/socialRegister" method="post">
                <div class="form-group <c:if test="${errorUsername}"> has-error </c:if>">
                    <label for="username" class="col-sm-2 control-label"><spring:message code="label.username"/><span class="required">*</span>
                    </label>
                    <div class="col-xs-4">
                        <input type="text" name="username" class="form-control" id="username" placeholder="<spring:message code="label.username"/>" value="<c:out value="${smartUserDetails.username}"/>">
                    </div>
                </div>
                <div class="form-group">
                    <label for="firstname" class="col-sm-2 control-label"><spring:message code="label.firstname"/><span class="required">*</span>
                    </label>
                    <div class="col-xs-4">
                        <input type="text" name="firstName" class="form-control" id="firstname" placeholder="<spring:message code="label.firstname"/>" value="<c:out value="${smartUserDetails.smartUser.firstName}"/>">
                    </div>
                </div>
                <div class="form-group">
                    <label for="lastname" class="col-sm-2 control-label"><spring:message code="label.lastname"/></label>
                    <div class="col-xs-4">
                        <input type="text" name="lastName" class="form-control" id="lastname" placeholder="<spring:message code="label.lastname"/>" value="<c:out value="${smartUserDetails.smartUser.lastName}"/>">
                    </div>
                </div>
                <div class="form-group <c:if test="${errorEmail}"> has-error</c:if>">
                    <label for="email" class="col-sm-2 control-label"><spring:message code="label.email"/><span class="required">*</span>
                    </label>
                    <div class="col-xs-4">
                        <input type="email" name="email" class="form-control" id="email" placeholder="<spring:message code="label.email"/>" value="<c:out value="${smartUserDetails.email}"/>">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default"><spring:message code="label.signup"/></button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="template/bottom.jsp"/>