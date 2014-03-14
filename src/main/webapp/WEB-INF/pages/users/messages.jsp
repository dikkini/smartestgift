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
            <li class="active"><a href="#">Inbox <span class="badge"></span></a></li>
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
                                        <a data-conversation-username="${fromUserConversation.username}" data-conversation-uuid="${conversation.uuid}" class="list-group-item conversation" style="cursor: pointer;">
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
            <div id="conversation-message">
                <ul style="margin-bottom: 20px; max-height: 250px; overflow: auto;" id="messages-dialog" class="nav nav-pills nav-stacked"><%--messages here--%></ul>
            </div>

            <div id="new-message-input-form" style="margin-bottom: 20px; display: none;">
                <ul class="list-group">
                    <li class="list-group-item">
                        <input id="input-new-conversation-recipient" data-username="" type="text" class="form-control" placeholder="Username or First name">
                    </li>
                    <li class="list-group-item">
                        <div class="recipient-auto-complete" style="max-height: 250px; overflow: auto;"></div>
                    </li>
                </ul>
            </div>

            <ul class="list-group">
                <li class="list-group-item">
                    <textarea id="input-new-message" class="form-control" placeholder="Write new message here" style="resize:vertical;"></textarea>
                </li>
                <li class="list-group-item">
                    <a id="btn-send-message" type="button" style="float: right; cursor: pointer;">Enter - send message</a>
                    <div class="clearfix"></div>
                </li>
            </ul>
        </div>
    </div>
</div>
<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function(){
        var pollingConversationInterval;
        function startLongPollingConversation(conversationUuid){
            pollingConversationInterval = setInterval(function(){
                loadAndRenderConversationMessages(conversationUuid)
            }, 3000);
        }

        var conversationMessages = "";
        function loadAndRenderConversationMessages(conversationUuid, now) {
            $.ajax({
                type: "post",
                url: "/messages/getConversationMessages",
                cache: false,
                data: "conversationUuid=" + conversationUuid,
                success: function (response) {
                    if (now) {
                        renderConversationMessages(response);
                    } else if (conversationMessages.length != response.length) {
                        renderConversationMessages(response);
                        conversationMessages = response;
                    }
                },
                error: function (response) {
                    //TODO обработка ошибок
                    alert("error");
                }
            });
        }

        function renderConversationMessages(messages) {
            $("#new-message-input-form").hide();
            var messagesDialog = $("#messages-dialog");
            messagesDialog.empty();

            messages.forEach(function(entry) {
                var html =
                        '<li tabindex="1">' +
                            '<div class="list-group">' +
                                '<a class="list-group-item" style="cursor: pointer">' +
                                    '<div class="row">' +
                                        '<div class="col-xs-1">' +
                                            '<img height="50" src="/file/get/' + entry.smartUser.file.id + '">' +
                                        '</div>' +
                                        '<div class="col-xs-9">' +
                                            '<p class="list-group-item-heading">' +
                                                entry.smartUser.username +
                                            '</p>' +
                                            '<p class="list-group-item-text">' +
                                                entry.message +
                                            '</p>' +
                                        '</div>'  +
                                        '<div class="col-xs-2">' +
                                            new Date(entry.date).customFormat("#DD#.#MM#.#YYYY#") +
                                        '</div>' +
                                    '</div>' +
                                '</a>' +
                            '</div>' +
                        '</li>';

                messagesDialog.append(html);
                $('#messages-dialog li').last().addClass('active-li').focus();
            });
        }

        $(".conversation").click(function() {
            var conversationUuid = $(this).data("conversation-uuid");
            $("#messages-title").text($(this).data("conversation-username"));
            $("#conversation-message").attr("data-conversation-uuid", conversationUuid);
            loadAndRenderConversationMessages(conversationUuid, true);
            clearInterval(pollingConversationInterval);
            startLongPollingConversation(conversationUuid);
        });

        $("#btn-new-message").click(function() {
            clearInterval(pollingConversationInterval);
            $("#messages-title").text("New Message");
            $("#messages-dialog").empty();
            $("#new-message-input-form").show();
        });

        $("#btn-send-message").click(function() {
            // send to user
            if ($("#messages-dialog").children().length > 0) {
                var conversationUuid = $("#conversation-message").data("conversation-uuid");
                $.ajax({
                    type: "post",
                    url: "/messages/sendMessageToUser",
                    cache: false,
                    data: "message=" + $("#input-new-message").val()
                            + "&conversation-uuid=" + conversationUuid,
                    success: function (response) {
                        $("#input-new-message").val("");
                        loadAndRenderConversationMessages(conversationUuid);
                    },
                    error: function (response) {
                        //TODO обработка ошибок
                        alert("error");
                    }
                });
            } else {
                // TODO username брать из data-username у инпута после зполненения этого аттрибута автокомплитом
                $.ajax({
                    type: "post",
                    url: "/messages/createNewConversation",
                    cache: false,
                    data: "message=" + $("#input-new-message").val()
                            + "&username=" + $("#input-new-conversation-recipient").val(),
                    success: function (response) {
                        alert("OK")
                    },
                    error: function (response) {
                        //TODO обработка ошибок
                        alert("error");
                    }
                });
            }
        });
    });
</script>

