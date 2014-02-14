<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<jsp:include page="../template/top.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-xs-10">
            <input type="text" class="form-control" placeholder="<spring:message code="label.searchGiftPlaceHolder"/>">
        </div>
        <button type="submit" class="btn btn-default"><spring:message code="label.find"/></button>
        <button type="submit" class="btn btn-default"><spring:message code="label.random_gift"/></button>
    </div>
    <div class="row top-buffer">
        <div class="col-xs-12">
            <div class="panel panel-primary">
                <div class="panel-heading"><spring:message code="label.gift_categories"/></div>
                <div class="panel-body">
                    <div class="row">
                        There are categories
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../template/bottom.jsp"/>