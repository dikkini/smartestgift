<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8"/>

<jsp:useBean id="allGiftCategories" type="java.util.List<com.smartestgift.dao.model.GiftCategory>" scope="request"/>
<jsp:useBean id="giftCategory" class="com.smartestgift.dao.model.GiftCategory" scope="request"/>

<c:set var="weekAgo" value="<%=new Date(new Date().getTime() - 60*60*24*1000*7)%>"/>

<jsp:include page="../template/top.jsp"/>

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
<c:if test="${giftCategory.id != null}">
    <div class="row top-buffer">
        <div class="col-xs-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <spring:message code="label.gifts"/> - <c:out value="${giftCategory.name}"/>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <c:forEach items="${giftCategory.gifts}" var="gift">
                            <div class="gift col-xs-3">
                                <c:forEach items="${gift.files}" var="giftFiles" end="0">
                                    <c:if test="${gift.addDate < weekAgo}">
                                        <span class="gift-new-label">
                                    </c:if>
                                        <img height="200" src="/file/get/${giftFiles.id}">
                                </c:forEach>
                                <p>${gift.name}</p>
                                <p class="ellipses small">${gift.description}</p>
                                <p class="gift-price small">${gift.cost}</p>
                                <button data-gift-uuid="<c:out value="${gift.uuid}"/>" class="btn btn-default want-gift-btn"><spring:message code="label.wanttogift"/></button>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>

<script type="text/javascript">
    $(document).ready(function(){
        $(".want-gift-btn").click(function() {
            $.ajax({
                type: "post",
                url: "/gifts/wantgift",
                cache: false,
                data: "giftuuid=" + $(this).data("gift-uuid"),
                success: function (response) {
                    if (response.success) {
                        window.location = $.updateQueryStringParameter(window.location.href, "successes", "user_add_gift");
                    }
                },
                error: function (response) {
                    window.location = $.updateQueryStringParameter(window.location.href, "errors", "user_add_gift_error");
                }
            });
        });
    });
</script>

<jsp:include page="../template/bottom.jsp"/>