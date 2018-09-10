package tag;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import model.Message;
import model.User;

public class UserMessagesTableTag extends TagSupport {

	private static final long serialVersionUID = -5272432001854635359L;
	private Message userMessage;

	public void setUserMessage(Message userMessage) {
		this.userMessage = userMessage;
	}

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpSession session = request.getSession(false);
		User sessionUser = (User)session.getAttribute("user");
		
		String messageBody = userMessage.getBody();
		int maxBodyLength = 64;
		String shortMessageBody = messageBody.length() < maxBodyLength ? messageBody
				: messageBody.substring(0, maxBodyLength - 1) + "...";

		DateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String messageTime = date.format(userMessage.getTime());
		
		long id = userMessage.getRecipient().getUserId();
		String fullName = userMessage.getRecipient().getFirstName() + " " + userMessage.getRecipient().getLastName();
		String avatar = userMessage.getRecipient().getAvatar();
		if (sessionUser.equals(userMessage.getRecipient())) {
			id = userMessage.getSender().getUserId();
			fullName = userMessage.getSender().getFirstName() + " " + userMessage.getSender().getLastName();
			avatar = userMessage.getSender().getAvatar();
		}
		if(avatar ==null) {
			avatar = "images/default-avatar.jpg";
		}

		try {
			pageContext.getOut().write(String.format("<tr>"
										+ "<td>"
										+	"<div class=\"media\">"
										+ 		"<div class=\"media-left\">"
										+ 			"<a href=\"./main?action=dialog&id=%d\">"
										+ 				"<img alt=\"64x64\" class=\"media-object img-circle\" style=\"width: 64px; height: 64px;\" src=\"%s\">"
										+ 			"</a>"
										+ 		"</div>"
										+ 		"<div class=\"media-body\">"
										+ 			"<h5 class=\"media-heading\"> <strong>%s</strong>  <small>%s</small></h5>"
										+ 			"<p>%s</p>"
										+ 		"</div>"
										+	"</div>"
										+ "</td>"
										+ "<td><a title=\"Delete conversation\" href=\"./main?action=deldialog&id=%d\"><span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span></a></td>"
									  + "</tr>",
						id,
						avatar,
						fullName, 
						messageTime, 
						shortMessageBody,
						id));

		} catch (IOException e) {
			e.printStackTrace();
		}

		return SKIP_BODY;
	}
}
