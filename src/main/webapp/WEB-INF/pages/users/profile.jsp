<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<jsp:useBean id="smartUser" class="com.smartestgift.dao.model.SmartUser" scope="request"/>

<jsp:include page="../template/top.jsp"/>

<div class="row">
    <div class="col-xs-3">
        <div class="panel">
        <c:choose>
            <c:when test="${smartUser.file.id == null}">
                <img src=/resources/main/images/no_photo.jpg>
            </c:when>
            <c:otherwise>
                <img src="/file/get/${smartUser.file.id}">
            </c:otherwise>
        </c:choose>
        </div>

        <div class="panel panel-primary">
            <div class="panel-heading"><spring:message code="label.gift"/></div>
            <div class="panel-body">
                <ul class="nav nav-pills nav-stacked">
                    <li><a href="/catalog"><spring:message code="label.find_gift"/></a></li>
                </ul>
            </div>
        </div>

        <div class="panel panel-primary">
            <div class="panel-heading"><spring:message code="label.social"/></div>
            <div class="panel-body">
                <ul class="nav nav-pills nav-stacked">
                    <li><a href="#"><spring:message code="label.helpers"/></a></li>
                    <li><a href="#"><spring:message code="label.messages"/></a></li>
                </ul>
            </div>
        </div>

    </div>
    <div class="col-xs-5">
        <p>
            <strong> <c:out value="${smartUser.lastName} ${smartUser.firstName} ${smartUser.middleName}"/> </strong>
            <a href="/profile/settings"><img id="edit-profile" height="25px" src="http://icons.iconarchive.com/icons/visualpharm/must-have/256/Edit-icon.png"></a>
        </p>
        <p><strong><spring:message code="label.birthdate"/>:</strong> <fmt:formatDate value="${smartUser.birthDate}" pattern="dd.MM.yyyy" /></p>
        <p><strong><spring:message code="label.mobilephone"/>:</strong> <c:out value="${smartUser.cellPhone}"/></p>
        <p><strong><spring:message code="label.address"/>:</strong> <c:out value="${smartUser.address}"/></p>
    </div>
    <div class="col-xs-4">
        <h3><spring:message code="label.wishlist"/></h3>
        <ul class="list-group">
            <c:forEach items="${smartUser.smartUserGifts}" var="smartUserGift" end="3">
                <li class="list-group-item">
                    <blockquote>
                        <p>
                            <a href="/gifts/gift/<c:out value="${smartUserGift.gift.uuid}"/>"><c:out value="${smartUserGift.gift.name}"/></a>
                            - <c:out value="${smartUserGift.gift.cost}"/>
                        </p>
                        <p class="ellipses"><c:out value="${smartUserGift.gift.description}"/></p>
                        <small><spring:message code="label.collected_money_for_gift"/>: <c:out value="${smartUserGift.moneyCollect}"/> </small>
                    </blockquote>
                </li>
            </c:forEach>
            <c:if test="${fn:length(smartUser.smartUserGifts) > 3}">
                <a style="float: right;" href="/gifts/mygifts"><spring:message code="label.and_more"/> <c:out value="${fn:length(smartUser.smartUserGifts) - 3}"/></a>
            </c:if>
        </ul>
    </div>
</div>

<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function(){
    });
</script>