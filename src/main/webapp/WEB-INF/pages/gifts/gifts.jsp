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
<div style="height: 25px">
    <span id="loading-gifts" class="loading" style=""></span>
</div>
<div id="gifts-category" class="row top-buffer">
    <div class="col-xs-12">
        <div class="panel panel-primary">
            <div id="selected-category" class="panel-heading"></div>
            <div class="panel-body">
                <div id="gifts" class="row">
                    <h3>There are no gifts.</h3>
                </div>
            </div>
            <ul id="pager" class="pager" style="display: none;">
                <li><a id="pager-prev-btn" class="" href="#">Previous</a></li>
                <li><a id="pager-next-btn" class="" href="#">Next</a></li>
            </ul>
        </div>
    </div>
</div>

<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function () {

        // TODO выставлять название категории
        // TODO сделать так чтобы кнопки Next и Previous в пагинации были задзаблейны если нет следующей страницы или предыдущей
        // TODO сделать выбор количества элементов на странице и поправить код где эта переменная учавствует
        // TODO убрать кнопки и вывести надпись что нет элементов, если нет элементо

        $(".loading").loading({width: '25', text: 'Searching...'});

        function savePagerDataOnExit() {
            var pagerObj = $("#pager");
            var pageNum = pagerObj.data("pageNum");
            var pageSize = pagerObj.data("pageSize");
            var categoryCode = $(".selected-category").data('category-code');

            window.history.pushState({"pageNum": pageNum, "pageSize": pageSize, "categoryCode": categoryCode}, "", window.location.href);
        }

        jQuery(window).bind('beforeunload', function (e) {
            savePagerDataOnExit();
            e.preventDefault();
        });

        window.onpopstate = function (e) {
            if (e.state) {
                var wasPageNum = e.state.pageNum;
                /*
                 отнимаем единицу у страницы которая была, и передаем аяксу что загружаем
                 следующую страницу, таким образом загрузим нужную.
                 */
                wasPageNum = wasPageNum - 1;
                changePageAndRender(true, wasPageNum, e.state.pageSize, e.state.categoryCode);
            }
        };

        $("span.gift-category a").click(function () {
            // при клике на категорию, выставляем стандартные параметры - 0 страница и дефолтное количество элементов
            var giftsCategories = $(".gift-category");
            giftsCategories.each(function () {
                $(this).removeClass("selected-category");
            });
            $(this).parent().addClass("selected-category");
            var categoryCode = $(this).attr("href").replace("#", "");

            // 0 - начальная страница, при клике по категории просмотр всегда начинается с 0вой страницы
            // TODO поменять pagesize на переменную из формочки
            changePageAndRender(true, 0, 3, categoryCode);
        });

        $("#pager-next-btn").click(function (e) {
            var pagerObj = $("#pager");
            var pageNum = pagerObj.data("pageNum");
            var pageSize = pagerObj.data("pageSize");
            var categoryCode = $(".selected-category a").attr("href").replace("#", "");

            changePageAndRender(true, pageNum, pageSize, categoryCode);
            e.preventDefault();
        });


        $("#pager-prev-btn").click(function (e) {
            var pagerObj = $("#pager");
            var pageNum = pagerObj.data("pageNum");
            var pageSize = pagerObj.data("pageSize");
            var categoryCode = $(".selected-category a").attr("href").replace("#", "");

            changePageAndRender(false, pageNum, pageSize, categoryCode);
            e.preventDefault();
        });

        function changePageAndRender(next, pageNum, pageSize, categoryCode) {
            $("#loading-gifts").loading("start");

            if (next) {
                pageNum += 1;
            } else {
                pageNum -= 1;
            }

            $.ajax({
                type: "post",
                url: "/gifts/changePage",
                cache: false,
                data: "next=" + next + "&pageNum=" + pageNum + "&pageSize=" + pageSize + "&categoryCode=" + categoryCode,
                success: function (response) {
                    var giftsContainer = $("#gifts");
                    var pagerObj = $("#pager");
                    giftsContainer.empty();

                    var json = JSON.parse(response);
                    var results = json.results;
                    var today = new Date();
                    var weeakAgoDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() - 7);
                    results.forEach(function (entry) {
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
                        html += 'class="btn btn-default want-gift-btn"> <spring:message code="label.wanttogift"/>';
                        html += '</button>';

                        giftsContainer.append(html);
                        pagerObj.show();
                    });

                    // TODO fix it. it wont work
                    <%--if (results.size() == 0) {--%>
                        <%--var test = '<h3>' + <spring:message code="label.no_gifts_to_show"/> + '</h3>';--%>
                        <%--giftsContainer.append(test);--%>
                    <%--}--%>

                    var previousPageBtn = $("#pager-prev-btn");
                    var nextPageBtn = $("#pager-next-btn");
                    if (!json.isNextPage) {
                        nextPageBtn.hide();
                    } else {
                        nextPageBtn.show();
                    }

                    if (!json.isPreviousPage) {
                        previousPageBtn.hide();
                    } else {
                        previousPageBtn.show();
                    }

                    $("#loading-gifts").loading("stop");
                    /*
                    Если вдруг так произошло что пользователь умудрился нажать next page или prev page когда они залочены
                    что надо проставять в pager object.
                     */
                    // TODO отладить этот вопрос
                    pagerObj.data("pageNum", json.pageNum);
                    pagerObj.data("pageSize", json.pageSize);
                },
                error: function (response) {
                    window.location = "500";
                }
            });
        }

        $(".want-gift-btn").click(function () {
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