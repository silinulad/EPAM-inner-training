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

<title>EarlGrey - Gift</title>

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
						<h3 class="panel-title">Gift Information</h3>
					</div>

					<div class="panel-body">

						<div class="media">
							<div class="media-body">
								<address>
									<h4 class="media-heading">
										<strong>${giftInfo.name}</strong>
									</h4>
									<br> ID:
									<c:out value="${giftInfo.giftId}" />
									<br> 
									Description: ${giftInfo.description} 
									<br>
									Date created:
									<fmt:formatDate value="${giftInfo.dateCreated}" />
									<br>
								</address>
								
									<button type="button" class="btn btn-info btn-xs"
										data-toggle="modal" data-target="#giftModal">Edit
										Gift</button>
								
									<form method="POST" name="giftImageForm" action="./file">
										<input type="hidden" name="token" value="${token}"> 
										<input type="hidden" name="action" value=""> 
										<input type="hidden" name="idGift" value="${giftInfo.giftId}">
										<button type="button" class="btn btn-info btn-xs"
											onclick="document.getElementById('inputFile').click();">Change
											photo</button>
										<br>
										<input type="file" class="form-control-file" id="inputFile"
											aria-describedby="fileHelp" style="display: none;"
											name="file" accept="image/jpeg,image/png,image/gif"
											onchange="setFieldValue('giftImageForm', 'action','uploadimage'); 
												  setBinaryEnctype('giftImageForm');
												  submitTo('giftImageForm', './file');" />
									</form>
							
								<c:if test="${! (empty errorMsg)}">
									<br>
									<br>
									<customtags:printMessage message="${errorMsg}" type="error" />
								</c:if>
								<c:if test="${! (empty successMsg)}">
									<br>
									<br>
									<customtags:printMessage message="${successMsg}" type="success" />
								</c:if>
							</div>
							<div class="media-right">
								<img class="media-object avatar img-circle"
									src='<c:out value="${giftInfo.image}" default="images/defaultgift.png"/>'
									style="width: 150px; height: 150px;">
							</div>
						</div>
					</div>
				</div>

					<!-- Modal Edit Gift-->
					<div id="giftModal" class="modal fade" role="dialog">
						<div class="modal-dialog">

							<!-- Modal content-->
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
									<h4 class="modal-title">Edit Gift</h4>
								</div>
								<form method="POST" name="giftForm" action="./main">
									<div class="modal-body">
										<div class="form-group">
											<label class="control-label">Name:</label> <input
												class="form-control" name="name" maxlength="35"
												value="${giftInfo.name}" required>
										</div>
										<div class="form-group">
											<label class="control-label">Description:</label>
											<textarea class="form-control" name="description"
												maxlength="255" required>"${giftInfo.description}"</textarea>
										</div>
										<input type="hidden" name="token" value="${token}"> <input
											type="hidden" name="action" value="editgift"> <input
											type="hidden" name="id" value="${giftInfo.giftId}">

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
	<script src="js/bootstrap-formhelpers.js"></script>
	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/dataTables.bootstrap.min.js"></script>
	<script src="js/project-scripts.js"></script>
	<script src="js/send.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#groups').DataTable();
			$('#friends').DataTable();
		});
	</script>
	<c:if test="${!(empty errorMsgModal) || !(empty successMsgModal)}">
		<script type="text/javascript">
			$(window).load(function() {
				$('#giftModal').modal('show');
			});
		</script>
	</c:if>
</body>
</html>