<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<jsp:include page="template/top.jsp"/>

<div class="container">
    <div class="well well-lg">
        <div>
            <form class="form-horizontal login-form" action="j_spring_security_check" method="post">
                <div class="form-group">
                    <label for="j_username" class="col-sm-2 control-label"><spring:message code="label.emailorusername"/></label>
                    <div class="col-xs-4">
                        <input id="j_username" name="j_username" type="text" class="form-control" placeholder="<spring:message code="label.emailorusername"/>">
                    </div>
                </div>
                <div class="form-group">
                    <label for="j_password" class="col-sm-2 control-label"><spring:message code="label.password"/></label>
                    <div class="col-xs-4">
                        <input id="j_password" name="j_password" type="password" class="form-control" placeholder="<spring:message code="label.password"/>">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-xs-4">
                        <div class="checkbox">
                            <label>
                                <input id="_spring_security_remember_me" name="_spring_security_remember_me" type="checkbox"><spring:message code="label.remember"/></label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-xs-4">
                        <a href="/login/facebookLogin">
                            <img height="25px" src="/resources/ext/main/images/fb_login.png" alt="<spring:message code="label.facebooklogin"/>" />
                        </a>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <input type="submit" value="Login" class="btn btn-default"/>
                        <c:if test="${error}">
                            <div style="color: red">
                                <spring:message code="label.loginerror"/>
                            </div>
                        </c:if>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="template/bottom.jsp"/>