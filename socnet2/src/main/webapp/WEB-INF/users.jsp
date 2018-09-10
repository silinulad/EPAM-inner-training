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

<title>EarlGrey - Users</title>

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

			<!-- all users -->
			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">All users</h3>
					</div>
					<table id="users" class="table table-hover">
						<thead>
							<tr>

								<th>Photo</th>
								<th>Full Name</th>
								<th>Country</th>

							</tr>
						</thead>
						<tbody>
							<c:forEach var="user" items="${requestScope.users}">
								<tr>
									<td><a href="./main?action=profile&id=${user.userId}">
											<img class="media-object avatar img-circle"
											src='<c:out value="${user.avatar}" default="images/default-avatar.jpg"/>'
											style="width: 64px; height: 64px;">
									</a></td>
									<td>${user.firstName} ${user.lastName}</td>
									<td>${user.country}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

			</div>
		</div>
	</div>

	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/dataTables.bootstrap.min.js"></script>
	<script type="text/javascript">
    $(document).ready(function () {
        $('#users').DataTable();
    });
</script>
</body>
</html>
