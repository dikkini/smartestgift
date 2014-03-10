<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<jsp:useBean id="smartUser" class="com.smartestgift.dao.model.SmartUser" scope="request"/>

<jsp:include page="../template/top.jsp"/>

<div class="row">
    <div class="col-xs-4">
        <ul class="nav navbar-nav">
            <li class="active"><a href="#">Inbox <span class="badge">42</span></a></li>
            <li><a href="#">Sent</a></li>
        </ul>
        <div class="col-lg-9">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="Find conversations">
              <span class="input-group-btn">
                <button style="margin-bottom: 20px" class="btn btn-default" type="button">Search</button>
              </span>
            </div>
            <div>
                <ul class="nav nav-pills nav-stacked">
                    <li>
                        <div class="list-group">
                            <a href="#" class="list-group-item active">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <img height="50" src="asd">
                                    </div>
                                    <div class="col-xs-9">
                                        <p class="list-group-item-heading">Nickname</p>
                                        <p class="list-group-item-text">Description</p>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </li>
                    <li>
                        <div class="list-group">
                            <a href="#" class="list-group-item">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <img height="50" src="asd">
                                    </div>
                                    <div class="col-xs-9">
                                        <p class="list-group-item-heading">Nickname</p>
                                        <p class="list-group-item-text">Description</p>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </li>
                    <li>
                        <div class="list-group">
                            <a href="#" class="list-group-item">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <img height="50" src="asd">
                                    </div>
                                    <div class="col-xs-9">
                                        <p class="list-group-item-heading">Nickname</p>
                                        <p class="list-group-item-text">Description</p>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="col-xs-8">
        <strong>User Nickname</strong>
        <a style="margin-left: 20px" class="btn btn-primary">New Message</a>
        <div class="btn-group">
            <a class="btn btn-default dropdown-toggle" data-toggle="dropdown" href="#">
                Actions
                <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <li><a href="#">First Link</a></li>
                <li><a href="#">Second Link</a></li>
                <li><a href="#">Third Link</a></li>
            </ul>
        </div>
    </div>
</div>
<jsp:include page="../template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function(){

    });
</script>

