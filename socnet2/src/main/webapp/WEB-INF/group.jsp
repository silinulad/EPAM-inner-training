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

<title>EarlGrey - Group</title>

<link href="css/navbar.css" rel="stylesheet">
<link href="css/footer.css" rel="stylesheet">
<link href="css/table.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/bootstrap-formhelpers.min.css" rel="stylesheet">
<link href="css/dataTables.bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<!-- Navbar-->
	<%@ include file="/WEB-INF/jspf/navbar.jspf"%>
	<div class="container">

		<div class="row">
			<!-- user menu -->
			<%@ include file="/WEB-INF/jspf/usermenu.jspf"%>

			<div class="col-md-9 personal-info">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Group Information</h3>
					</div>

					<div class="panel-body">
						<div class="media">
							<form method="POST" name="groupForm" action="./file">
								<input type="hidden" name="token" value="${token}">
								<input type="hidden" name="action" value=""> 
								<input type="hidden" name="id" value="${groupInfo.groupId}">
								
								<div class="media-body">
									<address>
										<h4 class="media-heading">
											<strong>${groupInfo.name}</strong>
										</h4>
										<br> ID:
										<c:out value="${groupInfo.groupId}" />
										<br> Description: ${groupInfo.description} <br>
										Creator: <a
											href="./main?action=profile&id=${groupInfo.owner.userId}">${groupInfo.owner.firstName}
											${groupInfo.owner.lastName}</a> <br> Date created:
										<fmt:formatDate value="${groupInfo.dateCreated}" />
										<br>
									</address>
									<c:choose>
										<c:when test="${(empty isMember)}">
											<a class="btn btn-info btn-xs" role="button" href="#"
												onclick="setFieldValue('groupForm', 'action','joingroup');submitTo('groupForm', './main');">Join
												Group</a>
										</c:when>
										<c:when test="${! (empty isMember)}">
											<a class="btn btn-warning btn-xs" role="button" href="#"
												onclick="setFieldValue('groupForm', 'action','leavegroup');submitTo('groupForm', './main');">Leave
												Group</a>
										</c:when>
									</c:choose>
									<c:if test="${groupInfo.isOwner(user)}">
										
										<button type="button" class="btn btn-info btn-xs"
											data-toggle="modal" data-target="#groupModal">Edit
											Group</button>
											
										<a class="btn btn-danger btn-xs" role="button" href="#"
											onclick="setFieldValue('groupForm', 'action','delgroup');submitTo('groupForm', './main');">Delete
											group</a>

										<a class="btn btn-info btn-xs" role="button" href="#"
											onclick="document.getElementById('inputFile').click();">
											Change photo </a>

										<input type="file" class="form-control-file" id="inputFile"
											aria-describedby="fileHelp" style="display: none;"
											name="file" accept="image/jpeg,image/png,image/gif"
											onchange="setFieldValue('groupForm', 'action','uploadimage'); 
												  setBinaryEnctype('groupForm');
												  submitTo('groupForm', './file');" />
									</c:if>
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
							
							<div class="media-right">
								<img class="media-object avatar img-rounded"
									src='<c:out value="${groupInfo.image}" default="images/groupdefault1.jpg"/>'
									style="width: 150px; height: 150px;">
							</div>
							</form>
						</div>
					</div>
				</div>

				<!-- users in group -->
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Group members</h3>
					</div>
					<c:if test="${! (empty requestScope.groupMembers)}">
						<table id="friends" class="table table-hover">
							<thead>
								<tr>
									<th>Photo</th>
									<th>Full Name</th>
									<th>Country</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="member" items="${requestScope.groupMembers}">
									<tr>
										<td><a href="./main?action=profile&id=${member.userId}"> <img class="media-object avatar img-circle"
												src='<c:out value="${member.avatar}" default="images/default-avatar.jpg"/>'
												style="width: 64px; height: 64px;">
										</a></td>
										<td>${member.firstName} ${member.lastName}</td>
										<td>${member.country}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>				
				</div>

				<c:if test="${groupInfo.isOwner(user)}">
					<!-- Modal Edit Group-->
					<div id="groupModal" class="modal fade" role="dialog">
						<div class="modal-dialog">

							<!-- Modal content-->
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
									<h4 class="modal-title">Edit Group</h4>
								</div>
								<form method="POST" action="./main">
									<div class="modal-body">
										<div class="form-group">
											<label class="control-label">Name:</label> <input
												class="form-control" name="name" maxlength="35"
												value="${groupInfo.name}" required>
										</div>
										<div class="form-group">
											<label class="control-label">Description:</label>
											<textarea class="form-control" name="description"
												maxlength="255" required>"${groupInfo.description}"</textarea>
										</div>
										<input type="hidden" name="token" value="${token}"> <input
											type="hidden" name="action" value="editgroup"> <input
											type="hidden" name="id" value="${groupInfo.groupId}">

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
				</c:if>
			</div>
		</div>
	</div>
	
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap-formhelpers.js"></script>
	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/dataTables.bootstrap.min.js"></script>
	<script src="js/project-scripts.js"></script>
	<script src="js/send.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#members').DataTable();
		});
	</script>
	<c:if test="${!(empty errorMsgModal) || !(empty successMsgModal)}">
		<script type="text/javascript">
			$(window).load(function() {
				$('#groupModal').modal('show');
			});
		</script>
	</c:if>
</body>
</html>