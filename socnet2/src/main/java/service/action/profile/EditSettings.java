package service.action.profile;

import static service.action.common.Constants.SETTINGS;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.impl.UserSettingDAOImpl;
import database.dao.interfaces.GenericDAO;
import exception.DaoException;
import exception.UploadException;
import model.User;
import model.UserSettings;
import service.action.common.Action;

public class EditSettings implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws DaoException, ServletException, IOException, UploadException {
		String checked = "on";
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		GenericDAO<UserSettings> userOptionsDao = new UserSettingDAOImpl();
		
		String friends = request.getParameter("hidefriends");
		String groups = request.getParameter("hidegroups");
		String presents = request.getParameter("lastname");
		String sex = request.getParameter("hidesex");
		String phone = request.getParameter("hidephone");
		String country = request.getParameter("hidecountry");
		String city = request.getParameter("hidecity");
		
		boolean isHideFriends = false;
		boolean isHideGroups = false;
		boolean isHidePresents = false;
		boolean isHideSex = false;
		boolean isHidePhone = false;
		boolean ishideCountry = false;
		boolean isHideCity = false;

		// validate form input
		
		if (checked.equals(friends)) {
			isHideFriends = true;
		}
		if (checked.equals(groups)) {
			isHideGroups = true;
		}
		if (checked.equals(presents)) {
			isHidePresents = true;
		}
		if (checked.equals(sex)) {
			isHideSex = true;
		}
		if (checked.equals(phone)) {
			isHidePhone = true;
		}
		if (checked.equals(country)) {
			ishideCountry = true;
		}
		if (checked.equals(city)) {
			isHideCity = true;
		}
		UserSettings userOptions = new UserSettings.Builder()
									.setHideFriends(isHideFriends)
									.setHideGroups(isHideGroups)
									.setHideGifts(isHidePresents)
									.setHideSex(isHideSex)
									.setHidePhone(isHidePhone)
									.setHideCountry(ishideCountry)
									.setHideCitiy(isHideCity)
									.setUserId(user.getUserId())
									.setShowDate("l")
									.build();
		
		userOptionsDao.update(userOptions);
	
		return SETTINGS;
	}

}
