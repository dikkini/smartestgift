<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<fmt:requestEncoding value="utf-8" />

<jsp:useBean id="smartUser" class="com.smartestgift.dao.model.SmartUser" scope="request"/>

<jsp:include page="template/top.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-xs-3">
            <c:choose>
                <c:when test="${smartUser.file.id == null}">
                    <img height="250px" src=/resources/ext/main/images/no_photo.jpg>
                </c:when>
                <c:otherwise>
                    <img height="250px" src="/file/get/${smartUser.file.id}">
                </c:otherwise>
            </c:choose>
            <button type="button" class="btn btn-default">Choose Photo</button>
        </div>
        <div class="col-xs-9">
                <form class="form-horizontal login-form" action="/settings/save" method="post">
                    <fieldset class="personal-information">
                        <legend>Personal Information</legend>
                        <div class="form-group">
                            <label for="input-lastname" class="col-sm-4 control-label">Last Name</label>
                            <div class="col-xs-4">
                                <input id="input-lastname" name="lastName" type="text" class="form-control" placeholder="Last Name" value="${smartUser.lastName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="input-firstname" class="col-sm-4 control-label">First Name</label>
                            <div class="col-xs-4">
                                <input id="input-firstname" name="firstName" type="text" class="form-control" placeholder="First Name" value="${smartUser.firstName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="input-middlename" class="col-sm-4 control-label">Middle Name</label>
                            <div class="col-xs-4">
                                <input id="input-middlename" name="middleName" type="text" class="form-control" placeholder="Middle Name" value="${smartUser.middleName}">
                            </div>
                        </div>
                        <div class="form-group">
                        <label for="input-address" class="col-sm-4 control-label">Address</label>
                            <div class="col-xs-4">
                                <input id="input-address" name="address" type="text" class="form-control" placeholder="Address" value="${smartUser.address}">
                            </div>
                            <div class="col-xs-4">
                                <div class="checkbox">
                                    <input id="input-address-visible" name="addressVisible" type="checkbox"  <c:if test="${smartUser.addressVisible}">checked</c:if> > Show to all
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="input-cellphone" class="col-sm-4 control-label">Cellphone</label>
                            <div class="col-xs-4">
                                <input id="input-cellphone" name="cellphone" type="tel" class="form-control" placeholder="Cellphone" value="${smartUser.cellPhone}"  >
                            </div>
                            <div class="col-xs-4">
                                <div class="checkbox">
                                    <input id="input-cellphone-visible"  type="checkbox" name="cellphoneVisible" <c:if test="${smartUser.cellPhoneVisible}">checked</c:if> > Show to all
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="input-birthdate" class="col-sm-4 control-label">Birth Date</label>
                            <div class="col-xs-4">
                                <input id="input-birthdate" name="birthdate" type="date" class="form-control" placeholder="Birth Date" value="<fmt:formatDate value="${smartUser.birthDate}" pattern="yyyy-MM-dd" />">
                            </div>
                        </div>

                    </fieldset>
                    <fieldset class="private-settings">
                        <legend>Private settings</legend>
                        <div class="form-group">
                            <label for="input-profile-visible" class="col-sm-4 control-label">Public profile</label>
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
</div>

<jsp:include page="template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function(){
        $('.date').pickmeup({
            format  : 'm.d.Y'
        });
    });
</script>