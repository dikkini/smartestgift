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
<jsp:useBean id="smartUser" class="com.smartestgift.dao.model.SmartUser" scope="request"/>
<jsp:useBean id="gift" class="com.smartestgift.dao.model.Gift" scope="request"/>

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
<div class="row top-buffer">
    <div class="col-xs-12">
        <div class="panel panel-primary">
            <div id="selected-category" class="panel-heading"></div>
            <div class="panel-body">
                <div id="gifts" class="row">
                    <span id="loading-gifts" class="loading" style=""></span>
                </div>
            </div>
            <ul id="pager" class="pager">
                <li><a id="pager-prev-btn" class="" href="#">Previous</a></li>
                <li><a id="pager-next-btn" class="" href="#">Next</a></li>
            </ul>
        </div>
    </div>
</div>

<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function(){
        $(".loading").loading({width: '25', text: 'Searching...'});

        $("span.gift-category a").click(function() {
            $("#loading-gifts").loading("start");

            var giftsCategories = $(".gift-category");
            giftsCategories.each(function() {
                $(this).removeClass("selected");
            });
            $(this).parent().addClass("selected");
            var categoryCode =  $(this).attr("href").replace("#", "");
            changePageAndRender(true, 0, 1, categoryCode);
            $("#selected-category").data('category-code', categoryCode);
        });

        $("#pager-next-btn").click(function() {
            var pageNum = $("#pager").data("pageNum");
            var pageSize = $("#pager").data("pageSize");
            var categoryCode = $("#selected-category").data('category-code');
            changePageAndRender(true, pageNum, pageSize, categoryCode);
        });


        $("#pager-prev-btn").click(function() {
            var pageNum = $(this).parent().data("pageNum");
            var pageSize = $(this).parent().data("pageSize");
            var categoryCode = $("#selected-category").data('category-code');
            changePageAndRender(false, pageNum, pageSize, categoryCode);
        });

        function changePageAndRender(next, pageNum, pageSize, categoryCode) {
            $.ajax({
                type: "post",
                url: "/gifts/changePage",
                cache: false,
                data: "next=" + next + "&pageNum=" + pageNum + "&pageSize=" + pageSize + "&categoryCode=" + categoryCode,
                success: function (response) {
                    var giftsContainer = $("#gifts");
                    if (giftsContainer.children() > 1) {
                        giftsContainer.empty();
                    }
                    var json = JSON.parse(response);
                    var results = json.results;
                    var today = new Date();
                    var weeakAgoDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() - 7);
                    results.forEach(function(entry) {
                        var html = '<div class="gift col-xs-3">';
                        entry.files.forEach(function(file){
                            var giftDate = new Date(entry.addDate);
                            var timeDiff = Math.abs(giftDate.getTime() - weeakAgoDate.getTime());
                            var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
                            if (diffDays < 7) {
                                html += '<span class="gift-new-label">';
                            }
                            html += '<a href="/gifts/' + entry.category.code + '/' + entry.uuid + '">';
                            html += '<img height="200" src="/file/get/'+ file.id +'">';
                            html += '</a>';
                        });
                        html += '<p>' + entry.name + '</p>'
                        html += '<p class="ellipses small">' + entry.description + '</p>';
                        // TODO (?) проверка есть ли у пользователя этот подарок в желаемых
                        html += '<button data-gift-uuid="' + entry.uuid + '"';
                        html += 'class="btn btn-default want-gift-btn"> <spring:message code="label.wanttogift"/>';
                        html += '</button>';

                        $("#gifts").append(html);
                        $("#loading-gifts").loading("stop");
                    });

                    setPagerData(json.pageNum, json.pageSize);
                },
                error: function (response) {
                    window.location = "500";
                }
            });
        }

        function setPagerData(pageNum, pageSize) {
            var pagerObj = $("#pager");
            pagerObj.data("pageNum", pageNum);
            pagerObj.data("pageSize", pageSize);
        }

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
    });
</script>