package service.action.common;

import service.action.file.DeleteFile;
import service.action.file.DownloadFile;
import service.action.file.FileUpload;
import service.action.friend.AddFriend;
import service.action.friend.RemoveFriend;
import service.action.friend.ViewFriends;
import service.action.gift.*;
import service.action.group.*;
import service.action.image.ChangeImage;
import service.action.image.ImageUpload;
import service.action.message.*;
import service.action.post.DeletePost;
import service.action.post.SendPost;
import service.action.profile.EditProfile;
import service.action.profile.EditSettings;
import service.action.profile.ViewProfile;
import service.action.setting.ChangePassword;
import service.action.setting.ViewSettings;
import service.action.user.ViewUsers;

/**
 * Created by Silin on 07.2018.
 */

/**
 * ActionProvider provides mapping for the action request parameter
 * corresponding to the Action Object
 */
public enum ActionProvider {
   //forward to *.jsp
	VIEW_USERS("users", new ViewUsers(), false),
    VIEW_GROUPS("groups", new ViewGroups(), false),
    VIEW_GROUP("group", new ViewGroup(), false),
    VIEW_FRIENDS("friends", new ViewFriends(), false),
    VIEW_PROFILE("profile", new ViewProfile(), false),
    VIEW_SETTINGS("settings", new ViewSettings(), false),
	VIEW_MESSAGES("messages", new ViewMessages(), false),
	VIEW_DIALOG("dialog", new ViewDialog(), false),
	VIEW_GIFTS("gifts", new ViewGifts(), false),
	VIEW_GIFT("gift", new ViewGift(), false),
   
	//forward to main servlet
	EDIT_GROUP("editgroup", new EditGroup(), false),
	EDIT_GIFT("editgift", new EditGift(), false),
	EDIT_PROFILE("editprofile", new EditProfile(), false), 
	CHANGE_PASSWORD("changepassword", new ChangePassword(), false),
    EDIT_OPTIONS("editoptions", new EditSettings(), false), 
    JOIN_GROUP("joingroup", new JoinGroup(), false),
    LEFT_GROUP("leavegroup", new LeaveGroup(), false),
    DELETE_FRIEND("delfriend", new RemoveFriend(), false),
    DELETE_GROUP("delgroup", new DeleteGroup(), false),
	DELETE_MESSAGE("delmessage", new DeleteMessage(), false),
	DELETE_DIALOG("deldialog", new DeleteDialog(), false),
	DELETE_FILE("deletefile", new DeleteFile(), false),
	DELETE_PRESENT("delpresent", new DeletePresent(), false),
	
	// can add same action to the database, so redirect to the main servlet
	ADD_FRIEND("addfriend", new AddFriend(), true),
	SEND_POST("sendpost", new SendPost(), true),
	DELETE_POST("delpost", new DeletePost(), true),
	UPLOAD_IMAGE("uploadimage", new ImageUpload(), true),
	CHANGE_IMAGE("changeimage", new ChangeImage(), true),
	ADD_GIFT("addgift", new AddGift(), true),
	SEND_PRESENT("present", new SendPresent(), true),
	ADD_GROUP("addgroup", new AddGroup(), true),
	SEND_MESSAGE("sendmessage", new SendMessage(), true),
	UPLOAD_FILE("uploadfile", new FileUpload(), true),
	DOWNLOAD_FILE("downfile", new DownloadFile(), true);

    private final String name;

    private final Action action;
    private final boolean isRepeatsAdding;

	private ActionProvider(String name, Action action, boolean isRepeatsAdding) {
		this.name = name;
		this.action = action;
		this.isRepeatsAdding = isRepeatsAdding;
	}
	public String getName() {
		return name;
	}
	public Action getAction() {
		return action;
	}
	public boolean isRepeatsAdding() {
		return isRepeatsAdding;
	}

   
}