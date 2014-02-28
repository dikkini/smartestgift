<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:forEach items="${param.errors}" var="error">
    <div class="alert alert-danger alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <p class="error"><spring:message key="label.${error}"/></p>
    </div>
</c:forEach>

<c:forEach items="${param.successes}" var="success">
    <div class="alert alert-success alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <p class="erros"><spring:message key="label.${success}"/></p>
    </div>
</c:forEach>

<c:forEach items="${param.warnings}" var="warning">
    <div class="alert alert-warning alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <p class="warning"><spring:message key="label.${warning}"/></p>
    </div>
</c:forEach>

<c:forEach items="${param.information}" var="info">
    <div class="alert alert-info alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <p class="erros"><spring:message key="label.${info}"/></p>
    </div>
</c:forEach>