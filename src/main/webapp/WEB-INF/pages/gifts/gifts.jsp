<%@ page import="java.util.Date" %>
<%@ page import="com.smartestgift.dao.model.Gift" %>
<%@ page import="com.smartestgift.controller.model.Page" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8"/>

<jsp:useBean id="allGiftCategories" type="java.util.List<com.smartestgift.dao.model.GiftCategory>" scope="request"/>
<jsp:useBean id="giftCategory" class="com.smartestgift.dao.model.GiftCategory" scope="request"/>
<jsp:useBean id="pageGift" class="com.smartestgift.controller.model.Page" scope="session">
    <jsp:setProperty name="session" property="session"/>
    <jsp:setProperty name="page" property="page"/>
    <jsp:setProperty name="pageSize" property="pageSize"/>
    <jsp:setProperty name="cls" property="cls"/>
</jsp:useBean>
<jsp:useBean id="smartUser" class="com.smartestgift.dao.model.SmartUser" scope="request"/>
<jsp:useBean id="gift" class="com.smartestgift.dao.model.Gift" scope="request"/>

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
                                    <a href="#${giftCategory.code}">
                                        <img height="75" src="<c:out value="/file/get/${giftCategory.file.id}"/>">
                                    </a>
                                    <a href="#${giftCategory.code}">
                                        <c:out value="${giftCategory.name}"/>
                                    </a>
                                </span>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="test"></div>
<div class="row top-buffer" style="display: none;">
    <div class="col-xs-12">
        <div class="panel panel-primary">
            <div id="category-name" class="panel-heading"></div>
            <div class="panel-body">
                <div class="row">
<%--                    <c:forEach items="${giftCategory.gifts}" var="gift">
                        <div class="gift col-xs-3">
                            <c:forEach items="${gift.files}" var="giftFiles" end="0">
                                <c:if test="${gift.addDate < weekAgo}">
                                    <span class="gift-new-label">
                                </c:if>
                                    <a href="/${giftCategory.code}/${gift.uuid}">
                                        <img height="200" src="/file/get/${giftFiles.id}">
                                    </a>
                            </c:forEach>
                            <p>${gift.name}</p>
                            <p class="ellipses small">${gift.description}</p>
                            <c:forEach items="${smartUser.smartUserGifts}" var="smartUserGift">
                                <c:if test="${smartUserGift.gift.uuid == gift.uuid}">
                                    <c:set var="giftExist" value="true"/>
                                </c:if>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${giftExist == true}">
                                    <spring:message code="label.gift_already_in_wishlist"/>
                                    <c:set var="giftExist" value="false"/>
                                </c:when>
                                <c:otherwise>
                                    <button data-gift-uuid="<c:out value="${gift.uuid}"/>"
                                            class="btn btn-default want-gift-btn">
                                        <spring:message code="label.wanttogift"/>
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>--%>
                </div>
            </div>
            <ul class="pager">
                <li><a id="pager-prev-btn" class="<c:if test="${!true}">disable</c:if>" href="#">Previous</a></li>
                <li><a id="pager-next-btn" class="<c:if test="${!true}">disable</c:if>" href="#">Next</a></li>
            </ul>
        </div>
    </div>
</div>

<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function(){
        $(".want-gift-btn").click(function() {
            $.ajax({
                type: "post",
                url: "/gifts/wantGift",
                cache: false,
                data: "giftuuid=" + $(this).data("gift-uuid"),
                success: function (response) {
                    alert("OK");
                },
                error: function (response) {
                    window.location = "500";
                }
            });
        });
        <%
            List<Gift> glst = pageGift.getNew();
            for (Gift g : glst) { %>
            $("#test").append("<%=g.getName()%>");
        <% } %>
    });
</script>