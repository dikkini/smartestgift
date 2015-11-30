<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<jsp:include page="template/top.jsp"/>

<div class="well well-lg">
    <div class="row">
        <div class="col-xs-2"></div>
        <div class="col-xs-8">
            <blockquote>
                <p>Excelent service. Now, my friends from other city could help me and make me more happy :-P</p>
                <footer>Someone famous in <cite title="Source Title">Source Title</cite></footer>
            </blockquote>
        </div>
        <div class="col-xs-2"></div>
    </div>

    <div class="row">
        <div class="col-xs-2"></div>
        <div class="col-xs-8">
            <sec:authorize access="isAuthenticated()">
                <button id="sign-up-btn" class="btn btn-default btn-lg btn-block"><spring:message code="label.index_btn_text"/></button>
            </sec:authorize>
        </div>
        <div class="col-xs-2"></div>
    </div>
</div>

<jsp:include page="template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function(){
        $("#sign-up-btn").click(function() {
            window.location = "/signup";
        });
    });
</script>