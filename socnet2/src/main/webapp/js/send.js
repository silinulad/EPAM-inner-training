function sendForm(formName, type) {
	if (!document.forms[formName]) {
		return;
	}
	document.forms[formName].action.value = type;
	document.forms[formName].submit();
}