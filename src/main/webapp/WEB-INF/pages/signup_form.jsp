<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<div class="well well-lg">
    <div>
        <form class="form-horizontal">
            <div class="form-group has-feedback">
                <label for="username" class="col-sm-2 control-label">
                    <spring:message code="label.username"/>
                    <span class="required">*</span>
                </label>
                <div class="col-xs-4">
                    <input id="username" data-social="<c:out value="${facebookSmartUser.socialId}"/>"
                           type="text" name="username" class="form-control"
                           placeholder="<spring:message code="label.username"/>"
                           value="<c:out value="${facebookSmartUser.username}"/>">
                    <span id="username-ok-icon" class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
                    <span id="username-not-ok-icon" class="glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
                </div>
                <span id="username-input-error" class="required" style="display: none;">
                    <spring:message code="label.username_input_length_error"/>
                </span>
                <span id="username-busy-error" class="required" style="display: none;">
                    <spring:message code="label.username_busy_error"/>
                </span>
                <span id="loading-username" class="loading"></span>
            </div>
            <div class="form-group has-feedback">
                <label for="firstname" class="col-sm-2 control-label">
                    <spring:message code="label.firstname"/>
                    <span class="required">*</span>
                </label>
                <div class="col-xs-4">
                    <input type="text" name="firstName" class="form-control" id="firstname"
                           placeholder="<spring:message code="label.firstname"/>"
                           value="<c:out value="${facebookSmartUser.firstName}"/>">
                    <span id="firstName-ok-icon" class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
                    <span id="firstName-not-ok-icon" class="glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
                </div>
            </div>
            <div class="form-group">
                <label for="lastname" class="col-sm-2 control-label">
                    <spring:message code="label.lastname"/>
                </label>
                <div class="col-xs-4">
                    <input type="text" name="lastName" class="form-control" id="lastname"
                           placeholder="<spring:message code="label.lastname"/>"
                           value="<c:out value="${facebookSmartUser.lastName}"/>">
                </div>
            </div>
            <div class="form-group has-feedback">
                <label for="city" class="col-sm-2 control-label">
                    <spring:message code="label.city"/>
                    <span class="required">*</span>
                </label>
                <div class="col-xs-4">
                    <input type="text" name="city" class="form-control" id="city"
                           placeholder="<spring:message code="label.city"/>">
                    <div id="kladr_autocomplete"></div>
                    <span id="city-ok-icon" class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
                    <span id="city-not-ok-icon" class="glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
                </div>
            </div>
            <div class="form-group has-feedback">
                <label for="email" class="col-sm-2 control-label">
                    <spring:message code="label.email"/>
                    <span class="required">*</span>
                </label>
                <div class="col-xs-4">
                    <input type="email" name="email" class="form-control" id="email"
                           placeholder="<spring:message code="label.email"/>"
                           value="<c:out value="${facebookSmartUser.email}"/>">
                    <span id="email-ok-icon" class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
                    <span id="email-not-ok-icon" class="glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
                </div>
                <span id="email-input-error" class="required" style="display: none;"><spring:message code="label.email_input_error"/></span>
                <span id="email-busy-error" class="required" style="display: none;"><spring:message code="label.email_busy_error"/></span>
                <span id="loading-email" class="loading"></span>
            </div>
            <c:if test="${!requestScope.social}">
                <div class="form-group has-feedback">
                    <label for="password" class="col-sm-2 control-label"><spring:message code="label.password"/>
                        <span class="required">*</span>
                    </label>
                    <div class="col-xs-4">
                        <input type="password" name="password" class="form-control" id="password"
                               placeholder="<spring:message code="label.password"/>">
                        <span id="password-ok-icon" class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
                        <span id="password-not-ok-icon" class="glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
                    </div>
                </div>
            </c:if>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button id="sign-up-btn" class="btn btn-default">
                        <spring:message code="label.signup"/>
                    </button>
                    <span id="loading-sign-up" class="loading"></span>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    social = "${requestScope.social}";
</script>
<script type="text/javascript" src="/assets/main/js/signup.js"></script>