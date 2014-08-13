<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<fmt:requestEncoding value="utf-8"/>

<jsp:include page="../template/top.jsp"/>

<div class="well well-lg">
    <form:form method="POST" commandName="registerSmartUserDTO" action="/signup/register.do" cssClass="form-horizontal">
        <div class="form-group has-feedback">
            <label for="username" class="col-sm-2 control-label">
                <spring:message code="label.username"/>
                <span class="required">*</span>
            </label>

            <div class="col-xs-4">
                <form:input id="username" path="username" placeholder="Username" class="form-control"/>
                <form:errors path="username" cssClass="error"/>
                <span id="username-ok-icon" class="glyphicon glyphicon-ok form-control-feedback"
                      style="display: none;"></span>
                <span id="username-not-ok-icon" class="glyphicon glyphicon-remove form-control-feedback"
                      style="display: none;"></span>
                <span id="username-input-error" class="required" style="display: none;">
                    <spring:message code="label.username_input_length_error"/>
                </span>
                <span id="username-busy-error" class="required" style="display: none;">
                    <spring:message code="label.username_busy_error"/>
                </span>
            </div>
            <span id="loading-username" class="loading"></span>
        </div>
        <div class="form-group has-feedback">
            <label for="email" class="col-sm-2 control-label">
                <spring:message code="label.email"/>
                <span class="required">*</span>
            </label>

            <div class="col-xs-4">
                <form:input id="email" path="email" placeholder="Email" class="form-control"/>
                <form:errors path="email" cssClass="error"/>
                <span id="email-ok-icon" class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
                <span id="email-not-ok-icon" class="glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
                <span id="email-input-error" class="required" style="display: none;">
                    <spring:message code="label.email_input_error"/>
                </span>
                <span id="email-busy-error" class="required" style="display: none;">
                    <spring:message code="label.email_busy_error"/>
                </span>
            </div>
            <span id="loading-email" class="loading"></span>
        </div>
        <div class="form-group has-feedback">
            <label for="password" class="col-sm-2 control-label"><spring:message code="label.password"/>
                <span class="required">*</span>
            </label>

            <div class="col-xs-4">
                <form:input id="password" path="password" type="password" class="form-control"/>
                <form:errors path="password" cssClass="error"/>
            </div>
        </div>
        <div class="form-group has-feedback">
            <label for="firstName" class="col-sm-2 control-label">
                <spring:message code="label.firstname"/>
                <span class="required">*</span>
            </label>

            <div class="col-xs-4">
                <form:input id="firstName" path="firstName" type="text" name="firstName" class="form-control"/>
                <form:errors path="firstName" cssClass="error"/>
            </div>
        </div>
        <div class="form-group">
            <label for="lastName" class="col-sm-2 control-label">
                <spring:message code="label.lastname"/>
            </label>

            <div class="col-xs-4">
                <form:input id="lastName" path="lastName" type="text" name="lastName" class="form-control"/>
                <form:errors path="lastName" cssClass="error"/>
            </div>
        </div>
        <div class="form-group has-feedback">
            <label for="city" class="col-sm-2 control-label">
                <spring:message code="label.city"/>
                <span class="required">*</span>
            </label>

            <div class="col-xs-4">
                <form:input id="city" path="city" type="text" class="form-control"/>
                <form:errors path="city" cssClass="error"/>
                <div id="kladr_autocomplete"></div>
            </div>
        </div>
        <div class="submit col-sm-offset-2 col-sm-10">
            <input id="sign-up-btn" class="btn btn-default" type="submit" value="<spring:message code="label.signup"/>">
            <span id="loading-sign-up" class="loading"></span>
        </div>
        <div class="clearfix"></div>
    </form:form>
</div>

<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function () {
        var usernameOkSpan = $("#username-ok-icon");
        var usernameNotOkSpan = $("#username-not-ok-icon");
        var emailOkSpan = $("#email-ok-icon");
        var emailNotOkSpan = $("#email-not-ok-icon");

        var usernameInputErrorObj = $("#username-input-error");
        var usernameBusyErrorObj = $("#username-busy-error");
        var emailInputErrorObj = $("#email-input-error");
        var emailBusyErrorObj = $("#email-busy-error");

        var usernameObj = $("#username");
        var emailObj = $("#email");
        var usernameForm = usernameObj.parent().parent();
        var emailForm = emailObj.parent().parent();

        usernameObj.on('focusout', function (e) {
            var ajaxLoadingUsername = $("#loading-username");
            ajaxLoadingUsername.loading('start');

            $.ajax({
                type: "get",
                url: "/signup/checkLogin",
                cache: false,
                data: "login=" + $('#username').val(),
                success: function (response) {
                    if (response.message) {
                        usernameForm.removeClass("has-error");
                        usernameForm.addClass("has-success");
                        usernameOkSpan.show();
                        usernameInputErrorObj.hide();
                        usernameBusyErrorObj.hide();
                        usernameNotOkSpan.hide();

                        ajaxLoadingUsername.loading('stop');
                    } else {
                        usernameForm.removeClass("has-success");
                        usernameForm.addClass("has-error");
                        usernameNotOkSpan.show();
                        usernameBusyErrorObj.show();
                        usernameOkSpan.hide();

                        ajaxLoadingUsername.loading('stop');
                    }
                },
                error: function (response) {
                    alert("error");
                }
            });
        });

        emailObj.on('focusout', function (e) {
            var emailLoading = $("#loading-email");
            emailLoading.loading('start');

            $.ajax({
                type: "get",
                url: "/signup/checkEmail",
                cache: false,
                data: "email=" + $('#email').val(),
                success: function (response) {
                    if (response.message) {
                        emailForm.removeClass("has-error");
                        emailForm.addClass("has-success");
                        emailOkSpan.show();

                        emailNotOkSpan.hide();
                        emailInputErrorObj.hide();
                        emailBusyErrorObj.hide();

                        emailLoading.loading('stop');
                    } else {
                        emailForm.removeClass("has-success");
                        emailForm.addClass("has-error");

                        emailBusyErrorObj.show();
                        emailNotOkSpan.show();
                        emailOkSpan.hide();

                        emailLoading.loading('stop');
                    }
                },
                error: function (response) {
                    alert("error");
                }
            });
        });

        (function($){
            $(function() {
                $('#city').kladr({
                    token: '533bdf64dba5c74349000000',
                    key: '11a0052fed5a99278a594ca8ded998b160aeef7b',
                    type: $.kladr.type.city,
                    labelFormat: function( obj, query) {
                        console.log(query);
                        console.log(obj);
                        return obj.type + " " + obj.name +  (obj.zip == null ? "" : ", Индекс: " + obj.zip);
                    }
                });

                /*            // Автодополнение населённых пунктов
                 $( '[name="location"]' ).kladr({
                 token: '533bdf64dba5c74349000000',
                 key: '11a0052fed5a99278a594ca8ded998b160aeef7b',
                 type: $.kladr.type.city,
                 select: function( obj ) {
                 // Изменения родительского объекта для автодополнения улиц
                 $( '[name="street"]' ).kladr('parentId', obj.id);
                 }
                 });*/

                /*            // Автодополнение улиц
                 $( '[name="street"]' ).kladr({
                 token: '533bdf64dba5c74349000000',
                 key: '11a0052fed5a99278a594ca8ded998b160aeef7b',
                 type: $.kladr.type.street,
                 parentType: $.kladr.type.city
                 });*/
            });
        })(jQuery);
    });
</script>