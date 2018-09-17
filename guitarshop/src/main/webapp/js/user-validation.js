var EMPTY_STRING = '';
var LOGIN_PATTERN = /^.{3,}$/g;
var PASSWORD_PATTERN = /^.{6,}$/g;
var TAIL_LOGIN = ' have to consist at least 3 characters</div>';
var TAIL_PASSWORD = ' have to consist at least 6 characters</div>';
var TAIL = ' is empty or consists only of spaces</div>';

function trimStr(str) {
	var SPACE_PATTERN = /^(\s|\u00A0)+|(\s|\u00A0)+$/g;
	return str.replace(SPACE_PATTERN, EMPTY_STRING);
}

function registerValidation(idErrBlock, idLoginField, idPassField,
		idRetypePassField, idEmailField) {
	
	var HEAD = '<div class="additional-page-main-error">';
	var EMPTY_LOGIN = 'Field login ';
	var EMPTY_PASSWORD = 'Field password ';
	var EMPTY_RETYPE_PASS = 'Field retype password ';
	var EMPTY_EMAIL = 'Field email ';
	var DONT_MATCH = 'Passwords do not match</div>';

	var errBlock = document.getElementById(idErrBlock);
	
	errBlock.innerHTML = EMPTY_STRING;
	
	var fieldLogin = document.getElementById(idLoginField);
	var trimmedLogin = trimStr(fieldLogin.value);
	
	if (trimmedLogin == EMPTY_STRING) {
		errBlock.innerHTML = HEAD + EMPTY_LOGIN + TAIL;
		fieldLogin.value = trimmedLogin;
		return false;
	}
	
	var fieldPass = document.getElementById(idPassField);
	var trimmedPass = trimStr(fieldPass.value);
	
	if (trimmedPass == EMPTY_STRING) {
		errBlock.innerHTML = HEAD + EMPTY_PASSWORD + TAIL;
		fieldPass.value = trimmedPass;
		return false;
	}
	
	var fieldRetypePass = document.getElementById(idRetypePassField);
	var trimmedRetypePass = trimStr(fieldRetypePass.value);
	
	if (trimmedRetypePass == EMPTY_STRING) {
		errBlock.innerHTML = HEAD + EMPTY_RETYPE_PASS + TAIL;
		fieldRetypePass.value = trimmedRetypePass;
		return false;
	}
	
	var fieldEmail = document.getElementById(idEmailField);
	var trimmedEmail = trimStr(fieldEmail.value);
	
	if (trimmedEmail == EMPTY_STRING) {
		errBlock.innerHTML = HEAD + EMPTY_EMAIL + TAIL;
		fieldEmail.value = trimmedEmail;
		return false;
	}
	
	if (trimmedPass != trimmedRetypePass) {
		errBlock.innerHTML = HEAD + DONT_MATCH;
		return false;
	}
	
	var loginRegExp = new RegExp(LOGIN_PATTERN);
	var passRegExp = new RegExp(PASSWORD_PATTERN);
	
	if (!loginRegExp.test(trimmedLogin)) {
		errBlock.innerHTML = HEAD + TAIL_LOGIN;
		return false;
	}
	
	if (!passRegExp.test(trimmedPass)) {
		errBlock.innerHTML = HEAD + TAIL_PASSWORD;
		return false;
	}
	return true;
}

function loginValidation(idErrBlock, idField1, fieldName1, idField2, fieldName2) {
	
	var HEAD = '<div class="additional-page-main-error">Field ';

	var errBlock = document.getElementById(idErrBlock);
	
	errBlock.innerHTML = EMPTY_STRING;
	
	var field1 = document.getElementById(idField1);
	var fieldTrimmedValue1 = trimStr(field1.value);
	
	if (fieldTrimmedValue1 == EMPTY_STRING) {
		errBlock.innerHTML = HEAD + fieldName1 + TAIL;
		field1.value = fieldTrimmedValue1;
		return false;
	}

	var field2 = document.getElementById(idField2);
	var fieldTrimmedValue2 = trimStr(field2.value);
	
	if (fieldTrimmedValue2 == EMPTY_STRING) {
		errBlock.innerHTML = HEAD + fieldName2 + TAIL;
		field2.value = fieldTrimmedValue2;
		return false;
	}

	var loginRegExp = new RegExp(LOGIN_PATTERN);
	var passRegExp = new RegExp(PASSWORD_PATTERN);

	if (!loginRegExp.test(fieldTrimmedValue1)) {
		errBlock.innerHTML = HEAD + fieldName1 + TAIL_LOGIN;
		return false;
	}

	if (!passRegExp.test(fieldTrimmedValue2)) {
		errBlock.innerHTML = HEAD + fieldName1 + TAIL_PASSWORD;
		return false;
	}
	return true;
}