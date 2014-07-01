<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<jsp:useBean id="smartUser" class="com.smartestgift.dao.model.SmartUser" scope="request"/>

<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${fn:replace(req.requestURL, fn:substring(req.requestURI, 1, fn:length(req.requestURI)), req.contextPath)}" />

<jsp:include page="../template/top.jsp"/>

<div class="row">
    <div class="col-xs-3">
        <div class="panel">
            <img width="200" height="200" src="/file/get/${smartUser.file.id}">
        </div>

        <div class="panel panel-primary">
            <div class="panel-heading"><spring:message code="label.gift"/></div>
            <div class="panel-body">
                <ul class="nav nav-pills nav-stacked">
                    <li><a href="/gifts"><spring:message code="label.find_gift"/></a></li>
                </ul>
            </div>
        </div>

        <div class="panel panel-primary">
            <div class="panel-heading"><spring:message code="label.social"/></div>
            <div class="panel-body">
                <ul class="nav nav-pills nav-stacked">
                    <li><a href="/users/myfriends"><spring:message code="label.friends"/></a></li>
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
                            <a href="/gifts/<c:out value="${smartUserGift.giftShop.gift.uuid}"/>"><c:out value="${smartUserGift.giftShop.gift.name}"/></a>
                        </p>
                        <p class="ellipses"><c:out value="${smartUserGift.giftShop.gift.description}"/></p>
                        <small><spring:message code="label.collected_money_for_gift"/>: <c:out value="${smartUserGift.moneyCollect}"/> </small>
                    </blockquote>
                    <div class="row">
                        <button id="${smartUserGift.uuid}" class="btn btn-default copy-button" data-clipboard-text="<c:out value="${baseURL}${smartUserGift.smartUserGiftURL.shortUrl}"/>" title="Click to copy me.">Copy to Clipboard</button>
                        <button data-giftshop-uuid="<c:out value="${smartUserGift.giftShop.uuid}"/>" class="btn bgiftShoplt un-want-gift-btn"><spring:message code="label.un_want_gift_button"/></button>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <input class="col-xs-12" type="text" value="<c:out value="${baseURL}${smartUserGift.smartUserGiftURL.shortUrl}"/>" disabled>
                        </div>
                    </div>
                </li>
            </c:forEach>
            <c:if test="${fn:length(smartUser.smartUserGifts) > 3}">
                <a style="float: right;" href="/gifts/mygifts"><spring:message code="label.all_gifts_in_wishlist"/> <c:out value="${fn:length(smartUser.smartUserGifts)}"/></a>
            </c:if>
        </ul>
    </div>
</div>

<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function(){
        var copyButtonsObj = $(".copy-button");
        var clip;
        copyButtonsObj.each(function() {
            clip = new ZeroClipboard(document.getElementById($(this).attr('id')));
        });

        clip.on("ready", function() {
            console.log("Flash movie loaded and ready.");

            this.on("aftercopy", function(event) {
                // TODO локализация
                alert("Copied text to clipboard: " + event.data["text/plain"]);
            });
        });

        clip.on('noflash', function (client, args) {
            $("#copy-button").click(function() {
                // TODO локализация
                prompt ("Copy link, then click OK.", $($(this).attr('data-clipboard-target')).val());
            });
        });

        clip.on("error", function(event) {
            console.log('error[name="' + event.name + '"]: ' + event.message);
            ZeroClipboard.destroy();
        });

        copyButtonsObj.click(function() {
            clip.clip(document.getElementById($(this).attr('id')));
        });

        $(".un-want-gift-btn").click(function(e) {
            $.ajax({
                type: "post",
                url: "/gifts/unWantGift",
                cache: false,
                data: "giftShopUuid=" + $(this).data("giftshop-uuid"),
                success: function (response) {
                    alert("ok");
                },
                error: function (response) {
                    alert("error");
                }
            });
            e.preventDefault();
        });
    });
</script>