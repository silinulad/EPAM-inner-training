package tag;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import service.MessageProvider;

/**
 * Created by Silin on 08.2018.
 */

/**
 * MessageTag prints translated error and information server messages
 */
public class MessageTag extends TagSupport {

	private static final long serialVersionUID = -4613079289110932393L;

	private MessageProvider message;

	// type of the message, can be "error" or "success"
	private String type;

	public void setMessage(MessageProvider message) {
		this.message = message;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpSession session = request.getSession(true);
		String language = (String) session.getAttribute("language");
		String alertType;

		if (language == null || language.isEmpty()) {
			language = "en";
		}

		// Apply alert type styles
		switch (type) {
		case "error":
			alertType = "alert-danger";
			break;
		case "success":
			alertType = "alert-success";
			break;
		default:
			alertType = "alert-info";
		}

		try {
			pageContext.getOut().write(String.format("<div class=\"alert %s\">\n"
					+ "                    <span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
					+ "                    <a class=\"close\" data-dismiss=\"alert\" href=\"#\">×</a>%s\n"
					+ "                </div>", alertType, message.getLocalized(new Locale(language))));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SKIP_BODY;
	}
}
