<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<fmt:requestEncoding value="utf-8" />

<jsp:include page="template/top.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-xs-2">
            <img height="250px" src="http://upload.wikimedia.org/wikipedia/commons/b/bd/Dts_news_bill_gates_wikipedia.JPG">
        </div>
        <div class="col-xs-7">
            <p><strong>Bill Gates</strong><img height="15" src="http://icons.iconarchive.com/icons/visualpharm/must-have/256/Edit-icon.png"></p>
            <p>Age: 58</p>
            <p>Some other information</p>
        </div>
        <div class="col-xs-3">
            <h3>My gifts</h3>
            <ul class="list-group">
                <li class="list-group-item">
                    <blockquote>
                        <p><a href="/gift.html?id=5&owner=1">iPhone 4</a></p>
                        Some description
                        <small>
                            <p>Cost: 1000$</p>
                            <p>Got: 100$</p>
                            <p>Left: 1000$ - 100$ = 900$</p>
                        </small>
                    </blockquote>
                </li>
                <li class="list-group-item">
                    <blockquote>
                        <p><a href="/gift.html?id=5&owner=1">Samsung Galaxy S4</a></p>
                        Some description
                        <small>
                            <p>Cost: 1000$</p>
                            <p>Got: 100$</p>
                            <p>Left: 1000$ - 100$ = 900$</p>
                        </small>
                    </blockquote>
                </li>
            </ul>
        </div>
    </div>
</div>

<jsp:include page="template/bottom.jsp"/>