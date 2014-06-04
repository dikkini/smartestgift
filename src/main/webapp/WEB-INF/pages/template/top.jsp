<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8"/>

<sec:authentication var="user" property="principal"/>

<jsp:useBean id="user" class="com.smartestgift.dao.model.SmartUserDetails" scope="request"/>
<jsp:useBean id="constants" class="com.smartestgift.utils.ApplicationConstants" scope="request"/>

<!DOCTYPE HTML>

<html lang="en">
<head>
    <title><spring:message code="label.title"/></title>
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="/assets/main/css/style.css">
    <link rel="stylesheet" href="/assets/ext/kladr/jquery.kladr.css">
    <link rel="stylesheet" href="/assets/ext/jquery/notification/pnotify.custom.min.css">
    <link rel="stylesheet" href="/assets/main/css/fileupload.css">
    <link rel="stylesheet" href="/assets/ext/jquery/ui-1.10.4/css/custom-theme/jquery-ui-1.10.4.custom.min.css">

    <script type="text/javascript" src="/assets/ext/jquery/jquery-2.0.3.js"></script>
    <script type="text/javascript" src="/assets/ext/jquery/ui-1.10.4/js/jquery-ui-1.10.4.custom.min.js"></script>
    <script type="text/javascript" src="https://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/assets/ext/kladr/jquery.kladr.min.js"></script>
    <script type="text/javascript" src="/assets/ext/bootbox/bootbox.min.js"></script>
    <script type="text/javascript" src="/assets/ext/common/modernizr.js"></script>
    <script type="text/javascript" src="/assets/ext/common/sockjs-0.3.4.js"></script>
    <script type="text/javascript" src="/assets/ext/common/stomp.js"></script>
    <script type="text/javascript" src="/assets/main/js/loading.js"></script>
    <script type="text/javascript" src="/assets/main/js/utils.js"></script>
    <script type="text/javascript" src="/assets/main/js/notifications.js"></script>
    <script type="text/javascript" src="/assets/ext/jquery/notification/pnotify.custom.min.js"></script>

    <script type="text/javascript" src="/assets/ext/jquery/fileupload/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="/assets/ext/jquery/fileupload/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="/assets/ext/jquery/fileupload/jquery.fileupload.js"></script>
    <script type="text/javascript" src="/assets/ext/jquery/fileupload/jquery.fileupload-process.js"></script>
    <script type="text/javascript" src="/assets/ext/jquery/fileupload/jquery.fileupload-image.js"></script>
    <script type="text/javascript" src="/assets/ext/jquery/fileupload/jquery.fileupload-validate.js"></script>
</head>

<body>
<form name="refreshForm">
    <input type="hidden" name="visited" value=""/>
</form>
<div class="header">
    <header>
        <div class="row">
            <div class="col-md-11">
                <img src="/assets/main/images/logo.png">
            </div>
            <div class="col-md-1">
                <span style="float: right">
                    <a href="?lang=en">en</a>
                    |
                    <a href="?lang=ru">ru</a>
                </span>
            </div>
        </div>
        <div class="navbar navbar-inverse">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span
                            class="icon-bar"></span><span class="icon-bar"></span>
                    </button>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <sec:authorize access="isAuthenticated()">
                            <li>
                                <a href="/profile"><spring:message code="label.profile"/></a>
                            </li>
                            <li>
                                <a href="<c:url value="/gifts/my"/>"><spring:message code="label.mygifts"/></a>
                            </li>
                            <li>
                                <a href="<c:url value="/messages"/>"><spring:message code="label.messages"/> <span
                                        id="countUnreadMessages" class="badge"></span></a>
                            </li>
                        </sec:authorize>
                        <sec:authorize access="isAnonymous()">
                            <li>
                                <a href="/"><spring:message code="label.home"/></a>
                            </li>
                        </sec:authorize>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <sec:authorize access="isAuthenticated()">
                            <li>
                                <form class="navbar-form navbar-right" role="search">
                                    <div class="col-xs-12 form-group">
                                        <input id="global-search-input" type="text" class="form-control"
                                               placeholder="Search people and gifts">
                                    </div>
                                </form>
                            </li>
                            <li>
                                <p class="navbar-text navbar-right"><spring:message code="label.signed"/>
                                    <a href="/profile" class="navbar-link">
                                        <c:out value="${user.smartUser.firstName}"/>
                                        <c:out value="${user.smartUser.lastName}"/>
                                    </a>
                                </p>
                            </li>
                            <li>
                                <a href="<c:url value="/profile/settings"/>"><spring:message code="label.settings"/></a>
                            </li>
                            <li>
                                <a href="<c:url value="/logout"/>"><spring:message code="label.logout"/></a>
                            </li>
                        </sec:authorize>
                        <sec:authorize access="isAnonymous()">
                            <li>
                                <a href="<c:url value="/login"/>"><spring:message code="label.login"/></a>
                            </li>
                            <li>
                                <a href="<c:url value="/signup"/>"><p class="navbar-right"><spring:message
                                        code="label.signup"/></p></a>
                            </li>
                        </sec:authorize>
                    </ul>
                </div>
            </div>
        </div>
    </header>
