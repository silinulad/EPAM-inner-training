package service;

/**
 * Created by Silin on 08.2018.
 */

import java.util.Locale;

/**
 * MessageProvider provides error and information server messages with current language from LocaleManager
 */
public enum MessageProvider {

    EMAIL_INVALID_ERROR("validator.error.email_invalid"),
    FIRSTNAME_INVALID_ERROR("validator.error.firstname_invalid"),
    LASTNAME_INVALID_ERROR("validator.error.lastname_invalid"),
    PASSWORD_INVALID_ERROR("validator.error.password_invalid"),
    REPEAT_PASSWORD_INVALID_ERROR("validator.error.repeat_password_invalid"),
    SEX_INVALID_ERROR("validator.error.sex_invalid"),
    PHONE_INVALID_ERROR("validator.error.phone_invalid"),
    BIRTHDATE_INVALID_ERROR("validator.error.birthdate_invalid"),
    GROUP_NAME_INVALID_ERROR("validator.error.group_name_invalid"),
	GROUP_DESC_INVALID_ERROR("validator.error.group_desc_invalid"),
    USER_INCORRECT_ERROR("servlet.error.user_incorrect"),
    USER_EXISTS_ERROR("servlet.error.user_exists"),
    DATABASE_ERROR("servlet.error.database"),
    UNKNOWN_ACTION("servlet.error.action"),
    STATUS_403_ERROR("servlet.error.403"),
    STATUS_404_ERROR("servlet.error.404"),
    STATUS_500_ERROR("servlet.error.500"),
    STATUS_UNKNOWN_ERROR("servlet.error.default"),
    OLD_PASSWORD_INVALID_ERROR("action.error.old_password_incorrect"),
    INSUFFICIENT_RIGHTS_ERROR("action.error.insufficient.rights"),
    USER_NOT_FOUND_ERROR("action.error.user_not_found"),
    USER_ID_ERROR("action.error.user_id_incorrect"),
    GROUP_NOT_FOUND_ERROR("action.error.group_not_found"),
    GROUP_ID_ERROR("action.error.group_id_incorrect"),
	GROUP_NOT_JOINED_ERROR("action.error.group_not_joined"),
    GROUP_ALREADY_JOINED_ERROR("action.error.group_already_joined"),
    USER_NOT_FRIEND_ERROR("action.error.user_not_friend_error"),
    USER_IS_FRIEND_ERROR("action.error.user_is_friend_error"),
    PASSWORD_CHANGE_SUCCESSFUL("action.info.password_change_success"),
    PROFILE_CHANGE_SUCCESSFUL("action.info.profile_change_success"),
    USER_ADD_FRIEND_SUCCESS("action.info.user_friend_success"),
    USER_DEL_FRIEND_SUCCESS("action.info.user_delfriend_success"),
    GROUP_JOIN_SUCCESS("action.info.group_join_success"),
    GROUP_LEFT_SUCCESS("action.info.group_left_success"),
    GROUP_DELETED_SUCCESS("action.info.group_deleted_success"),
    GROUP_CREATED_SUCCESS("action.info.group_created_success"),
    GROUP_UPDATED_SUCCESS("action.info.group_updated_success"),
    VALID("validator.error.valid");

    private LocaleManager localeManager = LocaleManager.getInstance();

    private final String propertyKey;

    MessageProvider(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    /**
     * Return translated Message value by Locale
     *
     * @param locale
     * @return value
     */
    public String getLocalized(Locale locale) {
        return localeManager.getValue(propertyKey, locale);
    }

    /**
     * Return message value in English in case MessageTag not used
     *
     * @return String Message
     */
    @Override
    public String toString() {
        return getLocalized(new Locale("en"));
    }
}
