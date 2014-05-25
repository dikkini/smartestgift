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
<div id="shadow"></div>
<div class="row top-buffer">
    <div class="col-xs-12">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3">
                        <span id="loading-gifts" class="loading" style=""></span>
                    </div>
                    <div class="col-xs-6">
                        <div class="col-xs-6">
                            <input id="find-gift-input" type="text" class="form-control" placeholder="<spring:message code="label.searchGiftPlaceHolder"/>">
                        </div>
                        <div class="btn-group">
                            <button id="find-gift-btn" class="btn btn-default"><spring:message code="label.find"/></button>
                            <button id="random-gift-btn" class="btn btn-default"><spring:message code="label.random_gift"/></button>
                        </div>
                    </div>
                    <div class="col-xs-3">
                        <div class="page-size-container row">
                            <div class="col-xs-5">
                                <label for="page-size-select">Page size</label>
                            </div>
                            <div class="col-xs-5">
                                <select id="page-size-select" class="form-control">
                                    <option>10</option>
                                    <option>25</option>
                                    <option>50</option>
                                    <option>100</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="gifts-category" class="row top-buffer">
    <div class="col-xs-12">
        <div class="panel panel-primary">
            <div id="selected-category" class="panel-heading"></div>
            <ul class="pager" style="display: none;">
                <li><a class="pager-prev-btn" href="#">Previous</a></li>
                <li><a class="pager-next-btn" href="#">Next</a></li>
            </ul>
            <div class="panel-body">
                <div id="gifts" class="row">
                    <h3>There are no gifts.</h3>
                </div>
            </div>
            <ul class="pager" style="display: none;">
                <li><a class="pager-prev-btn" href="#">Previous</a></li>
                <li><a class="pager-next-btn" href="#">Next</a></li>
            </ul>
        </div>
    </div>
</div>