</div>

<script type="text/javascript">
    var socket;
    $(document).ready(function () {
        <sec:authorize access="isAuthenticated()">
        socket = new SockJS('/messages');
        var stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            stompClient.send("/app/setUnreadCount", {}, {});
//                console.log('Connected: ' + frame);
            stompClient.subscribe('/user/' + '${user.username}' + '/getUnreadMessagesCount', function (response) {
                response = JSON.parse(response.body);
                $("#countUnreadMessages").text(response);
            });
        });

        var cache = {};

        var customRenderMenu = function(ul, items){
                var that = this,
                        currentCategory = "";
                var giftItems = items[0].gift;
                var userItems = items[0].user;

                if (giftItems.length > 0) {
                    ul.append("<li class='ui-autocomplete-category'>" + "GIFTS" + "</li>");
                }
                $.each(giftItems, function (index, item) {
                    var gift = {
                        type: 'gift',
                        name: item.name,
                        descr: item.description,
                        price: item.price,
                        imgId: item.files[0].id
                    };
                    that._renderItemData(ul, gift);
                });

                if (userItems.length > 0) {
                    ul.append("<li class='ui-autocomplete-category'>" + "USERS" + "</li>");
                }
                $.each(userItems, function (index, item) {
                    var user = {
                        type: 'user',
                        name: (item.lastName == null ? "  " : item.lastName + " ") + item.firstName + " " + (item.middleName == null ? "" : " " + item.middleName),
                        imgId: item.file.id
                    };
                    that._renderItemData(ul, user);
                });
            };

        $("#global-search-input").autocomplete({
            create: function () {
                //access to jQuery Autocomplete widget differs depending
                //on jQuery UI version - you can also try .data('autocomplete')
                $(this).data('ui-autocomplete')._renderMenu = customRenderMenu;
            },
            open: function() {
                $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
            },
            close: function() {
                $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
            },
            select: function (event, ui) {
                console.log("select event:" + event);
                console.log("select ui:" + ui);
                // TODO action on select from autocomplete
                /*alert( ui.item ?
                 "Selected: " + ui.item.value + " aka " + ui.item.id :
                 "Nothing selected, input was " + this.value );*/
            },
            delay: 500,
            source: function (request, response) {
                var that = this;

                var term = request.term;
                if (term in cache) {
                    var data = JSON.parse(cache[term]);
                    response({items:data});
                    return;
                }

                $.ajax({
                    url: '/globalSearch',
                    type: 'post',
                    data: {searchString: term},
                    dataType: "json",
                    success: function (data) {
                        cache[ term ] = data;
                        data = JSON.parse(data);
                        response({items:data});
                    },
                    error: function (data) {
                        alert("bad");
                    }
                });

            }
        }).data("ui-autocomplete")._renderItem = function (ul, item) {
            var html =
                    "<li>" +
                        "<a>" +
                            "<div class='row'>" +
                                "<div class='col-xs-4'>" +
                                    "<img height='50' src='/file/get/" + item.imgId + "'>" +
                                "</div>" +
                                "<div class='col-xs-8'>" +
                                    "<h5>" +
                                        item.name +
                                    "</h5>" +
                                "</div>" +
                            "</div>" +
                        "</a>" +
                    "</li>";
            return ul.append(html);
        };
        </sec:authorize>
    });
</script>

<div class="container">
