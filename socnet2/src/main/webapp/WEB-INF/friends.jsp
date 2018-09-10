<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://earlgray.com/jsp/tags/customtags"
	prefix="customtags"%>
<!DOCTYPE html>
<html lang="${language}">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>EarlGrey - Friends</title>

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
				<!-- user friends -->
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">User's friends</h3>
					</div>
					<c:if test="${! (empty requestScope.userFriends)}">
						<div class="table-responsive">
							<table id="friends" class="table table-hover">
								<thead>
								<tr>
									<th>Photo</th>
									<th>Full Name</th>
									<th>Country</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="friend" items="${requestScope.userFriends}">
									<tr>
										<td><a href="./main?action=profile&id=${friend.userId}"> <img class="media-object avatar img-circle"
												src='<c:out value="${friend.avatar}" default="images/default-avatar.jpg"/>'
												style="width: 64px; height: 64px;">
										</a></td>
										<td>${friend.firstName} ${friend.lastName}</td>
										<td>${friend.country}</td>
									</tr>
								</c:forEach>
							</tbody>
							</table>
						</div>
					</c:if>
					<c:if test="${empty requestScope.userFriends}">
						<table id="messages" class="table table-striped">
							<tbody>
								<tr>
									<td colspan="5">You haven't friends yet</td>
								</tr>
							</tbody>
						</table>
					</c:if>
				</div>
			</div>
		</div>
	</div>

	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/dataTables.bootstrap.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#friends').DataTable();
		});
	</script>
</body>
</html>
