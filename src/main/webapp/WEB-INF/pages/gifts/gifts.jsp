<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<fmt:requestEncoding value="utf-8" />

<jsp:include page="../template/top.jsp"/>

<div class="container">
    <div class="alert alert-dismissable alert-info">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <b>Try to choose random gift!</b> The best gift - it is a random gift!
    </div>
    <div class="row">
        <div class="col-xs-10">
            <input type="text" class="form-control" placeholder="Type gift name or description">
        </div>
        <button type="submit" class="btn btn-default">Find</button>
        <button type="submit" class="btn btn-default">Random Gift</button>
    </div>
    <div class="row top-buffer">
        <div class="col-xs-12">
            <div class="panel panel-primary">
                <div class="panel-heading">Categories</div>
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