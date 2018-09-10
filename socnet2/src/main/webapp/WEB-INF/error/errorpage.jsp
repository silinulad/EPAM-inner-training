<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://earlgray.com/jsp/tags/customtags" prefix="customtags" %>
<!DOCTYPE html>
<html lang="${lang}">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>EarlGray - Error page</title>

    <link href="${pageContext.request.contextPath}/css/error.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1>${statusCode}</h1>
                <div class="error-details" style="margin:auto;width:300px;">
                    <customtags:printMessage message="${errorMsg}" type="error"/>
                </div>
                <div class="error-actions">
                    <a href="${pageContext.request.contextPath}" class="btn btn-primary"><span class="glyphicon glyphicon-home"></span>
                        Take Me Home </a>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
