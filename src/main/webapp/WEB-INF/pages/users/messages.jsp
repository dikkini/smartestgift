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
                                    <c:when test="${conversation.pk.userFrom.uuid eq smartUser.uuid}">
                                        <c:set var="fromUserConversation" value="${conversation.pk.userTo}" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="fromUserConversation" value="${conversation.pk.userFrom}" />
                                    </c:otherwise>
                                </c:choose>

                                <li>
                                    <div class="list-group">
                                        <a href="#" data-user-uuid="${fromUserConversation.uuid}" class="list-group-item conversation">
                                            <div class="row">
                                                <div class="col-xs-3">
                                                    <img height="50" src="/file/get/${fromUserConversation.file.id}">
                                                </div>
                                                <div class="col-xs-9">
                                                    <p class="list-group-item-heading">${fromUserConversation.username}</p>
                                                    <p class="list-group-item-text ellipses">${conversation.lastMessage.message}</p>
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
        <strong>User Nickname</strong>
        <a style="margin-left: 20px" class="btn btn-primary">New Message</a>
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
        <div class="messages-dialog">

        </div>
    </div>
</div>
<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function(){
        $(".conversation").click(function() {
            $.ajax({
                type: "post",
                url: "/messages/getUserMessages",
                cache: false,
                data: "useruuid=" + $(this).data("user-uuid"),
                success: function (response) {
                    response.forEach(function(entry) {
                        console.log(entry);
                    });
                },
                error: function (response) {
                    alert(response);
                    window.location = "500";
                }
            });
        });
    });
</script>

