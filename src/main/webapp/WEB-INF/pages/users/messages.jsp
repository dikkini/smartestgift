<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<jsp:useBean id="smartUser" class="com.smartestgift.dao.model.SmartUser" scope="request"/>

<jsp:include page="../template/top.jsp"/>

<div class="row">
    <div class="col-xs-4">
        <ul class="nav navbar-nav">
            <li class="active"><a href="#">Inbox</a></li>
            <li><a href="#">Sent</a></li>
        </ul>
        <div class="col-lg-9">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="Find conversations">
              <span class="input-group-btn">
                <button class="btn btn-default" type="button">Search</button>
              </span>
            </div>
            <div>
                <ul>
                    <li></li>
                    <li></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="col-xs-8">
        <strong>User Nickname</strong>
        <a class="btn btn-primary">New Message</a>
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
    </div>
</div>
<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function(){
        $(".un-want-gift-btn").click(function(e) {
            $.ajax({
                type: "post",
                url: "/gifts/unWantGift",
                cache: false,
                data: "giftuuid=" + $(this).data("gift-uuid"),
                success: function (response) {
                    window.location = $.updateNotifyBlockRequest(window.location.href, response.successes, response.errors, response.information);
                },
                error: function (response) {
                    window.location = "500";
                }
            });
            e.preventDefault();
        });
    });
</script>

