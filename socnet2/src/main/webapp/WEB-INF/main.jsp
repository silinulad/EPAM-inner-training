<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://earlgray.com/jsp/tags/customtags" prefix="customtags" %>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>EarlGray - Information page</title>
    <link href="css/navbar.css" rel="stylesheet">
    <link href="css/footer.css" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<!-- Navbar-->
    <%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<div class="container">
    
    <div class="row">
        <!-- user menu -->
        <%@ include file="/WEB-INF/jspf/usermenu.jspf" %>

        <div class="col-md-9">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Information</h3>
                </div>

                <div class="panel-body">
                    <c:if test="${not empty errorMsg}">
                        <br>
                        <customtags:printMessage message="${errorMsg}" type="error"/>
                    </c:if>
                    <c:if test="${not empty successMsg}">
                        <br>
                        <customtags:printMessage message="${successMsg}" type="success"/>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
