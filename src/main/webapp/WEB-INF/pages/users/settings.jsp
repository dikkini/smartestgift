<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<jsp:useBean id="smartUser" class="com.smartestgift.dao.model.SmartUser" scope="request"/>

<jsp:include page="../template/top.jsp"/>

<div class="row">
    <div class="col-xs-3">
        <fieldset style="height: 359px" class="user-photo">
            <legend><spring:message code="label.photo"/></legend>
            <div class="form-group">
                <img id="user-photo-img" height="200" src="/file/get/${smartUser.file.id}" style="align-self: center; margin-bottom: 20px;">
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
        <form class="form-horizontal login-form" action="/profile/settings/save" method="post">
            <fieldset class="personal-information">
                <legend><spring:message code="label.personalinfo"/></legend>
                <div class="form-group">
                    <label for="input-lastname" class="col-sm-4 control-label"><spring:message code="label.lastname"/></label>
                    <div class="col-xs-4">
                        <input id="input-lastname" name="lastName" type="text" class="form-control" placeholder="<spring:message code="label.lastname"/>" value="${smartUser.lastName}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-firstname" class="col-sm-4 control-label"><spring:message code="label.firstname"/></label>
                    <div class="col-xs-4">
                        <input id="input-firstname" name="firstName" type="text" class="form-control" placeholder="<spring:message code="label.firstname"/>" value="${smartUser.firstName}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-middlename" class="col-sm-4 control-label"><spring:message code="label.middlename"/></label>
                    <div class="col-xs-4">
                        <input id="input-middlename" name="middleName" type="text" class="form-control" placeholder="<spring:message code="label.middlename"/>" value="${smartUser.middleName}">
                    </div>
                </div>
                <div class="form-group">
                <label for="input-address" class="col-sm-4 control-label"><spring:message code="label.address"/></label>
                    <div class="col-xs-4">
                        <input id="input-address" name="address" type="text" class="form-control" placeholder="<spring:message code="label.address"/>" value="${smartUser.address}">
                    </div>
                    <div class="col-xs-4">
                        <div class="checkbox">
                            <input id="input-address-visible" name="addressVisible" type="checkbox"  <c:if test="${smartUser.addressVisible}">checked</c:if> > <spring:message code="label.showtoall"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-cellphone" class="col-sm-4 control-label"><spring:message code="label.mobilephone"/></label>
                    <div class="col-xs-4">
                        <input id="input-cellphone" name="cellphone" type="tel" class="form-control" placeholder="<spring:message code="label.mobilephone"/>" value="${smartUser.cellPhone}"  >
                    </div>
                    <div class="col-xs-4">
                        <div class="checkbox">
                            <input id="input-cellphone-visible"  type="checkbox" name="cellphoneVisible" <c:if test="${smartUser.cellPhoneVisible}">checked</c:if> > <spring:message code="label.showtoall"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-birthdate" class="col-sm-4 control-label">Birth Date</label>
                    <div class="col-xs-4">
                        <input id="input-birthdate" name="birthdate" type="date" class="form-control" placeholder="<spring:message code="label.birthdate"/>" value="<fmt:formatDate value="${smartUser.birthDate}" pattern="yyyy-MM-dd" />">
                    </div>
                </div>

            </fieldset>
            <fieldset class="private-settings">
                <legend><spring:message code="label.privatesettings"/></legend>
                <div class="form-group">
                    <label for="input-profile-visible" class="col-sm-4 control-label"><spring:message code="label.publicprofile"/></label>
                    <div class="col-xs-4">
                        <div class="checkbox">
                            <input id="input-profile-visible" name="profileVisible" type="checkbox" <c:if test="${smartUser.profileVisible}">checked</c:if> >
                        </div>
                    </div>
                </div>
            </fieldset>
            <div class="row">
                <div class="col-xs-6"></div>
                <div class="col-xs-2">
                    <div class="col-xs-1">
                        <input type="submit" value="Save" class="btn btn-default">
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>


<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function () {

        $(".loading").loading({width: '25', text: 'Waiting...'});

        $("#inputTypeFile").fileupload({
            url: '/file/uploadUserPhoto',
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

        $('.date').pickmeup({
            format: 'm.d.Y'
        });
    });
</script>