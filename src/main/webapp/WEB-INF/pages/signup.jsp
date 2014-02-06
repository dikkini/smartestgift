<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<fmt:requestEncoding value="utf-8" />

<jsp:include page="template/top.jsp"/>

<div class="container">
    <div class="well well-lg">
        <div>
            <form class="form-horizontal" method="post">
                <div class="form-group">
                    <label for="input_firstname" class="col-sm-2 control-label">First Name<span class="required">*</span>
                    </label>
                    <div class="col-xs-4">
                        <input type="text" class="form-control" id="input_firstname" placeholder="FirstName">
                    </div>
                </div>
                <div class="form-group">
                    <label for="input_lastname" class="col-sm-2 control-label">Last Name</label>
                    <div class="col-xs-4">
                        <input type="text" class="form-control" id="input_lastname" placeholder="LastName">
                    </div>
                </div>
                <div class="form-group">
                    <label for="input_email" class="col-sm-2 control-label">Email<span class="required">*</span>
                    </label>
                    <div class="col-xs-4">
                        <input type="email" class="form-control" id="input_email" placeholder="Email">
                    </div>
                </div>
                <div class="form-group">
                    <label for="input_password" class="col-sm-2 control-label">Password<span class="required">*</span>
                    </label>
                    <div class="col-xs-4">
                        <input type="password" class="form-control " id="input_password" placeholder="Password">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">Sign Up</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="template/bottom.jsp"/>