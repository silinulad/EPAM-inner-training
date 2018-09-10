package service.action.post;

import static service.action.common.Constants.USER_PARAM;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.impl.PostDAOImpl;
import database.dao.interfaces.PostDAO;
import exception.DaoException;
import exception.UploadException;
import model.Post;
import model.User;
import service.action.common.Action;

public class SendPost implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws DaoException, ServletException, IOException, UploadException {
		HttpSession session = request.getSession();
		User author = (User) session.getAttribute(USER_PARAM);
		
		PostDAO postDao = new PostDAOImpl();
		
		String body = request.getParameter("body");
		Post post = new Post.Builder().setAuthor(author).setBody(body).build();
		
		postDao.create(post);
		return "/main?action=profile";
	}
}
