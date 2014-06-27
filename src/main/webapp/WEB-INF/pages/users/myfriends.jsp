<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<jsp:useBean id="smartUser" class="com.smartestgift.dao.model.SmartUser" scope="request"/>
<jsp:useBean id="smartUserFriends" type="java.util.List<com.smartestgift.dao.model.SmartUserFriend>" scope="request"/>

<jsp:include page="../template/top.jsp"/>

<div class="row top-buffer" style="margin-left: 200px">
    <div class="col-xs-8">
        <div class="panel panel-primary">
            <div class="panel-heading"><spring:message code="label.gift_categories"/></div>
            <div class="panel-body">
                <div class="row">
                    <div id="people-container" class="col-xs-12">
                        <ul id="friends" class="nav nav-pills nav-stacked">
                            <span id="loading-people" class="loading" style=""></span>
                            <c:forEach items="${smartUserFriends}" var="smartUserFriend">
                                <li  class="contact" tabindex="1">
                                    <div class="list-group">
                                        <a class="user list-group-item" data-username="${smartUserFriend.friendUser.username}" style="cursor: pointer">
                                            <div class="row">
                                                <div class="col-xs-2">
                                                    <img height="50" src="/file/get/${smartUserFriend.friendUser.file.id}">
                                                </div>
                                                <div class="col-xs-8">
                                                    <p class="list-group-item-heading">${smartUserFriend.friendUser.lastName} ${smartUserFriend.friendUser.firstName} ${smartUserFriend.friendUser.middleName}</p>
                                                    <p class="list-group-item-text">${smartUserFriend.friendUser.username}</p>
                                                </div>
                                            </div>
                                        </a>
                                        <button style="float: right;" class="btn btn-default accept-friend-request-btn" data-username="${smartUserFriend.friendUser.username}">Accept Request</button>
                                        <button style="float: right;" class="btn btn-default decline-friend-request-btn" data-username="${smartUserFriend.friendUser.username}">Decline Request</button>
                                        <button style="float: right;" class="btn btn-default remove-friend-btn" data-username="${smartUserFriend.friendUser.username}">Remove Friend</button>
                                        <div class="clearfix"/>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function(){
        $(".accept-friend-request-btn").click(function() {
            var friendUsername = $(this).data("username");
            $.ajax({
                async: true,
                type: "post",
                url: "/users/acceptFriendRequest.do",
                cache: false,
                data: "friendUsername=" + friendUsername,
                success: function (response) {
                    alert(response.success);
                },
                error: function (response) {
                    alert(response);
                }
            });
        });

        $(".remove-friend-btn").click(function() {
            var friendUsername = $(this).data("username");
            $.ajax({
                async: true,
                type: "post",
                url: "/users/removeFriend.do",
                cache: false,
                data: "friendUsername=" + friendUsername,
                success: function (response) {
                    alert(response.success);
                },
                error: function (response) {
                    alert(response);
                }
            });
        });

        $(".decline-friend-request-btn").click(function() {
            var friendUsername = $(this).data("username");
            $.ajax({
                async: true,
                type: "post",
                url: "/users/declineFriendRequest.do",
                cache: false,
                data: "friendUsername=" + friendUsername,
                success: function (response) {
                    alert(response.success);
                },
                error: function (response) {
                    alert(response);
                }
            });
        });
    });
</script>