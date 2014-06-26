<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<jsp:useBean id="facebookSmartUser" class="com.smartestgift.dao.model.SmartUser" scope="request"/>
<jsp:useBean id="username_error" type="java.lang.Boolean" scope="request"/>
<jsp:useBean id="email_error" type="java.lang.Boolean" scope="request"/>

<jsp:include page="template/top.jsp"/>

<jsp:include page="signup_form.jsp"/>

<jsp:include page="template/bottom.jsp"/>