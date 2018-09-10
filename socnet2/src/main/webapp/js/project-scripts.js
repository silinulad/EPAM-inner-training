function setBinaryEnctype(formName) {
	if (!document.forms[formName]) {
		return;
	}
	document.forms[formName].enctype = 'multipart/form-data';
}

function setFieldValue(formName, fieldName, fieldValue) {
	if (!document.forms[formName])
		return;
	document.forms[formName][fieldName].value = fieldValue;
}

function submitTo(formName, servlet) {
	if (!document.forms[formName]) {
		return;
	}
	document.forms[formName].action = servlet;
	document.forms[formName].submit();
}