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
        <div class="col-xs-5">
            <form class="form-horizontal login-form" action="/profile/save" method="post">
                <div class="form-group">
                    <label for="input-lastname" class="col-sm-4 control-label">Last Name</label>
                    <div class="col-xs-4">
                        <input id="input-lastname" name="lastname" type="text" class="form-control" placeholder="Last Name" value="${smartUser.lastName}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-firstname" class="col-sm-4 control-label">First Name</label>
                    <div class="col-xs-4">
                        <input id="input-firstname" name="firstname" type="text" class="form-control" placeholder="First Name" value="${smartUser.firstName}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-middlename" class="col-sm-4 control-label">Middle Name</label>
                    <div class="col-xs-4">
                        <input id="input-middlename" name="middlename" type="text" class="form-control" placeholder="Middle Name" value="${smartUser.middleName}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-birthdate" class="col-sm-4 control-label">Birth Date</label>
                    <div class="col-xs-4">
                        <input id="input-birthdate" name="birthdate" type="text" class="form-control date" placeholder="Birth Date" value="${smartUser.birthDate}">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <input type="submit" value="Save" class="btn btn-default"/>
                    </div>
                    <div class="col-sm-offset-2 col-sm-10">
                        <a href="/profile">Cancel</a>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-xs-4">
            <h3>My wishlist</h3>
            <ul class="list-group">
                <c:forEach items="${smartUser.gifts}" var="gift">
                    <li class="list-group-item">
                        <blockquote>
                            <p>
                                <a href="/gift?id=<c:out value="${gift.uuid}"/>"><c:out value="${gift.name}"/></a>
                                - <c:out value="${gift.cost}"/>
                            </p>
                            <c:out value="${gift.description}"/>
                        </blockquote>
                    </li>
                </c:forEach>
            </ul>
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