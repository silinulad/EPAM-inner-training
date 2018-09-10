<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://earlgray.com/jsp/tags/customtags"
	prefix="customtags"%>
<c:set var="userSettings"
	value="${not empty requestScope.userSettings ? requestScope.userSettings : sessionScope.user}"
	scope="page" />
<!DOCTYPE html>
<html lang="${language}">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>EarlGray - Settings page</title>

<link href="css/navbar.css" rel="stylesheet">
<link href="css/footer.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/bootstrap-formhelpers.min.css" rel="stylesheet">
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
						<h3 class="panel-title">Profile settings</h3>
					</div>

					<div class="panel-body">

						<form role="form" class="col-md-4" method="POST" action="./main"
							name="setting">
							<input type="hidden" name="token" value="${token}"> <input
								type="hidden" name="action" value="editprofile">

							<div class="form-group">
								<label class="control-label">Email:</label>
								<div class="input-group">
									<span class="input-group-addon"> <i
										class="glyphicon glyphicon-envelope"></i>
									</span> <input class="form-control" name="email" maxlength="254"
										type="email" value="<c:out value="${userSettings.email}"/>"
										required>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">First name:</label>
								<div class="input-group">
									<span class="input-group-addon"> <i
										class="glyphicon glyphicon-user"></i>
									</span> <input class="form-control" name="firstname" maxlength="35"
										value="${userSettings.firstName}" required>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">Last name:</label>
								<div class="input-group">
									<span class="input-group-addon"> <i
										class="glyphicon glyphicon-user"></i>
									</span> <input class="form-control" name="lastname" maxlength="35"
										value="${userSettings.lastName}" required>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">Sex:</label>
								<div class="input-group">
									<span class="input-group-addon"> <i
										class="glyphicon glyphicon-user"></i>
									</span> <select class="form-control" name="sex">
										<option value="m"
											<c:if test="${userSettings.gender.toString() eq 'm'}">selected</c:if>>Male
										</option>
										<option value="f"
											<c:if test="${userSettings.gender.toString() eq 'f'}">selected</c:if>>Female
										</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">Phone:</label>
								<div class="input-group">
									<span class="input-group-addon"><i
										class="glyphicon glyphicon-earphone"></i></span> <input name="phone"
										id="phone" class="form-control bfh-phone" type="text"
										value='${userSettings.phone}' data-format="+ddd dd ddd-dd-dd"
										required>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">Date of Birth:</label>
								<div class="input-group">
									<div class="bfh-datepicker" data-name="birthdate"
										id="birthdate"
										data-date='<fmt:formatDate value="${userSettings.birthDate}" pattern="dd.MM.yyyy"/>'
										data-format="d.m.y" data-min="01/01/1900" data-max="today"
										required></div>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">Country:</label>
								<div class="input-group">
									<span class="input-group-addon"> <i
										class="glyphicon glyphicon-globe"></i>
									</span> <input class="form-control" name="country" maxlength="35"
										value="<c:out value="${userSettings.country}"/>" required>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label">City:</label>
								<div class="input-group">
									<span class="input-group-addon"> <i
										class="glyphicon glyphicon-tent"></i>
									</span> <input class="form-control" name="city" maxlength="35"
										value="<c:out value="${userSettings.city}"/>" required>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label">Photo:</label>
								<div class="input-group">
									<div class="input-group-btn">
										<button type="button" class="btn btn-lnk"
											onclick="document.getElementById('inputFile').click();">
											<span class="glyphicon glyphicon-picture" aria-hidden="true"></span>
										</button>

										<input type="file" class="form-control-file" id="inputFile"
											aria-describedby="fileHelp" style="display: none;"
											name="file" accept="image/jpeg,image/png,image/gif"
											onchange="setFieldValue('setting', 'action','uploadimage'); 
												  setBinaryEnctype('setting');
												  submitTo('setting', './file');" />
									</div>
									<p class="help-block">Change photo</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-3 control-label"></label>
								<div class="col-md-8">
									<button class="btn btn-primary" type="submit">Save
										changes</button>
								</div>
							</div>
						</form>


						<form role="form" class="col-md-4" method="POST" action="./main"
							name="options">
							<input type="hidden" name="token" value="${token}"> <input
								type="hidden" name="action" value="editoptions">
							<div class="form-group">
								<label class="control-label">Show Date of Birth</label>
								<div class="input-group">
									<span class="input-group-addon"> <i
										class="glyphicon glyphicon-user"></i>
									</span> <select class="form-control" name="sex">
										<option value="l"
											<c:if test="${userOptions.showDate.toString() eq 'l'}">selected</c:if>>Whole
											Date</option>
										<option value="s"
											<c:if test="${userOptions.showDate.toString() eq 's'}">selected</c:if>>Only
											Years</option>
										<option value="n"
											<c:if test="${userOptions.showDate.toString() eq 'n'}">selected</c:if>>Hide
										</option>
									</select>
								</div>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox"
									onclick="document.getElementById('1').value = this.value" /> <input
									type="hidden" name="onlyfriends" id="1"
									<c:if test="${userOptions.visiblePageOnlyFriends}">checked="checked"</c:if>
									value="${userOptions.visiblePageOnlyFriends}" /> My page is
									visible only to friends
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox"
									onclick="document.getElementById('2').value = this.value"
									<c:if test="${userOptions.hideFriends}">checked="checked"</c:if> />
									<input type="hidden" name="hidefriends" id="2"
									value="${userOptions.hideFriends}" /> Hide my friends
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox"
									onclick="document.getElementById('3').value = this.value"
									<c:if test="${userOptions.hideGroups}">checked="checked"</c:if> />
									<input type="hidden" name="hidegroups" id="3"
									value="${userOptions.hideGroups}" /> Hide my groups
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox"
									onclick="document.getElementById('4').value = this.value"
									<c:if test="${userOptions.hideGifts}">checked="checked"</c:if> />
									<input type="hidden" name="hidepresents" id="4"
									value="${userOptions.hideGifts}" /> Hide my presents
								</label>
							</div>

							<div class="checkbox">
								<label> <input type="checkbox"
									onclick="document.getElementById('5').value = this.value"
									<c:if test="${userOptions.hideCountry}">checked="checked"</c:if> />
									<input type="hidden" name="hidecountry" id="5"
									value="${userOptions.hideCountry}" /> Hide my country
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox"
									onclick="document.getElementById('6').value = this.value"
									<c:if test="${userOptions.hideCitiy}">checked="checked"</c:if> />
									<input type="hidden" name="hidecity" id="6"
									value="${userOptions.hideCitiy}" /> Hide my city
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox"
									onclick="document.getElementById('7').value = this.value"
									<c:if test="${userOptions.hideSex}">checked="checked"</c:if> />
									<input type="hidden" name="hidesex" id="7"
									value="${userOptions.hideSex}" /> Hide my sex
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox"
									onclick="document.getElementById('8').value = this.value"
									<c:if test="${userOptions.hidePhone}">checked="checked"</c:if> />
									<input type="hidden" name="hidephone" id="8"
									value="${userOptions.hidePhone}" /> Hide my phone
								</label>
							</div>

							<div class="form-group">
								<label class="col-md-3 control-label"></label>
								<div class="col-md-8">
									<button class="btn btn-primary" type="submit">Save
										changes</button>
								</div>
							</div>
						</form>


						<!-- change password -->
						<form role="form" class="col-md-4" method="POST" action="./main">
							<div class="form-group">
								<label class="control-label">Old password:</label>
								<div class="input-group">
									<span class="input-group-addon"> <i
										class="glyphicon glyphicon-lock"></i>
									</span> <input class="form-control" name="old_password" maxlength="40"
										type="password" required>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">New Password:</label>
								<div class="input-group">
									<span class="input-group-addon"> <i
										class="glyphicon glyphicon-lock"></i>
									</span> <input class="form-control" name="new_password" maxlength="40"
										type="password" required>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">Repeat password:</label>
								<div class="input-group">
									<span class="input-group-addon"> <i
										class="glyphicon glyphicon-lock"></i>
									</span> <input class="form-control" name="repeat_password"
										maxlength="40" type="password" required>
								</div>
							</div>
							<input type="hidden" name="action" value="changepassword">
							<input type="hidden" name="token" value="${token}">
							<div class="form-group">
								<label class="col-md-3 control-label"></label>
								<div class="col-md-8">
									<button class="btn btn-primary" type="submit">Change
										password</button>
								</div>
							</div>
						</form>
					</div>
					<c:if test="${! (empty errorMsg)}">
						<br>
						<div style="margin-left: 30px; margin-right: 30px;">
							<customtags:printMessage message="${errorMsg}" type="error" />
						</div>

					</c:if>
					<c:if test="${! (empty successMsg)}">
						<br>
						<div style="margin-left: 30px; margin-right: 30px;">
							<customtags:printMessage message="${successMsg}" type="success" />
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>

	<script src="js/jquery.min.js"></script>
	<script src="js/jquery-3.3.1.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dataTables.bootstrap.min.js"></script>
	<script src="js/bootstrap-formhelpers.min.js"></script>
	<script src="js/project-scripts.js"></script>
	<script src="js/send.js"></script>
</body>
</html>