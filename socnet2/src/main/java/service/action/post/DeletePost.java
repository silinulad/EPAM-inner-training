package service.action.post;

import static service.MessageProvider.*;
import static service.action.common.Constants.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.impl.PostDAOImpl;
import database.dao.interfaces.GenericDAO;
import exception.DaoException;
import model.Post;
import model.User;
import service.InputValidator;
import service.action.common.Action;
/**
 * Created by Silin on 08.2018.
 */


/**
 * DeletePost action processes the post author request to delete the post
 * information
 */
public class DeletePost implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws DaoException, ServletException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER_PARAM);

		GenericDAO<Post> postDao = new PostDAOImpl();

		String idParameter = request.getParameter("id");

		if (InputValidator.validateId(idParameter)) {
			long postId = Long.parseLong(idParameter);
			Post post = postDao.read(postId);

			if (post != null) {
				// check that user is the post author
				if ((post.getAuthor().equals(user))) {
					postDao.delete(post);
					request.setAttribute(SUCCESS_PARAM, GROUP_DELETED_SUCCESS);
					return "/main";
				} else {
					request.setAttribute(ERROR_PARAM, INSUFFICIENT_RIGHTS_ERROR);
				}
			} else {
				request.setAttribute(ERROR_PARAM, GROUP_NOT_FOUND_ERROR);
			}
		} else {
			request.setAttribute(ERROR_PARAM, GROUP_ID_ERROR);
		}
		return MAIN;
	}

}
