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

<title>EarlGrey - Gifts</title>

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

			<!-- all gifts -->
			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title pull-left">All gifts</h3>
						<button type="button" class="btn btn-success btn-xs pull-right"
							data-toggle="modal" data-target="#giftModal">Create new
						</button>
						<div class="clearfix"></div>
					</div>
					<c:if test="${! (empty requestScope.gifts)}">
						<table id="gifts" class="table table-hover">
							<thead>
								<tr>
									<th>Photo</th>
									<th>Name</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="gift" items="${requestScope.gifts}">
									<tr>
										<td>
											<a href="./main?action=gift&id=${gift.giftId}">
												<img class="media-object avatar img-circle"
													src='<c:out value="${gift.image}" default="images/defaultgift.png"/>'
												style="width: 64px; height: 64px;">
											</a>
										</td>
										<td>${gift.name}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>

				<!-- user presents -->
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Your presents</h3>
					</div>
					<c:if test="${not empty requestScope.userPresents}">
						<table id="userpresents" class="table table-hover">
							<thead>
								<tr>
									<th>Photo</th>
									<th>Name</th>
									<th>Sender</th>
									<th>Date</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="userPresent" items="${requestScope.userPresents}">
									<tr>
										<td>
											<a href="./main?action=gift&id=${userPresent.gift.giftId}">
												<img class="media-object avatar img-circle"
													src='<c:out value="${userPresent.gift.image}" default="images/defaultgift.png"/>'
												style="width: 64px; height: 64px;">
											</a>
										</td>
										<td>${userPresent.gift.name}</td>
										<td>${userPresent.sender.firstName} ${userPresent.sender.lastName}</td>
										<td><fmt:formatDate pattern="dd/MM/yyy HH:mm"
												value="${userPresent.time}" var="presentDate" />${presentDate}</td>
											<td><c:if test="${userPresent.sender eq user}">
												<a title="Delete present"
													href="./main?action=delpresent&id=${userPresent.presentId}"><span
													class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
											</c:if></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>

				<!-- Modal Add Gift-->
				<div id="giftModal" class="modal fade" role="dialog">
					<div class="modal-dialog">

						<!-- Modal content-->
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Add Gift</h4>
							</div>
							<form method="POST" name="giftForm" action="./main">
								<div class="modal-body">
									<div class="form-group">
										<label class="control-label">Name:</label> <input
											class="form-control" name="name" maxlength="35"
											value="${name}" required>
									</div>
									<div class="form-group">
										<label class="control-label">Description:</label>
										<textarea class="form-control" name="description"
											maxlength="255" required>"${description}"</textarea>
									</div>
									<input type="hidden" name="token" value="${token}"> 
									<input type="hidden" name="action" value="addgift">

									<c:if test="${! (empty errorMsgModal)}">
										<br>
										<customtags:printMessage message="${errorMsgModal}"
											type="error" />
									</c:if>
									<c:if test="${! (empty successMsgModal)}">
										<br>
										<customtags:printMessage message="${successMsgModal}"
											type="success" />
									</c:if>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
									<button type="submit" class="btn btn-primary">Save</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/dataTables.bootstrap.min.js"></script>
	<script src="js/project-scripts.js"></script>
	<script src="js/send.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#gifts').DataTable(({
			}));
			$('#userpresents').DataTable(({
			}));
		});
	</script>
	<c:if test="${!(empty errorMsg) || !(empty successMsg)}">
		<script type="text/javascript">
			$(window).load(function() {
				$('#giftModal').modal('show');
			});
		</script>
	</c:if>
</body>
</html>
