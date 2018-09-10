package tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import model.Group;

/**
 * Created by Silin on 08.2018.
 */

/**
 * GroupTableTag prints a single group information as a part of the table on the
 * page
 */
public class GroupTableTag extends TagSupport {

	
	private static final long serialVersionUID = 6283270382580875455L;
	private Group group;

	public void setGroup(Group group) {
		this.group = group;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().write(String.format("                    <tr>\n"
					+ "                        <td>%s</td>\n" + "                        <td>%s</td>\n"
					+ "                        <td><a href=\"./main?action=profile&id=%d\">%s %s</a></td>\n"
					+ "                        <td><a href=\"./main?action=group&id=%d\">View Group</a></td>\n"
					+ "                    </tr>", group.getName(), group.getDescription(),
					group.getOwner().getUserId(), group.getOwner().getFirstName(), group.getOwner().getLastName(),
					group.getGroupId()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SKIP_BODY;
	}
}
