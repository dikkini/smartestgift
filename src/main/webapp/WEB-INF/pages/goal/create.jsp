<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:useBean id="allCurrencies" type="java.util.Collection<com.paymybill.dao.model.Currency>" scope="request"/>

<fmt:requestEncoding value="utf-8" />

<jsp:include page="../partitial/top.jsp"/>

<script src="${pageContext.request.contextPath}/assets/ext/jstz.min.js" type="text/javascript" charset="utf-8"></script>

<script src="${pageContext.request.contextPath}/assets/main/js/goal/create.min.js" type="text/javascript" charset="utf-8"></script>

<form id="goal-create-form"
      modelAttribute="<c:out default="None" escapeXml="true" value="${empty param.targetUuid ? 'goalNoTargetDTO' : 'goalDTO'}"/>"
      action="${pageContext.request.contextPath}<c:out default="None" escapeXml="true" value="${empty param.targetUuid ? '/goal/create' : '/goal/createWithTarget'}"/>"
      method="POST">
    <input id="goal-name-input" type="text" name="name" placeholder="<fmt:message key="label.goal.name"/>" maxlength="255" required value="name"/>
    <textarea id="goal-descr-input" name="description" placeholder="<fmt:message key="label.goal.description"/>" maxlength="4000" required>asd</textarea>
    <input id="goal-startsum-input" type="number" name="startSum" placeholder="<fmt:message key="label.goal.startsum"/>" min="1" value="123" required/>
    <p><fmt:message key="label.goal.startdate"/><input id="goal-start-date" type="text" name="startDate" class="datepicker"></p>
    <p><fmt:message key="label.goal.enddate"/><input id="goal-end-date" type="text" name="endDate" class="datepicker"></p>
    <input id="client-timezone" type="hidden" name="timeZone" />
    <input id="goal-endsum-input" type="number" name="endSum" onkeypress='return event.charCode >= 48 && event.charCode <= 57' placeholder="<fmt:message key="label.goal.endsum"/>" min="1" value="123"/>
    <input id="goal-price-input" type="number" name="price" onkeypress='return event.charCode >= 48 && event.charCode <= 57' placeholder="<fmt:message key="label.goal.price"/>" min="1" required value="123"/>
    <select name="currencyId">
        <c:forEach items="${allCurrencies}" var="currency">
            <option value="${currency.id}">${currency.sign}</option>
        </c:forEach>
    </select>
    <c:if test="${not empty param.targetUuid}">
        <input id="goal-target-input" name="targetUuid" type="hidden" value="<c:out default="None" escapeXml="true" value='${param.targetUuid}'/>"/>
    </c:if>
    <button class="btn btn-danger" type="submit"><fmt:message key="label.goal.create"/></button>
</form>

<jsp:include page="../partitial/bottom.jsp"/>