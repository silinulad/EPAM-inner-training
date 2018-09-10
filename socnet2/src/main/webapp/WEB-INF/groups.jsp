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

<title>EarlGray - Groups</title>

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

			<!-- all groups -->
			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title pull-left">All groups</h3>
						<button type="button" class="btn btn-success btn-xs pull-right"
							data-toggle="modal" data-target="#groupModal">Create new
						</button>
						<div class="clearfix"></div>
					</div>
					<c:if test="${! (empty requestScope.groups)}">
						<table id="groups" class="table table-hover">
							<thead>
								<tr>
									<th>Name</th>
									<th>Description</th>
									<th>Creator</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="group" items="${requestScope.groups}">
									<customtags:printGroup group="${group}" />
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>

				<!-- user groups -->
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Your groups</h3>
					</div>
					<c:if test="${! (empty requestScope.userGroups)}">
						<table id="usergroups" class="table table-hover">
							<thead>
								<tr>
									<th>Name</th>
									<th>Description</th>
									<th>Creator</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="userGroup" items="${requestScope.userGroups}">
									<customtags:printGroup group="${userGroup}" />
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>

				<!-- Modal Add Group-->
				<div id="groupModal" class="modal fade" role="dialog">
					<div class="modal-dialog">
						<!-- Modal content-->
						
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Create Group</h4>
							</div>
							
							<form method="POST" action="./main">
								<div class="modal-body">
									
									<div class="form-group">
										<label class="control-label">Name:</label> <input
											class="form-control" name="name" maxlength="35"
											value="${name}" required>
									</div>
									
									<div class="form-group">
										<label class="control-label">Description:</label>
										<textarea class="form-control" name="description"
											maxlength="255" required>"${description}</textarea>
									</div>
									
									<input type="hidden" name="action" value="addgroup"> 
									<input type="hidden" name="token" value="${token}">
									<c:if test="${! (empty errorMsg)}">
										<br>
										<customtags:printMessage message="${errorMsg}" type="error" />
									</c:if>
									<c:if test="${! (empty successMsg)}">
										<br>
										<customtags:printMessage message="${successMsg}"
											type="success" />
									</c:if>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
									<button type="submit" class="btn btn-primary">Create</button>
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
	<script type="text/javascript">
		$(document).ready(function() {
			$('#groups').DataTable(({
			}));
			$('#usergroups').DataTable(({
			}));
		});
	</script>
	<c:if test="${!(empty errorMsg) || !(empty successMsg)}">
		<script type="text/javascript">
			$(window).load(function() {
				$('#groupModal').modal('show');
			});
		</script>
	</c:if>
</body>
</html>
