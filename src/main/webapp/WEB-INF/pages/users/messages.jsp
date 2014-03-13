<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<jsp:useBean id="userConversations" type="java.util.List<com.smartestgift.dao.model.Conversation>" scope="request"/>
<jsp:useBean id="smartUser" class="com.smartestgift.dao.model.SmartUser" scope="request"/>

<jsp:include page="../template/top.jsp"/>

<div class="row">
    <div class="col-xs-4">
        <ul class="nav navbar-nav">
            <li class="active"><a href="#">Inbox <span class="badge">42</span></a></li>
            <li><a href="#">Sent</a></li>
        </ul>
        <div class="col-lg-9">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="Find conversations">
              <span class="input-group-btn">
                <button style="margin-bottom: 20px" class="btn btn-default" type="button">Search</button>
              </span>
            </div>
            <div>
                <ul class="nav nav-pills nav-stacked">
                    <c:choose>
                        <c:when test="${fn:length(userConversations) == 0}">
                            You have no conversations now.
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${userConversations}" var="conversation">
                                <c:choose>
                                    <c:when test="${conversation.user_from == smartUser}">
                                        <c:set var="fromUserConversation" value="${conversation.user_to}" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="fromUserConversation" value="${conversation.user_from}" />
                                    </c:otherwise>
                                </c:choose>

                                <li>
                                    <div class="list-group">
                                        <a href="#" data-conversation-username="${fromUserConversation.username}" data-conversation-uuid="${conversation.uuid}" class="list-group-item conversation">
                                            <div class="row">
                                                <div class="col-xs-3">
                                                    <img height="50" src="/file/get/${fromUserConversation.file.id}">
                                                </div>
                                                <div class="col-xs-9">
                                                    <p class="list-group-item-heading">${fromUserConversation.username}</p>
                                                    <p class="list-group-item-text ellipses">message?</p>
                                                </div>
                                            </div>
                                        </a>
                                    </div>
                                </li>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </div>
    <div class="col-xs-8">
        <strong id="messages-title">New Message</strong>
        <a id="btn-new-message" style="margin-left: 20px" class="btn btn-primary">New Message</a>
        <div class="btn-group">
            <a class="btn btn-default dropdown-toggle" data-toggle="dropdown" href="#">
                Actions
                <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <li><a href="#">First Link</a></li>
                <li><a href="#">Second Link</a></li>
                <li><a href="#">Third Link</a></li>
            </ul>
        </div>
        <div style="margin-top: 20px">
            <ul style="margin-bottom: 20px" id="messages-dialog" class="nav nav-pills nav-stacked"><%--messages here--%></ul>
            <div id="new-message-recepient-form">
                <input id="input-new-conversation-recipient" type="text" class="form-control" placeholder="Start write a username or first name">
            </div>
            <div id="new-message-input-form" class="row" style="margin-bottom: 20px; display: none;">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Write new message here">
                    <span class="input-group-btn">
                        <button id="btn-send-message" class="btn btn-primary" type="button">Send</button>
                    </span>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function(){
        $(".conversation").click(function() {
            var conversationObj = $(this);
            $.ajax({
                type: "post",
                url: "/messages/getMessagesWithUser",
                cache: false,
                data: "conversationUuid=" + conversationObj.data("conversation-uuid"),
                success: function (response) {
                    var messagesDialog = $("#messages-dialog");
                    messagesDialog.empty();

                    response.forEach(function(entry) {
                        var html = '<li><div class="list-gropu"><a href="#" class="list-group-item"><div class="row"><div class="col-xs-1">';
                        html += '<img height="50" src="/file/get/' + entry.smartUser.file.id + '"></div><div class="col-xs-9">';
                        html += '<p class="list-group-item-heading">' + entry.smartUser.username + '</p>'
                        html += '<p class="list-group-item-text ellipses">' + entry.message + '</p>'
                        messagesDialog.append(html);
                    });
                    $("#new-message-input-form").show();
                    $("#messages-title").text(conversationObj.data("conversation-username"));
                },
                error: function (response) {
                    //TODO обработка ошибок
                    alert("error");
                }
            });
        });

        $("#btn-new-message").click(function() {

        });
    });
</script>

