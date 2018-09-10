
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://earlgray.com/jsp/tags/customtags"
	prefix="customtags"%>
<!DOCTYPE html>
<html lang="${language}">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>EarlGrey - Messages</title>

<link href="css/navbar.css" rel="stylesheet">
<link href="css/footer.css" rel="stylesheet">
<link href="css/table.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/dataTables.bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<!-- Navbar-->
	<%@ include file="/WEB-INF/jspf/navbar.jspf"%>
	<div class="container">
		<div class="row">
			<!-- user menu -->
			<%@ include file="/WEB-INF/jspf/usermenu.jspf"%>

			<div class="col-md-9">
				<!-- user messages -->

				<div class="page-header">
					<h5>User messages</h5>
				</div>
				<div class="table-striped">
					<c:if test="${! (empty requestScope.userMessages)}">
						<table id="messages" class="table">
							<tbody>
								<c:forEach var="message" items="${requestScope.userMessages}">
									<customtags:printUserMessage userMessage="${message}" />
								</c:forEach>
							</tbody>
						</table>
					</c:if>
					<c:if test="${empty requestScope.userMessages}">
						<table id="messages" class="table">
							<tbody>
								<tr>
									<td colspan="3">You haven't messages yet</td>
								</tr>
							</tbody>
						</table>
					</c:if>
				</div>
				<c:if test="${! (empty errorMsg)}">
					<customtags:printMessage message="${errorMsg}" type="error" />
				</c:if>
				<c:if test="${! (empty successMsg)}">
					<customtags:printMessage message="${successMsg}" type="success" />
				</c:if>
			</div>
		</div>
	</div>

	<script src="js/jquery.min.js"></script>
	<script src="js/jquery-3.3.1.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/dataTables.bootstrap.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#messages').DataTable();
		});
	</script>
</body>
</html>
