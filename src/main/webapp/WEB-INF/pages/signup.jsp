<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<jsp:include page="template/top.jsp"/>

<div class="container">
    <div class="alert alert-danger alert-dismissable" hidden>
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <p class="erros">

        </p>
    </div>
    <div class="well well-lg">
        <div>
            <form class="form-horizontal">
                <div class="form-group">
                    <label for="username" class="col-sm-2 control-label"><spring:message code="label.username"/><span class="required">*</span>
                    </label>
                    <div class="col-xs-4">
                        <input type="text" name="username" class="form-control" id="username" placeholder="<spring:message code="label.username"/>">
                    </div>
                    <span id="loading-username" class="loading"></span>
                    <img id="username-status" height="25" src="" hidden>
                </div>
                <div class="form-group">
                    <label for="firstname" class="col-sm-2 control-label"><spring:message code="label.firstname"/><span class="required">*</span>
                    </label>
                    <div class="col-xs-4">
                        <input type="text" name="firstName" class="form-control" id="firstname" placeholder="<spring:message code="label.firstname"/>">
                    </div>
                </div>
                <div class="form-group">
                    <label for="lastname" class="col-sm-2 control-label"><spring:message code="label.lastname"/></label>
                    <div class="col-xs-4">
                        <input type="text" name="lastName" class="form-control" id="lastname" placeholder="<spring:message code="label.lastname"/>">
                    </div>
                </div>
                <div class="form-group">
                    <label for="email" class="col-sm-2 control-label"><spring:message code="label.email"/><span class="required">*</span>
                    </label>
                    <div class="col-xs-4">
                        <input type="email" name="email" class="form-control" id="email" placeholder="<spring:message code="label.email"/>">
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label"><spring:message code="label.password"/><span class="required">*</span>
                    </label>
                    <div class="col-xs-4">
                        <input type="password" name="password" class="form-control" id="password" placeholder="<spring:message code="label.password"/>">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button id="sign-up-btn" class="btn btn-default"><spring:message code="label.signup"/></button>
                        <span id="loading-sign-up" class="loading"></span>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function() {
        $(".loading").loading({width:'25', text: 'Waiting...'});

        $("#username").on('keyup focusout', function (e) {
            $("#loading-username").loading('start');
            $.ajax({
                type: "post",
                url: "/signup/checkLogin",
                cache: false,
                data: "login=" + $('#username').val(),
                success: function (response) {
                    response = JSON.parse(response);
                    var imageSelector = $("#username-status");
                    if (response.loginFree) {
                        imageSelector.attr("src", "/resources/ext/main/images/ok.png");
                    } else {
                        imageSelector.attr("src", "/resources/ext/main/images/not_ok.png");
                    }
                    $("#loading-username").loading('stop');
                    imageSelector.show();
                },
                error: function (response) {
                    response = JSON.parse(response);
                    alert(response.error);
                }
            });
        });

        $("#sign-up-btn").click(function() {
            $("#loading-sign-up").loading('start');

            var data = {};
            $('input').each(function() {
                data[$(this).attr('name')] = $(this).val();
            });

            $.ajax({
                type: "post",
                url: "/signup/register",
                cache: false,
                data: data,
                success: function (response) {
                    if (response.status) {
                        window.location = "/profile";
                    } else {

                    }
                },
                error: function (response) {
                    alert(response.error);
                }
            });
        });
    });
</script>