<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function () {
        // default page size value
        var pageSize = 10;
        // no mode on loading page
        var pagerMode = "";
        var REGULAR_MODE_PAGE = "REGULAR";
        var SEARCH_MODE_PAGE = "SEARCH";

        var ajaxLoadingGifts = $("#loading-gifts");
        var giftsContainer = $("#gifts");
        var pagerObj = $(".pager");
        var blockingDiv = $("#shadow");

        // TODO выставлять название категории

        $(".loading").loading({width: '25', text: 'Searching...'});

        $('#end-date-input').pickmeup({
            flat	: true
        });

        $("#find-gift-btn").click(function() {
            ajaxLoadingGifts.loading("start");
            blockingDiv.addClass("blocker");
            pagerMode = SEARCH_MODE_PAGE;

            var findGiftInputObj = $("#find-gift-input");
            var searchString = findGiftInputObj.val();
            if ($.trim(searchString).length == 0) {
                alert("wrong search string");
                blockingDiv.removeClass("blocker");
                ajaxLoadingGifts.loading("stop");
                return;
            }
            findGiftInputObj.data("search-string", searchString);

            renderSearchPageOfGifts(-1, 1, pageSize, searchString);
        });

        $("#page-size-select").change(function(e) {
            blockingDiv.addClass("blocker");
            pageSize = parseInt($(this).val());
            var countAll = $(".pager").data("countAll");
            switch (pagerMode) {
                case REGULAR_MODE_PAGE:
                    var categoryCode = $(".selected-category a").attr("href").replace("#", "");
                    renderRegularPageOfGifts(countAll, 1, pageSize, categoryCode);
                    break;
                case SEARCH_MODE_PAGE:
                    var searchString = $("#find-gift-input").data("search-string");
                    renderSearchPageOfGifts(countAll, 1, pageSize, searchString);
                    break;
            }
            e.preventDefault();
        });

        $("span.gift-category a").click(function () {
            ajaxLoadingGifts.loading("start");
            blockingDiv.addClass("blocker");
            pagerMode = REGULAR_MODE_PAGE;

            var giftsCategories = $(".gift-category");
            giftsCategories.each(function () {
                $(this).removeClass("selected-category");
            });
            $(this).parent().addClass("selected-category");
            var categoryCode = $(this).attr("href").replace("#", "");

            renderRegularPageOfGifts(-1, 1, pageSize, categoryCode);
        });

        $(".pager-next-btn").click(function (e) {
            var pageNum = pagerObj.data("pageNum");
            var countAll = pagerObj.data("countAll");

            switch (pagerMode) {
                case REGULAR_MODE_PAGE:
                    var categoryCode = $(".selected-category a").attr("href").replace("#", "");
                    renderRegularPageOfGifts(countAll, pageNum+1, pageSize, categoryCode);
                    break;
                case SEARCH_MODE_PAGE:
                    var searchString = $("#find-gift-input").data("search-string");
                    renderSearchPageOfGifts(countAll, pageNum+1, pageSize, searchString);
                    break;
            }

            e.preventDefault();
        });

        $(".pager-prev-btn").click(function (e) {
            var pageNum = pagerObj.data("pageNum");
            var countAll = pagerObj.data("countAll");

            switch (pagerMode) {
                case REGULAR_MODE_PAGE:
                    var categoryCode = $(".selected-category a").attr("href").replace("#", "");
                    renderRegularPageOfGifts(countAll, pageNum-1, pageSize, categoryCode);
                    break;
                case SEARCH_MODE_PAGE:
                    var searchString = $("#find-gift-input").data("search-string");
                    renderSearchPageOfGifts(countAll, pageNum-1, pageSize, searchString);
                    break;
            }
            e.preventDefault();
        });

        giftsContainer.on("click", ".want-gift-btn", function () {
            /*$("#want-gift-modal").modal("show");*/
/*            $.ajax({
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
            });*/
        });

        function renderSearchPageOfGifts(countAll, pageNum, pageSize, searchString) {
            ajaxLoadingGifts.loading("start");
            blockingDiv.addClass("blocker");
            $.ajax({
                type: "post",
                url: "/gifts/getFindGiftPage",
                cache: false,
                data: "countAll=" + countAll + "&pageNum=" + pageNum + "&pageSize=" + pageSize + "&searchString=" + searchString,
                success: function (response) {
                    var json = JSON.parse(response);
                    var results = json.results;
                    if (results.length == 0) {
                        var noGiftsError = '<h3><spring:message code="label.no_gifts_to_show"/></h3>';
                        giftsContainer.empty();
                        giftsContainer.append(noGiftsError);
                        renderPagerButtons(-1, 0, false, false);
                        $("#page-size-container").hide();
                        blockingDiv.removeClass("blocker");
                        ajaxLoadingGifts.loading("stop");
                        return;
                    }
                    renderGifts(results);
                    renderPagerButtons(json.countAll, json.pageNum, json.isNextPage, json.isPreviousPage);
                },
                error: function (response) {
                    window.location = "500";
                }
            });
        }

        function renderRegularPageOfGifts(countAll, pageNum, pageSize, categoryCode) {
            ajaxLoadingGifts.loading("start");
            blockingDiv.addClass("blocker");
            $.ajax({
                type: "post",
                url: "/gifts/changePage",
                cache: false,
                data: "countAll=" + countAll + "&pageNum=" + pageNum + "&pageSize=" + pageSize + "&categoryCode=" + categoryCode,
                success: function (response) {
                    var json = JSON.parse(response);
                    var results = json.results;
                    if (results.length == 0) {
                        var noGiftsError = '<h3><spring:message code="label.no_gifts_to_show"/></h3>';
                        giftsContainer.empty();
                        giftsContainer.append(noGiftsError);
                        renderPagerButtons(-1, 0, false, false);
                        $("#page-size-container").hide();
                        blockingDiv.removeClass("blocker");
                        ajaxLoadingGifts.loading("stop");
                        return;
                    }
                    renderGifts(results);
                    renderPagerButtons(json.countAll, json.pageNum, json.isNextPage, json.isPreviousPage);
                },
                error: function (response) {
                    window.location = "500";
                }
            });
        }

        function renderGifts(gifts) {
            ajaxLoadingGifts.loading("start");
            giftsContainer.empty();

            var today = new Date();
            var weeakAgoDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() - 7);
            gifts.forEach(function (entry) {
                var html = '<div class="gift col-xs-3">';
                entry.files.forEach(function (file) {
                    var giftDate = new Date(entry.addDate);
                    var timeDiff = Math.abs(giftDate.getTime() - weeakAgoDate.getTime());
                    var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
                    if (diffDays < 7) {
                        html += '<span class="gift-new-label">';
                    }
                    html += '<a href="/gifts/' + entry.category.code + '/' + entry.uuid + '">';
                    html += '<img height="200" src="/file/get/' + file.id + '">';
                    html += '</a>';
                });
                html += '<p>' + entry.name + '</p>';
                html += '<p class="ellipses small">' + entry.description + '</p>';
                // TODO (?) проверка есть ли у пользователя этот подарок в желаемых
                html += '<button data-gift-uuid="' + entry.uuid + '"';
                html += 'class="btn btn-default want-gift-btn" data-toggle="modal" data-target="#want-gift-modal"> <spring:message code="label.wanttogift"/>';
                html += '</button>';

                giftsContainer.append(html);
            });

            $("#page-size-container").show();
            blockingDiv.removeClass("blocker");
            ajaxLoadingGifts.loading("stop");
        }

        function renderPagerButtons(countAll, pageNum, isNextPage, isPreviousPage) {
            var previousPageBtn = $(".pager-prev-btn");
            var nextPageBtn = $(".pager-next-btn");
            if (!isNextPage) {
                nextPageBtn.hide();
            } else {
                nextPageBtn.show();
            }

            if (!isPreviousPage) {
                previousPageBtn.hide();
            } else {
                previousPageBtn.show();
            }

            pagerObj.data("pageNum", pageNum);
            pagerObj.data("countAll", countAll);
            pagerObj.show();
        }

        jQuery(window).bind('beforeunload', function (e) {
            savePagerDataOnExit();
            e.preventDefault();
        });

        function savePagerDataOnExit() {
            var pageNum = pagerObj.data("pageNum");
            var countAll = pagerObj.data("countAll");
            var categoryCode = $(".selected-category a").attr("href").replace("#", "");
            var searchString = $("#find-gift-input").data("search-string");

            var json = {
                "pagerMode": pagerMode,
                "pageNum": pageNum,
                "countAll": countAll,
                "pageSize": pageSize,
                "categoryCode":categoryCode,
                "searchString":searchString
            };

            window.history.pushState(json, "", window.location.href);
        }

        window.onpopstate = function (e) {
            if (e.state) {
                pagerMode = e.state.pagerMode;
                pageSize = e.state.pageSize;
                switch (pagerMode) {
                    case REGULAR_MODE_PAGE:
                        $(".gift-category").each(function () {
                            if ($(this).find("a").attr("href").replace("#", "") == e.state.categoryCode) {
                                $(this).addClass("selected-category");
                            }
                        });
                        renderRegularPageOfGifts(e.state.countAll, e.state.pageNum, e.state.pageSize, e.state.categoryCode);
                        break;
                    case SEARCH_MODE_PAGE:
                        renderSearchPageOfGifts(e.state.countAll, e.state.pageNum, e.state.pageSize, e.state.searchString);
                        break;
                }
            }
        };
    });
</script>

<jsp:include page="/WEB-INF/pages/template/want-gift-modal.jsp"/>