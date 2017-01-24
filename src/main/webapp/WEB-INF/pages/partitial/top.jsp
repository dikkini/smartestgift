<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<fmt:requestEncoding value="utf-8" />

<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="msapplication-TileColor" content="#ffffff">
    <meta name="msapplication-TileImage" content="/ms-icon-144x144.png">
    <meta name="theme-color" content="#ffffff">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/ext/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/main/css/main.css">

    <link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/ext/jquery/jquery.datetimepicker.full.min.css">


    <script src="${pageContext.request.contextPath}/assets/ext/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="${pageContext.request.contextPath}/assets/ext/jquery/jquery.form.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="${pageContext.request.contextPath}/assets/ext/jquery/jquery.datetimepicker.full.js" type="text/javascript" charset="utf-8"></script>

    <script src="${pageContext.request.contextPath}/assets/ext/jquery/jquery.validate.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="${pageContext.request.contextPath}/assets/ext/jquery/jquery-validate.bootstrap-tooltip.min.js" type="text/javascript" charset="utf-8"></script>

    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <script src="${pageContext.request.contextPath}/assets/ext/bootstrap/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="${pageContext.request.contextPath}/assets/ext/moment/moment.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/assets/ext/underscore/underscore.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/assets/ext/modernizr.min.js" type="text/javascript" charset="utf-8"></script>

    <title><fmt:message key="label.title"/></title>
    <script type="text/javascript">
        var validationMessages = [];
        validationMessages['validate.required'] = "<spring:message code='validate.required' javaScriptEscape='true' />";
        validationMessages['validate.equalTo'] = "<spring:message code='validate.equalTo' javaScriptEscape='true' />";
        validationMessages['validate.email'] = "<spring:message code='validate.email' javaScriptEscape='true' />";
        validationMessages['validate.maxlength'] = "<spring:message code='validate.maxSize' javaScriptEscape='true' />";
        validationMessages['validate.minlength'] = "<spring:message code='validate.minSize' javaScriptEscape='true' />";

        var jsLocaleStrings = [];
        jsLocaleStrings['datetimeformat'] = "<spring:message code='label.frontend.datetimeformat' javaScriptEscape='true' />";
        jsLocaleStrings['timeformat'] = "<spring:message code='label.frontend.timeformat' javaScriptEscape='true' />";

    </script>
    <script type="text/javascript">
        var userUuid = "${pageContext['request'].userPrincipal.principal.uuid}";
        var language = "${cookie.localeCookie.value}";
        language = "${cookie.localeCookie.value}";
        var paramLocale = "${param.locale}";
        if (paramLocale != "") {
            language = paramLocale;
        }
        if (language == "") {
            language = "ru";
        }
    </script>
</head>
<body>

<nav>
    <ul>
        <li class="white"><a data-page="${pageContext.request.contextPath}/main" href="${pageContext.request.contextPath}/"><spring:message code="label.nav.main"/></a></li>
        <sec:authorize access="isAnonymous()">
            <li class="white"><a data-page="${pageContext.request.contextPath}/login" href="${pageContext.request.contextPath}/login"><spring:message code="label.nav.login"/></a></li>
        <li class="white"><a data-page="${pageContext.request.contextPath}/signup" href="${pageContext.request.contextPath}/signup"><spring:message code="label.nav.signup"/></a></li>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
        <li class="white"><a href="${pageContext.request.contextPath}/logout"><spring:message code="label.nav.logout"/></a></li>
        </sec:authorize>
        <li class="white right"><a href="?locale=en">English</a></li>
        <li class="white right"><a href="?locale=ru">Русский</a></li>
    </ul>
</nav>
