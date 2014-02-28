<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<jsp:include page="template/top.jsp"/>

<div class="well well-lg">
    <div class="row">
        <div class="col-md-8">
            <h1 class="text-center"><strong><spring:message code="label.index_text1"/></strong></h1>
        </div>
        <div class="col-md-4">
            <h1 class="text-center"><strong><spring:message code="label.index_text2"/></strong></h1>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4">
            <h1 class="text-center"><strong><spring:message code="label.index_text3"/></strong></h1>
        </div>
        <div class="col-md-4">
            <img height="150px" src="http://ionizerresearch.com/images/which_to_choose.jpg" class="img-rounded">
        </div>
        <div class="col-md-4">
            <h1 class="text-center"><strong><spring:message code="label.index_text4"/></strong></h1>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4">
            <h1 class="text-center"><strong><spring:message code="label.index_text5"/></strong></h1>
        </div>
        <div class="col-md-4">
            <h1 class="text-center"><strong><spring:message code="label.index_text6"/></strong></h1>
        </div>
        <div class="col-md-4">
            <h1 class="text-center"><strong><spring:message code="label.index_text7"/></strong></h1>
        </div>
    </div>
</div>
<button class="btn btn-default btn-lg btn-block"><spring:message code="label.index_btn_text"/></button>

<jsp:include page="template/bottom.jsp"/>