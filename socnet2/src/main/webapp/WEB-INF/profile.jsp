<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://earlgray.com/jsp/tags/customtags"
	prefix="customtags"%>

<!DOCTYPE html">
<html lang="${language}">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>EarlGrey - Profile</title>

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
			<!-- user posts -->
			<div class="col-md-4 personal-info">

					<form method="POST" name="messageform" action="./main"
						class="form-inline">
						<div class="form-group">
							<div class="input-group">

								<input type="hidden" name="token" value="${token}"> <input
									type="hidden" name="attach" value="${fileList}"> <input
									type="hidden" name="action" value="sendpost">

								<div class="form-group">
									<textarea name="body" cols="40" rows="1" class="form-control"
										placeholder="Write what you wish" maxlength="1000" required></textarea>
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

					<c:if test="${! (empty requestScope.userPosts)}">
						<c:forEach var="userPost" items="${requestScope.userPosts}">
							<c:if test="${userPost.author == user}">

								<form method="POST" name="deleteForm" action="./main">
									<input type="hidden" name="token" value="${token}"> <input
										type="hidden" name="action" value="delpost"> <input
										type="hidden" name="id" value="${userPost.postId}">
									<div class="thumbnail">
										<div class="caption">
											<fmt:formatDate pattern="dd/MM/yyy HH:mm"
												value="${userPost.time}" var="date" />
											${date}
											<hr>
											<p>${userPost.body}</p>

											<button type="submit" class="btn btn-lnk">
												<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
											</button>
										</div>
									</div>
								</form>

							</c:if>
						</c:forEach>
					</c:if>
			</div>


			<!-- user profile -->
			<div class="col-md-5 personal-info">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">User Profile</h3>
					</div>

					<div class="panel-body">

						<div class="media">
							<div class="media-body">
								<address>
									<h4 class="media-heading">${userProfile.firstName}
										${userProfile.lastName}</h4>
									<br> ID: ${userProfile.userId} <br> Email:
									${userProfile.email} <br> Sex:
									<c:if test="${!userOptions.hideSex}">
										<c:choose>
											<c:when test="${userProfile.gender.toString() eq 'm'}">male</c:when>
											<c:when test="${userProfile.gender.toString() eq 'f'}">female</c:when>
										</c:choose>
									</c:if>
									<br> Date of Birth:
									<fmt:formatDate value="${userProfile.birthDate}" />
									<br> Phone:
									<c:if test="${!userOptions.hidePhone}">
										<span class="bfh-phone" data-format="+(ddd) ddd-dd-dd"
											data-number="${userProfile.phone}"></span>
									</c:if>
									<br> Registration date:
									<fmt:formatDate value="${userProfile.regDate}" />
									<c:if
										test="${!userOptions.hideCountry}">
										<br> Country: ${userProfile.country} ,</c:if>
									<c:if
										test="${!userOptions.hideCountry}">
										<br> City:
									${userProfile.city}</c:if>
								</address>

								<c:if test="${! (userProfile.userId == user.userId)}">

									<form name="friend" action="./main" method="POST">
										<c:choose>
											<c:when test="${(empty isFriend)}">
												<a class="btn btn-info btn-xs" role="button"
													href="JavaScript:sendForm('friend', 'addfriend')">Add
													to Friends </a>
											</c:when>
											<c:when test="${! (empty isFriend)}">
												<a class="btn btn-warning btn-xs" role="button"
													href="JavaScript:sendForm('friend', 'delfriend')">Remove
													Friend </a>
											</c:when>
										</c:choose>
										<input type="hidden" name="id" value="${userProfile.userId}">
										<input type="hidden" name="action" value=""> <input
											type="hidden" name="token" value="${token}">
									</form>

									<button type="button" class="btn btn-info btn-xs"
										data-toggle="modal" data-target="#sendMessage">Send
										Message</button>
									<button type="button" class="btn btn-info btn-xs"
										data-toggle="modal" data-target="#presentGift">Present
										Gift</button>
								</c:if>
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
									src='<c:out value="${userProfile.avatar}" default="images/default-avatar.jpg"/>'
									style="width: 150px; height: 150px;">
							</div>
						</div>
					</div>
				</div>


				<c:if test="${!userOptions.hideGroups}">
					<!-- user Groups -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">User groups</h3>
						</div>
						<c:if test="${! (empty requestScope.userGroups)}">
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
									<c:forEach var="userGroup" items="${requestScope.userGroups}">
										<customtags:printGroup group="${userGroup}" />
									</c:forEach>
								</tbody>
							</table>
						</c:if>
					</div>

				</c:if>

				<c:if test="${!userOptions.hideFriends }">
					<!-- user Friends -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">User friends</h3>
						</div>
						<c:if test="${! (empty requestScope.userFriends)}">
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
											<td><a href="./main?action=profile&id=${friend.userId}">
													<img class="media-object avatar img-circle"
													src='<c:out value="${friend.avatar}" default="images/default-avatar.jpg"/>'
													style="width: 64px; height: 64px;">
											</a></td>
											<td>${friend.firstName}${friend.lastName}</td>
											<td>${friend.country}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
					</div>

				</c:if>

				<!-- user Presents -->

				<c:if test="${!userOptions.hideGifts}">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">User's presents</h3>
						</div>
						<c:if test="${! (empty requestScope.userPresents)}">
							<table id="usergifts" class="table table-hover">
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
									<c:forEach var="userPresent"
										items="${requestScope.userPresents}">
										<tr>
											<td><a
												href="./main?action=gift&id=${userPresent.gift.giftId}">
													<img class="media-object avatar img-circle"
													src='<c:out value="${userPresent.gift.image}" default="images/defaultgift.png"/>'
													style="width: 64px; height: 64px;">
											</a></td>
											<td>${userPresent.gift.name}</td>
											<td>${userPresent.sender.firstName}
												${userPresent.sender.lastName}</td>
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

				</c:if>

				<!-- Modal Send Message-->
				<div id="sendMessage" class="modal fade" role="dialog">
					<div class="modal-dialog">

						<!-- Modal content-->
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Send Message</h4>
							</div>

							<form method="POST" action="./main">
								<input type="hidden" name="token" value="${token}"> <input
									type="hidden" name="action" value="sendmessage"> <input
									type="hidden" name="idTo" value="${userProfile.userId}">

								<div class="modal-body">
									<div class="form-group">
										<label class="control-label">User name:
											${userProfile.firstName} ${userProfile.lastName}</label>
									</div>
									<div class="form-group">
										<textarea class="form-control" name="body" maxlength="1000"
											required></textarea>
									</div>

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
									<button type="submit" class="btn btn-primary">Send</button>
								</div>
							</form>

						</div>
					</div>
				</div>

				<!-- Modal send Present-->
				<div id="presentGift" class="modal fade" role="dialog">
					<div class="modal-dialog">

						<!-- Modal content-->
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Send a present</h4>
							</div>

							<form method="POST" name="presentForm" action="./main">
								<input type="hidden" name="token" value="${token}"> <input
									type="hidden" name="action" value="present"> <input
									type="hidden" name="idTo" value="${userProfile.userId}">
								<input type="hidden" name="giftId" value="">

								<div class="modal-body">
									<div class="form-group">
										<label class="control-label">User name:
											${userProfile.firstName} ${userProfile.lastName}</label>
									</div>
									<div class="dropdown">
										<button class="btn btn-default dropdown-toggle" type="button"
											id="dropdownMenu1" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="true">
											Gifts <span class="caret"></span>
										</button>
										<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
											<c:forEach var="gift" items="${requestScope.gifts}">
												<li><a href="#"
													onclick="setFieldValue('presentForm', 'giftId','${gift.giftId}'); 
												 						 submitTo('presentForm', './main');">${gift.name}</a></li>
											</c:forEach>
										</ul>
									</div>
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
				$('#sendMessage').modal('show');
				$('#presentGift').modal('show');
			});
		</script>
	</c:if>
</body>
</html>