<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<fmt:requestEncoding value="utf-8" />

<jsp:include page="../template/top.jsp"/>

<div class="row">
    <div class="col-xs-3">
        <fieldset class="user-photo">
            <legend><spring:message code="label.photo"/></legend>
            <div class="form-group">
                <img id="user-photo-img" width="200" height="200" src="/file/get/<%=request.getSession().getAttribute("userImageId")%>" style="align-self: center; margin-bottom: 20px;">
                <span class="btn btn-success fileinput-button">
                    <i class="glyphicon glyphicon-plus"></i>
                    <span>Choose Photo</span>
                    <input id="inputTypeFile" type="file" name="inputTypeFile">
                </span>
                <span id="loading-file" class="loading"></span>
            </div>
        </fieldset>
    </div>
    <div class="col-xs-9">
        <form:form method="POST" commandName="settingsSmartUserDTO" action="/profile/settings/save.do" cssClass="form-horizontal login-form">
            <fieldset class="personal-information">
                <legend><spring:message code="label.personalinfo"/></legend>
                <div class="form-group">
                    <label for="lastName" class="col-sm-4 control-label"><spring:message code="label.lastname"/></label>
                    <div class="col-xs-4">
                        <form:input id="lastName" path="lastName" placeholder="LastName" class="form-control"/>
                        <form:errors path="lastName" cssClass="error"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="firstName" class="col-sm-4 control-label"><spring:message code="label.firstname"/></label>
                    <div class="col-xs-4">
                        <form:input id="firstName" path="firstName" placeholder="First Name" class="form-control"/>
                        <form:errors path="firstName" cssClass="error"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="middleName" class="col-sm-4 control-label"><spring:message code="label.middlename"/></label>
                    <div class="col-xs-4">
                        <form:input id="middleName" path="middleName" placeholder="MiddleName" class="form-control"/>
                        <form:errors path="middleName" cssClass="error"/>
                    </div>
                </div>
                <div class="form-group">
                <label for="address" class="col-sm-4 control-label"><spring:message code="label.address"/></label>
                    <div class="col-xs-4">
                        <form:input id="address" path="address" placeholder="Address" class="form-control"/>
                        <form:errors path="address" cssClass="error"/>
                    </div>
                    <div class="col-xs-4">
                        <div class="checkbox">
                            <form:checkbox id="addressVisible" path="addressVisible" placeholder="addressVisible" class="form-control"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="cellPhone" class="col-sm-4 control-label"><spring:message code="label.mobilephone"/></label>
                    <div class="col-xs-4">
                        <form:input id="cellPhone" path="cellPhone" placeholder="CellPhone" class="form-control"/>
                        <form:errors path="cellPhone" cssClass="error"/>
                    </div>
                    <div class="col-xs-4">
                        <div class="checkbox">
                            <form:checkbox id="cellphoneVisible" path="cellphoneVisible" placeholder="CellphoneVisible" class="form-control"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="birthDate" class="col-sm-4 control-label">Birth Date</label>
                    <div class="col-xs-4">
                        <form:input id="birthDate" name="birthdate" path="birthDate" placeholder="BirthDate" class="form-control"/>
                        <form:errors path="birthDate" cssClass="error"/>
                    </div>
                </div>
            </fieldset>
            <fieldset class="private-settings">
                <legend><spring:message code="label.privatesettings"/></legend>
                <div class="form-group">
                    <label for="profileVisible" class="col-sm-4 control-label"><spring:message code="label.publicprofile"/></label>
                    <div class="col-xs-4">
                        <div class="checkbox">
                            <form:checkbox id="profileVisible" path="profileVisible" placeholder="ProfileVisible" class="form-control"/>
                        </div>
                    </div>
                </div>
            </fieldset>
            <div class="row">
                <div class="col-xs-6"></div>
                <div class="col-xs-2">
                    <div class="col-xs-1">
                        <button type="submit" class="btn btn-default">Save</button>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</div>


<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function () {

//        $("#save-settings-btn").click(function(e) {
//            var data = {};
//            $('input').each(function () {
//                data[$(this).attr('name')] = $(this).val();
//            });
//
//            $.ajax({
//                type: "post",
//                url: "/profile/settings/save.do",
//                cache: false,
//                data: data,
//                success: function (response) {
//                    if (response.message) {
//                        window.location = "/profile?from=settings&action=save";
//                    }
//                },
//                error: function (response) {
//                    alert("error");
//                    alert(response.responseText);
//                }
//            });
//            e.preventDefault();
//        });

        $("#birthDate").datepicker({
            changeMonth: true,
            changeYear: true,
            dateFormat: "${constants.JSP_INPUT_DATE_FORMAT_PATTERN}"
        });

        $(".loading").loading({width: '25', text: 'Waiting...'});

        $("#inputTypeFile").fileupload({
            url: '/file/uploadUserPhoto',
            formData: {
                'fileTypeId': ${constants.USER_IMAGE_FILE_TYPE_ID}
            },
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
            maxFileSize: 5000000, // 5 MB
            // Enable image resizing, except for Android and Opera,
            // which actually support image resizing, but fail to
            // send Blob objects via XHR requests:
            disableImageResize: /Android(?!.*Chrome)|Opera/
                    .test(window.navigator.userAgent),
            sequentialUploads: true,
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $("#loading-file").find("span").text('Uploading.. ' + progress + '%');
            },
            add: function (e, data) {
                var fileLoading = $("#loading-file");
                fileLoading.find("span").text('Uploading...');
                fileLoading.loading('start');
                data.context = fileLoading;
                data.submit();
            },
            done: function (e, data) {
                var imgSrc = '/file/get/' + data.result.id;
                data.context.loading('stop');
                $('#user-photo-img').attr('src', imgSrc).load(function(){
                    $(this).width;   // Note: $(this).width() will not work for in memory images
                });
            }
        });
    });
</script>