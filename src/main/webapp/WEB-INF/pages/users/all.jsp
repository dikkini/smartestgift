<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ taglib prefix="fns" uri="/WEB-INF/custom-functions.tld"%>

<fmt:requestEncoding value="utf-8"/>

<jsp:useBean id="smartUser" class="com.smartestgift.dao.model.SmartUser" scope="request"/>

<jsp:include page="../template/top.jsp"/>

<div class="row top-buffer" style="margin-left: 200px">
    <div class="col-xs-8">
        <div class="panel panel-primary">
            <div class="panel-heading"><spring:message code="label.gift_categories"/></div>
            <div class="panel-body">
                <div class="row">
                    <div id="people-container" class="col-xs-12">
                        <ul>
                            <li class="list-group-item">
                                <ul id="people" class="nav nav-pills nav-stacked"
                                    style="margin-bottom: 20px; overflow: auto;">
                                    <%--people here--%>
                                </ul>
                                <span id="loading-people" class="loading" style=""></span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function () {

        $(".loading").loading({width: '25', text: 'Waiting...'});
        var offset = 0;
        var peopleContainer = $("#people");
        var processing = false;

        $(document).on("click", ".add-friend-btn", function() {
            var friendUsername = $(this).data("username");
            $.ajax({
                async: true,
                type: "post",
                url: "/users/addFriendRequest.do",
                cache: false,
                data: "friendUsername=" + friendUsername,
                success: function (response) {
                    alert(response.message);
                },
                error: function (response) {
                    alert("error");
                }
            });
        });

        function getAndRender() {
            $("#loading-people").loading("start");
            $.ajax({
                async: true,
                type: "get",
                url: "/users/findPeople.do",
                cache: false,
                data: "offset=" + offset,
                success: function (response) {

                    response.message.forEach(function(entry) {
                        var html =
                                '<li class="contact" tabindex="1" data-fio="' + (entry.lastName ? entry.lastName + " " : "") + entry.firstName + (entry.middleName ? " " + entry.middleName : "") + '">' +
                                    '<div class="list-group">' +
                                        '<a class="user list-group-item" data-username="' + entry.username + '"style="cursor: pointer">' +
                                        '<div class="row">' +
                                            '<div class="col-xs-2">' +
                                                '<img height="50" src="/file/get/' + entry.file.id + '">' +
                                            '</div>' +
                                            '<div class="col-xs-8">' +
                                                '<p class="list-group-item-heading">' +
                                                    (entry.lastName ? entry.lastName + " " : "") + entry.firstName + (entry.middleName ? " " + entry.middleName : "")  +
                                                '</p>' +
                                                '<p class="list-group-item-text">' +
                                                    entry.username +
                                                '</p>' +
                                            '</div>'  +
                                        '</div>' +
                                        '</a>' +
                                        '<button style="float: right;" class="btn btn-default add-friend-btn" data-username="'+entry.username+'">Add Friend</button>' +
                                        '<div class="clearfix">' +
                                    '</div>' +
                                '</li>';

                        peopleContainer.append(html);
                    });
                    offset += ${constants.PEOPLE_SEARCH_RESULTS_COUNT};
                    $("#loading-people").loading("stop");
                    processing = false;
                },
                error: function (response) {
                    //TODO обработка ошибок
                    console.log(response.responseText)
                    $("#loading-people").loading("stop");
                    alert("error");
                }
            });
        }

        getAndRender();

        // TODO стандартную пагинацию и включение стандартной пагинации если выключен javascript
        $(document).scroll(function() {
            if (processing)
                return;

            if ($(window).scrollTop() >= ($(document).height() - $(window).height())*0.7){
                processing = true; //sets a processing AJAX request flag
                console.log("go scrolling results");
                getAndRender();
            }
        });

        peopleContainer.on("click", ".user", function() {
            window.location = "/users/" + $(this).data("username");
        });
    });
</script>