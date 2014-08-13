<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8" />

<jsp:useBean id="username" class="java.lang.String" scope="request"/>
<jsp:useBean id="giftName" class="java.lang.String" scope="request"/>

<jsp:include page="template/top.jsp"/>

alert(${username})
alert(${giftName})

<jsp:include page="template/bottom.jsp"/>

<script type="text/javascript">
    $(document).ready(function(){
    });
</script>