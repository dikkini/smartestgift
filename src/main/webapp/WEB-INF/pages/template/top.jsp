<%--
  Created by IntelliJ IDEA.
  User: akarapetov
  Date: 06.02.14
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<fmt:requestEncoding value="utf-8" />

<sec:authentication var="user" property="principal" />

<!doctype html>

<html lang="en">

<head>
    <title>Smart Gift - help your friend take an advantage!</title>
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="/resources/ext/main/css/style.css">
    <link rel="stylesheet" href="/resources/ext/jquery/datepicker/css/pickmeup.min.css">
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script type="text/javascript" src="https://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/ext/jquery/datepicker/jquery.pickmeup.min.js"></script>
</head>

<body>
<div class="header">
    <header>
        <div class="row">
            <div class="col-md-2">
                <img src="/resources/ext/main/images/logo.png">
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
                                <a href="/profile">Profile</a>
                            </li>
                            <li>
                                <a href="<c:url value="/mygifts"/>">My Gifts</a>
                            </li>
                        </sec:authorize>
                        <sec:authorize access="isAnonymous()">
                            <li>
                                <a href="/">Home</a>
                            </li>
                        </sec:authorize>
                    </ul>
                    <ul id="login_signup_logged" class="nav navbar-nav navbar-right">
                        <sec:authorize access="isAuthenticated()">
                            <li>
                                <a href="/profile/"><c:out value="${user.smartUser.firstName}"/> </a>
                            </li>
                            <li>
                                <a href="<c:url value="/profile/settings"/>">Settings</a>
                            </li>
                            <li>
                                <a href="<c:url value="/logout"/>">Logout</a>
                            </li>
                        </sec:authorize>
                        <sec:authorize access="isAnonymous()">
                            <li>
                                <a href="<c:url value="/login"/>">Login</a>
                            </li>
                            <li>
                                <a href="<c:url value="/signup"/>"><p class="navbar-right">SignUp</p></a>
                            </li>
                        </sec:authorize>
                    </ul>
                </div>
            </div>
        </div>
    </header>
</div>
