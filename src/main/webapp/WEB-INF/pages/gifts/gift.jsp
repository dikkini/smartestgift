<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sprint" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="utf-8" />

<jsp:useBean id="gift" class="com.smartestgift.dao.model.Gift" scope="request"/>

<jsp:include page="../template/top.jsp"/>

<c:forEach items="${gift.files}" var="giftFile" end="0">
    <c:set var="mainGiftFile" value="${giftFile}"/>
</c:forEach>

<div class="row">
    <div class="col-xs-3">
        <div class="panel">
            <c:choose>
                <c:when test="${mainGiftFile.id == null}">
                    <img height="250" src=/resources/main/images/no_photo.jpg>
                </c:when>
                <c:otherwise>
                    <img height="250" src="/file/get/${mainGiftFile.id}">
                </c:otherwise>
            </c:choose>
        </div>

        <div class="panel panel-primary">
            <div class="panel-heading"><spring:message code="label.gift"/></div>
            <div class="panel-body">
                <ul class="nav nav-pills nav-stacked">
                    <li><a id="want-gift-link" data-gift-uuid="${gift.uuid}" href="#"><spring:message code="label.wanttogift"/></a></li>
                </ul>
            </div>
        </div>

        <div class="panel panel-primary">
            <div class="panel-heading"><spring:message code="label.gift_page_shop_menu_header"/></div>
            <div class="panel-body">
                <ul class="nav nav-pills nav-stacked">
                </ul>
            </div>
        </div>
    </div>

    <div class="col-xs-5">
        <p><strong><sprint:message key="label.gift_page_description"/></strong></p>
        <p><c:out value="${gift.description}"/> </p>
    </div>

    <div class="col-xs-4">
        <h3><spring:message code="label.gift_page_avaiable_external_shops"/></h3>
        <ul class="list-group">
            <li class="list-group-item">
                <blockquote>
                    <p><a href="#">Amazon.com</a></p>
                </blockquote>
                25 000
            </li>
        </ul>
    </div>
</div>

<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function(){
        $("#want-gift-link").click(function() {
            $.ajax({
                type: "post",
                url: "/gifts/wantGift",
                cache: false,
                data: "giftuuid=" + $(this).data("gift-uuid"),
                success: function (response) {
                    window.location = $.updateNotifyBlockRequest(window.location.href, response.successes, response.errors, response.information);
                },
                error: function (response) {
                    window.location = "500";
                }
            });
        });
    });
</script>