<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8"/>

<jsp:useBean id="allGiftCategories" type="java.util.List<com.smartestgift.dao.model.GiftCategory>" scope="request"/>
<jsp:useBean id="giftCategory" class="com.smartestgift.dao.model.GiftCategory" scope="request"/>

<jsp:include page="../template/top.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-xs-9">
            <input type="text" class="form-control" placeholder="<spring:message code="label.searchGiftPlaceHolder"/>">
        </div>
        <button type="submit" class="btn btn-default"><spring:message code="label.find"/></button>
        <button type="submit" class="btn btn-default"><spring:message code="label.random_gift"/></button>
    </div>
    <div class="row top-buffer">
        <div class="col-xs-12">
            <div class="panel panel-primary">
                <div class="panel-heading"><spring:message code="label.gift_categories"/></div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-xs-12">
                            <c:forEach items="${allGiftCategories}" var="giftCategory">
                                    <span class="gift-category">
                                        <a href="/gifts/categories/${giftCategory.id}"><img height="75" src="<c:out value="/file/get/${giftCategory.file.id}"/>"></a>
                                        <a href="/gifts/categories/${giftCategory.id}"><c:out value="${giftCategory.name}"/></a>
                                    </span>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${giftCategory != null}">
        <div class="row top-buffer">
            <div class="col-xs-12">
                <div class="panel panel-primary">
                    <div class="panel-heading"><spring:message code="label.gifts"/> - <c:out value="${giftCategory.name}"/> </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-xs-12">
                                <c:forEach items="${giftCategory.gifts}" var="gift">
                                        <span class="gift">
                                            <p>${gift.name}</p>
                                            <c:forEach items="${gift.files}" var="giftFiles" end="0">
                                                <img height="100" src="/file/get/${giftFiles.id}">
                                            </c:forEach>
                                            <p class="ellipses">${gift.description}</p>
                                            <p>${gift.cost}</p>
                                            <button class="btn btn-default"><spring:message code="label.wanttogift"/></button>
                                        </span>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</div>

<jsp:include page="../template/bottom.jsp"/>