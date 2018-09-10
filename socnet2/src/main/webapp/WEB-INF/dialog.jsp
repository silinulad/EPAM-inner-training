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

<title>EarlGrey - Dialogs</title>

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
					<h4>User dialog</h4>
				</div>

				<c:if test="${not empty requestScope.dialog}">

					<div class="table-borderless">
						<table id="dialog" class="table">
							<thead>
								<tr>
									<th></th>
									<th></th>
								</tr>
							</thead>

							<tbody>

								<c:forEach var="message" items="${requestScope.dialog}">
									<fmt:formatDate pattern="dd/MM/yyy HH:mm"
										value="${message.time}" var="date" />
									<tr>
										<td>
											<div class="media">
												<div class="media-left">
													<a href="./main?action=profile&id=${message.sender.userId}">
														<img alt="64x64" class="media-object img-circle"
														style="width: 64px; height: 64px;"
														src=<c:out value='${message.sender.avatar}' default="images/default-avatar.jpg"/>
														data-holder-rendered="true">
													</a>
												</div>
												<div class="media-body">
													<h5 class="media-heading">
														<strong>${message.sender.firstName}
															${message.sender.lastName}</strong> <small>${date}</small>
													</h5>
													<p>${message.body}</p>

													<c:if test="${not empty message.files}">

														<form method="POST" name="downForm" action="./main"
															class="form-inline">
															<input type="hidden" name="token" value="${token}">
															<input type="hidden" name="action" value=""> <input
																type="hidden" name="idFile" value="">
															<p>
																<c:forEach var="userFile" items="${message.files}">
																	<div class="media">
																		<div class="media-left">
																			<span class="glyphicon glyphicon-file"
																				aria-hidden="true"></span>
																		</div>
																		<div class="media-body">
																			
																				<a href="#"
																					onclick="setFieldValue('downForm', 'action', 'downfile');
																							setFieldValue('downForm', 'idFile', '${userFile.fileId}');
																							submitTo('downForm', './main');">${userFile.originName}</a>
																			
																		</div>
																	</div>
																</c:forEach>
															</p>
														</form>
													</c:if>
												</div>
											</div>
										</td>

										<td><c:if test="${message.sender eq user}">
												<a title="Delete message"
													href="./main?action=delmessage&id=${message.messageId}"><span
													class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
											</c:if></td>

									</tr>
								</c:forEach>

							</tbody>
						</table>
					</div>

					<form method="POST" name="messageform" action="./main"
						class="form-inline">
						<div class="form-group">
							<div class="input-group">

								<input type="hidden" name="token" value="${token}"> <input
									type="hidden" name="idTo" value="${recipientId}">
								<c:forEach var="file" items="${fileList}">
									<input type="hidden" name="attach" value="${file.fileId}">
								</c:forEach>
								<input type="hidden" name="action" value="sendmessage">
								<input type="hidden" name="delattach" value="">

								<div class="input-group-btn">
									<button type="button" class="btn btn-lnk btn-lg pull-left"
										onclick="document.getElementById('inputFile').click();">
										<span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>
									</button>

									<input type="file" class="form-control-file" id="inputFile"
										aria-describedby="fileHelp" style="display: none;" name="file"
										multiple="multiple"
										onchange="setFieldValue('messageform', 'action','uploadfile'); 
												  setBinaryEnctype('messageform');
												  submitTo('messageform', './file');" />
								</div>

								<div class="form-group">
									<textarea name="body" cols="110" rows="1" class="form-control"
										placeholder="Write what you wish" maxlength="1000"></textarea>
								</div>

								<div class="input-group-btn">
									<div class="form-group">
										<button type="submit" class="btn btn-lnk btn-lg pull-left">
											<span class="glyphicon glyphicon-send" aria-hidden="true"></span>
										</button>
									</div>
								</div>

							</div>
						</div>
					</form>
					<!-- send message form -->

					<c:if test="${! (empty fileList)}">
						<div class="table-borderless">
							<table id="dialog" class="table">
								<tbody>
									<c:forEach var="file" items="${fileList}">
										<tr>
											<td>
												<div class="media">
													<div class="media-left">
														<span class="glyphicon glyphicon-file" aria-hidden="true"></span>
													</div>
													<div class="media-body">
														<p>${file.originName}</p>
													</div>
												</div>
											</td>
											<td><a title="Delete attachment" href="#"
												onclick="setFieldValue('messageform','action','deletefile'); 
														setFieldValue('messageform','delattach','${file.fileId}'); 
														submitTo('messageform', './main');">
													<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
											</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>

				</c:if>

				<c:if test="${! (empty errorMsg)}">
					<customtags:printMessage message="${errorMsg}" type="error" />
				</c:if>

				<c:if test="${! (empty successMsg)}">
					<customtags:printMessage message="${successMsg}" type="success" />
				</c:if>

			</div>
			<!-- input group -->

		</div>
	</div>

	<script src="js/jquery.min.js"></script>
	<script src="js/jquery-3.3.1.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/dataTables.bootstrap.min.js"></script>
	<script src="js/project-scripts.js"></script>
	<script src="js/send.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#dialog').DataTable({
				"scrollY" : "400px",
				"scrollCollapse" : true,
				"ordering" : false,
				"paging" : false,
				"scrollX" : false
			});

		});
	</script>
</body>
</html>
