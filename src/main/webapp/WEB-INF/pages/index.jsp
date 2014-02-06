<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<fmt:requestEncoding value="utf-8" />

<jsp:include page="template/top.jsp"/>

<div class="container">
    <div class="well well-lg">
        <div class="row">
            <div class="col-md-8">
                <h1 class="text-center"><strong>What gift your friend from Los-Angles want?</strong></h1>
            </div>
            <div class="col-md-4">
                <h1 class="text-center"><strong>CHOOSE</strong></h1>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <h1 class="text-center"><strong>PRESENT</strong></h1>
            </div>
            <div class="col-md-4">
                <img height="150px" src="http://ionizerresearch.com/images/which_to_choose.jpg" class="img-rounded">
            </div>
            <div class="col-md-4">
                <h1 class="text-center"><strong>How do u can to surprise your girlfriend?</strong></h1>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <h1 class="text-center"><strong>Don't know friends wishes?</strong></h1>
            </div>
            <div class="col-md-4">
                <h1 class="text-center"><strong>WITH SMART</strong></h1>
            </div>
            <div class="col-md-4">
                <h1 class="text-center"><strong>Help your sister complete her dream!</strong></h1>
            </div>
        </div>
    </div>
    <button class="btn btn-default btn-lg btn-block">SIGN UP AND WANT SOME PRESENT</button>
</div>

<jsp:include page="template/bottom.jsp"/>