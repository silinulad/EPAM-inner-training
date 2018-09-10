package database.dao.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.*;

/**
 * Created by Silin on 08.2018.
 */

/**
 * DaoUtils provides functionality of building different beans from ResultSets
 */
public class DaoUtils {

    /**
     * Build user from ResultSet parameters
     *
     * @param resultSet
     * @return User
     * @throws SQLException
     */
    public static User buildUser(ResultSet resultSet) throws SQLException {
        return new User.Builder()
                .setUserId(resultSet.getLong("user_id"))
                .setEmail(resultSet.getString("email"))
                .setHash(resultSet.getString("hash"))
                .setFirstName(resultSet.getString("firstname"))
                .setLastName(resultSet.getString("lastname"))
                .setGender(resultSet.getString("gender").charAt(0))
                .setBirthDate(resultSet.getDate("birthdate"))
                .setCountry(resultSet.getString("country"))
                .setCity(resultSet.getString("city"))
                .setPhone(resultSet.getString("phone"))
				.setAvatar(resultSet.getString("avatar"))
                .setRegDate(resultSet.getTimestamp("regdate"))
                .build();
    }


    /**
	 * Build group from ResultSet parameters
	 *
	 * @param resultSet
	 * @return Group
	 * @throws SQLException
	 */
    public static Group buildGroup(ResultSet resultSet) throws SQLException {
        User owner = buildUser(resultSet);
        return new Group.Builder()
				.setGroupId(resultSet.getLong("group_id"))
                .setName(resultSet.getString("name"))
                .setOwner(owner)
                .setDescription(resultSet.getString("description"))
				.setImage(resultSet.getString("image"))
                .setDateCreated(resultSet.getTimestamp("date_created"))
                .build();
    }

    /**
     * Build message from ResultSet parameters
     *
     * @param resultSet
     * @return Message
     * @throws SQLException
     */
	public static Message buildMessage(ResultSet resultSet) throws SQLException {
		User sender = buildUserSender(resultSet);
		User recipient = buildUserRecipient(resultSet);
        return new Message.Builder()
                .setMessageId(resultSet.getLong("message_id"))
                .setSender(sender)
                .setRecipient(recipient)
                .setBody(resultSet.getString("body"))
                .setTime(resultSet.getTimestamp("time"))
                .build();
    }
   
    /**
     * Build user sending a message from ResultSet parameters
     *
     * @param resultSet
     * @return User
     * @throws SQLException
     */
    public static User buildUserSender(ResultSet resultSet) throws SQLException {
        return new User.Builder()
                .setUserId(resultSet.getLong(6))
                .setEmail(resultSet.getString(7))
                .setHash(resultSet.getString(8))
                .setFirstName(resultSet.getString(9))
                .setLastName(resultSet.getString(10))
                .setGender(resultSet.getString(11).charAt(0))
                .setBirthDate(resultSet.getDate(12))
                .setCountry(resultSet.getString(13))
                .setCity(resultSet.getString(14))
                .setPhone(resultSet.getString(15))
				.setAvatar(resultSet.getString(16))
                .setRegDate(resultSet.getTimestamp(17))
                .build();
    }

    /**
     * Build user receiving a message from ResultSet parameters
     *
     * @param resultSet
     * @return User
     * @throws SQLException
     */
    public static User buildUserRecipient(ResultSet resultSet) throws SQLException {
        return new User.Builder()
        		 .setUserId(resultSet.getLong(18))
                 .setEmail(resultSet.getString(19))
                 .setHash(resultSet.getString(20))
                 .setFirstName(resultSet.getString(21))
                 .setLastName(resultSet.getString(22))
                 .setGender(resultSet.getString(23).charAt(0))
                 .setBirthDate(resultSet.getDate(24))
                 .setCountry(resultSet.getString(25))
                 .setCity(resultSet.getString(26))
                 .setPhone(resultSet.getString(27))
                 .setAvatar(resultSet.getString(28))
                 .setRegDate(resultSet.getTimestamp(29))
                 .build();
    }
    
    /**
     * Build file from ResultSet parameters
     *
     * @param resultSet
     * @return File
     * @throws SQLException
     */
    public static AttachFile buildFile(ResultSet resultSet) throws SQLException {
        return new AttachFile.Builder()
                .setFileId(resultSet.getLong("file_id"))
                .setOriginName(resultSet.getString("name_or"))
                .setUploadedName(resultSet.getString("name_up"))
				.setPath(resultSet.getString("path"))
                .setDateUploaded(resultSet.getTimestamp("time"))
                .build();
    }
    
    /**
	 * Build gift from ResultSet parameters
	 *
	 * @param resultSet
	 * @return Gift
	 * @throws SQLException
	 */
	public static Gift buildGift(ResultSet resultSet) throws SQLException {
		return new Gift.Builder().setGiftId(resultSet.getLong("gift_id"))
                .setName(resultSet.getString("name"))
                .setDescription(resultSet.getString("description"))
				.setImage(resultSet.getString("image"))
                .setDateCreated(resultSet.getTimestamp("date_created"))
                .build();
    }
	
	 /**
     * Build post from ResultSet parameters
     *
     * @param resultSet
     * @return Post
     * @throws SQLException
     */
	public static Post buildPost(ResultSet resultSet) throws SQLException {
		 User author = buildUser(resultSet);
        return new Post.Builder()
                .setPostId(resultSet.getLong("post_id"))
                .setAuthor(author)
                .setBody(resultSet.getString("body"))
                .setTime(resultSet.getTimestamp("time"))
                .build();
    }
	
	 /**
		 * Build present from ResultSet parameters
		 *
		 * @param resultSet
		 * @return Present
		 * @throws SQLException
		 */
		public static Present buildPresent(ResultSet resultSet) throws SQLException {
	        Gift gift = buildGift(resultSet);
	        User sender = buildUser(resultSet);
			return new Present.Builder()
					.setPresentId(resultSet.getLong("present_id"))
	                .setGift(gift)
	                .setSender(sender)
	                .setTime(resultSet.getTimestamp("time"))
	                .build();
	    }
		
		  /**
	     * Build user settings from ResultSet parameters
	     *
	     * @param resultSet
	     * @return UserSettings
	     * @throws SQLException
	     */
	public static UserSettings buildUserSettings(ResultSet resultSet) throws SQLException {
	        return new UserSettings.Builder()
					.setUserId(resultSet.getLong("user_id"))
					.setVisiblePageOnlyFriends(resultSet.getBoolean("visible_only_friends"))
					.setHideFriends(resultSet.getBoolean("hide_friends"))
					.setHideGroups(resultSet.getBoolean("hide_groups"))
					.setHideGifts(resultSet.getBoolean("hide_presents"))
					.setShowDate(resultSet.getString("show_date"))
					.setHideCountry(resultSet.getBoolean("hide_country"))
					.setHideCitiy(resultSet.getBoolean("hide_city"))
					.setHideSex(resultSet.getBoolean("hide_sex"))
					.setHidePhone(resultSet.getBoolean("hide_phone"))
	                .build();
	    }
}
