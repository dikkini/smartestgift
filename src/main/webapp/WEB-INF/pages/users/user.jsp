<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<jsp:useBean id="alienSmartUser" class="com.smartestgift.dao.model.SmartUser" scope="request"/>

<jsp:include page="../template/top.jsp"/>

<div class="row">
    <div class="col-xs-3">
        <div class="panel">
            <img height="200" src="/file/get/${smartUser.file.id}">
        </div>
<c:if test="${alienSmartUser.profileVisible}">
        <div class="panel panel-primary">
            <div class="panel-heading"><spring:message code="label.social"/></div>
            <div class="panel-body">
                <ul class="nav nav-pills nav-stacked">
                    <li><a href="#"><spring:message code="label.his_helper"/></a></li>
                    <li><a href="#"><spring:message code="label.write_message_to_user"/></a></li>
                </ul>
            </div>
        </div>
</c:if>
    </div>

    <div class="col-xs-5">
        <p>
            <strong> <c:out value="${alienSmartUser.lastName} ${alienSmartUser.firstName} ${alienSmartUser.middleName}"/> </strong>
            <a href="/profile/settings"><img id="edit-profile" height="25px" src="http://icons.iconarchive.com/icons/visualpharm/must-have/256/Edit-icon.png"></a>
        </p>
        <c:if test="${alienSmartUser.profileVisible}">
            <p><strong><spring:message code="label.birthdate"/>:</strong> <fmt:formatDate value="${alienSmartUser.birthDate}" pattern="dd.MM.yyyy" /></p>

            <c:if test="${alienSmartUser.cellPhoneVisible}">
                <p><strong><spring:message code="label.mobilephone"/>:</strong> <c:out value="${alienSmartUser.cellPhone}"/></p>
            </c:if>
            <c:if test="${alienSmartUser.addressVisible}">
                <p><strong><spring:message code="label.address"/>:</strong> <c:out value="${alienSmartUser.address}"/></p>
            </c:if>
        </c:if>
    </div>
<c:if test="${alienSmartUser.profileVisible}">
    <div class="col-xs-4">
        <h3><spring:message code="label.wishlist"/></h3>
        <ul class="list-group">
            <c:forEach items="${alienSmartUser.smartUserGifts}" var="smartUserGift" end="3">
                <li class="list-group-item">
                    <blockquote>
                        <p>
                            <a href="/gifts/gift/<c:out value="${smartUserGift.gift.uuid}"/>"><c:out value="${smartUserGift.gift.name}"/></a>
                        </p>
                        <p class="ellipses"><c:out value="${smartUserGift.gift.description}"/></p>
                        <small><spring:message code="label.collected_money_for_gift"/>: <c:out value="${smartUserGift.moneyCollect}"/> </small>
                    </blockquote>
                    <button data-gift-uuid="<c:out value="${smartUserGift.gift.uuid}"/>" class="btn btn-default un-want-gift-btn"><spring:message code="label.un_want_gift_button"/></button>
                </li>
            </c:forEach>
            <c:if test="${fn:length(alienSmartUser.smartUserGifts) > 3}">
                <a style="float: right;" href="/gifts/mygifts"><spring:message code="label.all_gifts_in_wishlist"/> <c:out value="${fn:length(smartUser.smartUserGifts)}"/></a>
            </c:if>
        </ul>
    </div>
</div>
</c:if>

<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function(){

    });
</script>