<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<sec:authentication var="user" property="principal" />

<jsp:useBean id="user" class="com.smartestgift.dao.model.SmartUserDetails" scope="request"/>


<!doctype html>

<html lang="en">

<head>
    <title><spring:message code="label.title"/></title>
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/ext/jquery/datepicker/css/pickmeup.min.css">
    <link rel="stylesheet" href="/resources/main/css/style.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script type="text/javascript" src="https://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/ext/jquery/datepicker/jquery.pickmeup.min.js"></script>
    <script type="text/javascript" src="/resources/main/js/loading.js"></script>
    <script type="text/javascript" src="/resources/main/js/utils.js"></script>
    <script type="text/javascript" src="/resources/main/js/check_browse_close.js"></script>
</head>

<body>
<div class="header">
    <header>
        <div class="row">
            <div class="col-md-11">
                <img src="/resources/main/images/logo.png">
            </div>
            <div class="col-md-1">
                <span style="float: right">
                    <a href="?lang=en">en</a>
                    |
                    <a href="?lang=ru">ru</a>
                </span>
            </div>
        </div>
        <div class="navbar navbar-inverse">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
                    </button>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <sec:authorize access="isAuthenticated()">
                            <li>
                                <a href="/profile"><spring:message code="label.profile"/></a>
                            </li>
                            <li>
                                <a href="<c:url value="/gifts/my"/>"><spring:message code="label.mygifts"/></a>
                            </li>
                            <li>
                                <a href="<c:url value="/messages"/>"><spring:message code="label.messages"/> <span id="countUnreadMessages" class="badge"></span></a>
                            </li>
                        </sec:authorize>
                        <sec:authorize access="isAnonymous()">
                            <li>
                                <a href="/"><spring:message code="label.home"/></a>
                            </li>
                        </sec:authorize>
                    </ul>
                    <ul id="login_signup_logged" class="nav navbar-nav navbar-right">
                        <sec:authorize access="isAuthenticated()">
                            <li>
                                <p class="navbar-text navbar-right"><spring:message code="label.signed"/>
                                    <a href="/profile" class="navbar-link">
                                        <c:out value="${user.smartUser.firstName}"/>
                                        <c:out value="${user.smartUser.lastName}"/>
                                    </a>
                                </p>
                            </li>
                            <li>
                                <a href="<c:url value="/profile/settings"/>"><spring:message code="label.settings"/></a>
                            </li>
                            <li>
                                <a href="<c:url value="/logout"/>"><spring:message code="label.logout"/></a>
                            </li>
                        </sec:authorize>
                        <sec:authorize access="isAnonymous()">
                            <li>
                                <a href="<c:url value="/login"/>"><spring:message code="label.login"/></a>
                            </li>
                            <li>
                                <a href="<c:url value="/signup"/>"><p class="navbar-right"><spring:message code="label.signup"/></p></a>
                            </li>
                        </sec:authorize>
                    </ul>
                </div>
            </div>
        </div>
    </header>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        <sec:authorize access="isAuthenticated()">
            (function poll(){
                $.ajax({
                    type: "post",
                    url: "/messages/getCountUserUnreadMessages",
                    cache: false,
                    success: function (response) {
                        $("#countUnreadMessages").text(response);
                    }, dataType: "json", complete: poll, timeout: 3000 });
            })();
        </sec:authorize>
    });
</script>

<div class="container">
