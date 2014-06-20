<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<jsp:useBean id="facebookSmartUser" class="com.smartestgift.dao.model.SmartUser" scope="request"/>
<jsp:useBean id="errors" type="java.lang.String[]" scope="request"/>

<jsp:include page="template/top.jsp"/>

<div class="well well-lg">
    <div>
        <form class="form-horizontal">
            <div class="form-group">
                <label for="username" class="col-sm-2 control-label"><spring:message code="label.username"/><span class="required">*</span>
                </label>
                <div class="col-xs-4">
                    <input id="username" data-social="<c:out value="${facebookSmartUser.socialId}"/>" type="text" name="username" class="form-control" placeholder="<spring:message code="label.username"/>" value="<c:out value="${facebookSmartUser.username}"/>">
                </div>
                <img id="username-status" height="25" src="" hidden>
                <span id="loading-username" class="loading"></span>
            </div>
            <div class="form-group">
                <label for="firstname" class="col-sm-2 control-label"><spring:message code="label.firstname"/><span class="required">*</span>
                </label>
                <div class="col-xs-4">
                    <input type="text" name="firstName" class="form-control" id="firstname" placeholder="<spring:message code="label.firstname"/>" value="<c:out value="${facebookSmartUser.firstName}"/>">
                </div>
            </div>
            <div class="form-group">
                <label for="lastname" class="col-sm-2 control-label"><spring:message code="label.lastname"/></label>
                <div class="col-xs-4">
                    <input type="text" name="lastName" class="form-control" id="lastname" placeholder="<spring:message code="label.lastname"/>" value="<c:out value="${facebookSmartUser.lastName}"/>">
                </div>
            </div>
            <div class="form-group">
                <label for="email" class="col-sm-2 control-label"><spring:message code="label.email"/><span class="required">*</span>
                </label>
                <div class="col-xs-4">
                    <input type="email" name="email" class="form-control" id="email" placeholder="<spring:message code="label.email"/>" value="<c:out value="${facebookSmartUser.email}"/>">
                </div>
                <img id="email-status" height="25" src="" hidden>
                <span id="loading-email" class="loading"></span>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button id="sign-up-facebook-user-btn" class="btn btn-default"><spring:message code="label.signup"/></button>
                    <span id="loading-sign-up" class="loading"></span>
                </div>
            </div>
        </form>
    </div>
</div>

<jsp:include page="template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function () {
        var SOCIAL_REGISTER = true;
    });
</script>
<script type="text/javascript" src="/assets/main/js/signup.js"></script>