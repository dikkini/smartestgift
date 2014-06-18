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
<%--        <ul class="nav navbar-nav">
            <li class="active"><a href="#">Inbox <span class="badge"></span></a></li>
            <li><a href="#">Sent</a></li>
        </ul>--%>
        <div class="col-lg-9">
            <div class="input-group">
                <input id="find-conversation-input" type="text" class="form-control" placeholder="Find conversations">
              <span class="input-group-btn">
                <button id="find-conversation-btn" style="margin-bottom: 20px" class="btn btn-default" type="button">Search</button>
              </span>
            </div>
            <div>
                <ul id="conversations" class="nav nav-pills nav-stacked">
                    <c:choose>
                        <c:when test="${fn:length(userConversations) == 0}">
                            <p id="no-conversations"> You have no conversations now.</p>
                        </c:when>
                        <c:otherwise>
                             <c:forEach items="${userConversations}" var="conversation">
                                 <c:choose>
                                     <c:when test="${conversation.user_from == smartUser}">
                                         <c:set var="userToConversation" value="${conversation.user_to}" />
                                     </c:when>
                                     <c:otherwise>
                                         <c:set var="userToConversation" value="${conversation.user_from}" />
                                     </c:otherwise>
                                 </c:choose>
                                <span id="loading-conversations" class="loading" style=""></span>
                                <li>
                                    <div class="list-group">
                                        <a data-conversation-username="${userToConversation.username}" data-conversation-uuid="${conversation.uuid}" class="list-group-item conversation" style="cursor: pointer;">
                                            <div class="row">
                                                <div class="col-xs-3">
                                                    <img height="50" src="/file/get/${userToConversation.file.id}">
                                                </div>
                                                <div class="col-xs-9">
                                                    <p class="list-group-unread-messages-count" style="float: right"></p>
                                                    <p class="list-group-item-heading">${userToConversation.username}</p>
                                                    <p class="list-group-item-text ellipses">todo last message</p>
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
            <ul class="list-group">
                <li id="new-message-input-form" class="list-group-item">
                    <input id="input-new-conversation-contact" data-username="" type="text" class="form-control" placeholder="Username or First name">
                </li>
                <li class="list-group-item">
                    <div id="people-and-messages">
                        <span id="loading-people-and-messages" class="loading" style=""></span>
                        <ul style="margin-bottom: 20px; max-height: 250px; overflow: auto;" id="messages-and-people" class="nav nav-pills nav-stacked">
                            <%--messages here--%>
                        </ul>
                        <span id="loading-messages" class="loading" style=""></span>
                    </div>
                </li>
                <li class="list-group-item">
                    <textarea id="message-input" class="form-control" placeholder="Write new message here" style="resize:vertical;"></textarea>
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

        var ajaxPeopleLoading = null;
        var timeoutLoadingConversations = null;
        var newMessageCondition = true;

        $(".loading").loading({width: '25', text: 'Waiting...'});

        var socket = new SockJS('/messages');
        var stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {
            stompClient.subscribe('/user/${smartUser.username}/getNewConversationMessages', function(response) {
                var body = JSON.parse(response.body);
                var messages = JSON.parse(body);
                renderConversationMessages(messages, false);
            });
        });

        // TODO когда загружаешь все сообщения, срабатывает асинхронный код добавляющий новые сообщения также.
        $("#conversations").on("click", ".conversation", function() {
            $("#loading-messages").loading("start");

            newMessageCondition = false;
            $("#message-input").val("");
            $("#new-message-input-form").hide();
            stopNewMessageSchedule();
            $(this).find(".list-group-unread-messages-count").text("");
            var conversationUuid = $(this).data("conversation-uuid");
            $("#messages-title").text($(this).data("conversation-username"));
            $("#people-and-messages").data("conversation-uuid", conversationUuid);
            loadAndRenderAllConversationMessages(conversationUuid);
        });

        $("#btn-new-message").click(function() {
            newMessageCondition = true;
            var inputConversationContact = $("#input-new-conversation-contact");
            $("#people-and-messages").data("conversation-uuid", "");
            $("#message-input").val("");
            stopNewMessageSchedule();
            $("#messages-title").text("New Message");
            $("#messages-and-people").empty();
            $("#new-message-input-form").show();
            inputConversationContact.data("username", "");
            inputConversationContact.val("");
        });

        $("#message-input").on('keypress', function(e) {
            // enter key
            if (e.which !== 13) {
                return;
            }
            if (checkContactAndMessageBeforeSend()) {
                sendMessageOrStartNewConversation();
            }
            e.preventDefault();
        });

        $("#btn-send-message").click(function(e) {
            if (checkContactAndMessageBeforeSend()) {
                sendMessageOrStartNewConversation();
            }
        });

        $("#input-new-conversation-contact").on("keyup", function(e) {
            if (ajaxPeopleLoading) {
                clearTimeout(ajaxPeopleLoading);
            }
            var userInput = $(this).val();
            var messageDialogObj = $("#messages-and-people");
            messageDialogObj.html("");
            if (userInput.trim() == "") {
                return;
            }

            $("#loading-people-and-messages").loading("start");
            ajaxPeopleLoading = setTimeout(getContactsByUserTyping(userInput), 1000);
        });

        $("#messages-and-people").on("click", ".contact", function() {
            var inputMessage = $("#input-new-conversation-contact");
            $("#messages-and-people").html("");
            var tag = $(this).data("fio");
            inputMessage.data("username",$(this).data("username"));
            inputMessage.val(tag);
        });

        $("#find-conversation-input").on("keyup", function(e) {
            if (timeoutLoadingConversations) {
                clearTimeout(timeoutLoadingConversations);
            }
            var ajaxLoadingConversations = $("#loading-conversations");
            var searchString = $(this).val();
            var conversations = $("#conversations").find(".conversation");

            if (searchString.trim() == "") {
                conversations.each(function() {
                    $(this).show();
                });
            }

            timeoutLoadingConversations = setTimeout(function(){
                ajaxLoadingConversations.loading("start");
                conversations.each(function() {
                    var conversationName = $(this).data("conversation-username");
                    var index = conversationName.indexOf(searchString);
                    if (index == -1) {
                        $(this).hide();
                    } else if ($(this).css('display') == 'none') {
                        $(this).show();
                    }
                });
                ajaxLoadingConversations.loading("stop");
            }, 500);
        });


        function checkContactAndMessageBeforeSend() {
            var messageInput = $("#message-input");
            var contactInput = $("#input-new-conversation-contact");
            if (newMessageCondition) {
                var contactInputValue = contactInput.val();
                var contactDataUsername = contactInput.data("username");
                if (contactDataUsername.trim() == "" || contactInputValue.trim() == "") {
                    // TODO
                    alert("choose contact");
                    return false;
                }
            }
            if (messageInput.val().trim() == "") {
                // TODO
                alert("enter message");
                return false;
            }

            return true;
        }

        function sendMessageOrStartNewConversation() {
            $("#loading-messages").loading("start");
            var messageInput = $("#message-input");
            var conversationUuid = $("#people-and-messages").data("conversation-uuid");

            if (newMessageCondition && (conversationUuid == "" || conversationUuid == undefined)) {
                var contactInputUsername= $("#input-new-conversation-contact").data("username");
                var sent = false;
                $(".conversation").each(function() {
                    var conversation = $(this);
                    var conversationUsername = conversation.data("conversation-username");
                    conversationUuid = conversation.data("conversation-uuid");
                    if (conversationUsername == contactInputUsername) {
                        sent = true;
                        sendMessageToUser(messageInput.val(), conversationUuid, true);
                    }
                });
                if (!sent) {
                    startNewConversationWithUser();
                }
            } else {
                sendMessageToUser(messageInput.val(), conversationUuid, false);
            }
            $("#no-conversations").remove();
        }

        function startNewConversationWithUser() {
            var inputNewMessageObj = $("#message-input");
            $.ajax({
                type: "post",
                url: "/messages/createNewConversation",
                cache: false,
                data: "message=" + inputNewMessageObj.val()
                        + "&username=" + $("#input-new-conversation-contact").data("username"),
                success: function (response) {
                    $("#new-message-input-form").hide();
                    var html =
                            '<li>' +
                                    '<div class="list-group">' +
                                    '<a data-conversation-username="'+ response.user_to.username +'" data-conversation-uuid="'+ response.uuid + '" class="list-group-item conversation" style="cursor: pointer;">' +
                                    '<div class="row">' +
                                    '<div class="col-xs-3">' +
                                    '<img height="50" src="/file/get/'+response.user_to.file.id + '">' +
                                    '</div>' +
                                    '<div class="col-xs-9">' +
                                    '<p class="list-group-unread-messages-count" style="float: right"/>' +
                                    '<p class="list-group-item-heading">'+ response.user_to.username + '</p>' +
                                    '<p class="list-group-item-text ellipses">message?</p>' +
                                    '</div>' +
                                    '</div>' +
                                    '</a>' +
                                    '</div>' +
                                    '</li>';
                    $("#messages-title").text(response.user_to.username);
                    $("#conversations").append(html);
                    loadAndRenderAllConversationMessages(response.uuid);
                    inputNewMessageObj.val("");
                    $("#loading-messages").loading("stop");
                },
                error: function (response) {
                    //TODO обработка ошибок
                    alert("error");
                }
            });
        }

        function sendMessageToUser(message, conversationUuid, fromNewMessage) {
            var messageDialogObj = $("#messages-and-people");
            var inputNewMessageObj = $("#message-input");
            $.ajax({
                type: "post",
                url: "/messages/sendMessageToUser",
                cache: false,
                data: "message=" + message + "&conversation-uuid=" + conversationUuid,
                success: function (response) {
                    if (fromNewMessage) {
                        $(".conversation[data-conversation-uuid=" + conversationUuid + "]").click();
                    } else {
                        var html =
                                '<li tabindex="1">' +
                                        '<div class="list-group">' +
                                        '<a class="list-group-item" style="cursor: pointer">' +
                                        '<div class="row">' +
                                        '<div class="col-xs-1">' +
                                        '<img height="50" src="/file/get/' + '${smartUser.file.id}' + '">' +
                                        '</div>' +
                                        '<div class="col-xs-9">' +
                                        '<p class="list-group-item-heading">' +
                                        '${smartUser.username}' +
                                        '</p>' +
                                        '<p class="list-group-item-text">' +
                                        message +
                                        '</p>' +
                                        '</div>'  +
                                        '<div class="col-xs-2">' +
                                        new Date().customFormat("#DD#.#MM#.#YYYY#") +
                                        '</div>' +
                                        '</div>' +
                                        '</a>' +
                                        '</div>' +
                                        '</li>';

                        inputNewMessageObj.val("");
                        messageDialogObj.append(html);
                        messageDialogObj.find('li').last().addClass('active-li').focus();
                        inputNewMessageObj.focus();
                        $("#loading-messages").loading("stop");
                    }
                },
                error: function (response) {
                    //TODO обработка ошибок
                    alert("error");
                }
            });
        }

        function stopNewMessageSchedule() {
            $.ajax({
                async: false, // Important - this makes this a blocking call
                url: '/messages/stopNewMessagesSchedulers',
                type: 'post',
                data: {}
            });
        }

        function loadAndRenderAllConversationMessages(conversationUuid) {
            $.ajax({
                type: "post",
                url: "/messages/getConversationMessages",
                cache: false,
                data: "conversationUuid=" + conversationUuid,
                dataType: "json",
                success: function (response) {
                    response = JSON.parse(response);
                    renderConversationMessages(response, true);

                    setTimeout(function() {
                        var jsonstr = JSON.stringify({ 'param': conversationUuid});
                        stompClient.send("/app/setConversation", {}, jsonstr);
                    }, 3000)
                },
                error: function (response) {
                    //TODO обработка ошибок
                    alert("error");
                }
            });
        }

        function renderConversationMessages(messages, isEmpty) {
            var messagesDialog = $("#messages-and-people");
            if (isEmpty) {
                messagesDialog.empty();
            }

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
                messagesDialog.find('li').last().addClass('active-li').focus();
            });
            $("#loading-messages").loading("stop");
        }

        function getContactsByUserTyping(userInput) {
            $.ajax({
                type: "post",
                url: "/searchPeople",
                cache: false,
                data: "searchPeopleStr=" + userInput,
                success: function (response) {
                    var messageDialogObj = $("#messages-and-people");
                    messageDialogObj.html("");

                    JSON.parse(response).forEach(function(entry) {
                        var html =
                                '<li data-username="' + entry.username + '"class="contact" tabindex="1" data-fio="' + (entry.lastName ? entry.lastName + " " : "") + entry.firstName + (entry.middleName ? " " + entry.middleName : "") + '">' +
                                        '<div class="list-group">' +
                                        '<a class="list-group-item" style="cursor: pointer">' +
                                        '<div class="row">' +
                                        '<div class="col-xs-1">' +
                                        '<img height="50" src="/file/get/' + entry.file.id + '">' +
                                        '</div>' +
                                        '<div class="col-xs-9">' +
                                        '<p class="list-group-item-heading">' +
                                        (entry.lastName ? entry.lastName + " " : "") + entry.firstName + (entry.middleName ? " " + entry.middleName : "")  +
                                        '</p>' +
                                        '<p class="list-group-item-text">' +
                                        entry.username +
                                        '</p>' +
                                        '</div>'  +
                                        '</div>' +
                                        '</a>' +
                                        '</div>' +
                                        '</li>';

                        messageDialogObj.append(html);
                    });
                    $("#loading-people-and-messages").loading("stop");
                },
                error: function (response) {
                    //TODO обработка ошибок
                    $("#loading-people-and-messages").loading("stop");
                    alert("error");
                }
            });
        }

        window.onbeforeunload = function() {
            stopNewMessageSchedule();
        };
    });
</script>

