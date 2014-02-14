<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<fmt:requestEncoding value="utf-8" />

<jsp:useBean id="smartUser" class="com.smartestgift.dao.model.SmartUser" scope="request"/>

<jsp:include page="../template/top.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-xs-3">
            <div class="panel">
            <c:choose>
                <c:when test="${smartUser.file.id == null}">
                    <img src=/ext/main/images/no_photo.jpg>
                </c:when>
                <c:otherwise>
                    <img src="/file/get/${smartUser.file.id}">
                </c:otherwise>
            </c:choose>
            </div>

            <div class="panel panel-primary">
                <div class="panel-heading">Gift</div>
                <div class="panel-body">
                    <ul class="nav nav-pills nav-stacked">
                        <li><a href="/gifts/categories">Find Gift</a></li>
                    </ul>
                </div>
            </div>

            <div class="panel panel-primary">
                <div class="panel-heading">Social</div>
                <div class="panel-body">
                    <ul class="nav nav-pills nav-stacked">
                        <li><a href="#">My Helpers</a></li>
                        <li><a href="#">Messages</a></li>
                    </ul>
                </div>
            </div>

        </div>
        <div class="col-xs-5">
            <p>
                <strong> <c:out value="${smartUser.lastName} ${smartUser.firstName} ${smartUser.middleName}"/> </strong>
            </p>
            <p><strong>Birth Date:</strong> <fmt:formatDate value="${smartUser.birthDate}" pattern="dd.MM.yyyy" /></p>
            <c:if test="${smartUser.cellPhoneVisible}">
                <p><strong>Cellphone:</strong> <c:out value="${smartUser.cellPhone}"/></p>
            </c:if>
            <c:if test="${smartUser.addressVisible}">
                <p><strong>Address:</strong> <c:out value="${smartUser.address}"/></p>
            </c:if>
        </div>
        <div class="col-xs-4">
            <c:if test="${smartUser.profileVisible}">
                <h3>My wishlist</h3>
                <ul class="list-group">
                    <c:forEach items="${smartUser.gifts}" var="gift">
                        <li class="list-group-item">
                            <blockquote>
                                <p>
                                    <a href="gift?id=<c:out value="${gift.uuid}"/>"><c:out value="${gift.name}"/></a>
                                    - <c:out value="${gift.cost}"/>
                                </p>
                                <c:out value="${gift.description}"/>
                            </blockquote>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
    </div>
</div>

<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function(){
    });
</script>