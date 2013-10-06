
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:requestEncoding value="utf-8" />

<!DOCTYPE html>
<html>
<head>
    <title></title>
    <!-- meta -->
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <!-- css -- >
    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="resources/ext/bootstrap/css/bootstrap.css">
    <!-- js -->
    <script type="text/javascript" charset="utf-8" src="resources/ext/jquery/jquery-2.0.3.js"></script>
    <script type="text/javascript" charset="utf-8" src="resources/ext/bootstrap/js/bootstrap.js"></script>
</head>
<body>
<p class="one">Hello World!</p>
</body>
</html>

<script type="text/javascript">
    $(document).ready(function() {
        $(".one").append("<p class='two'> GoodBye World! </p>");
    });
</script>
