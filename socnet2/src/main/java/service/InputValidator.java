package service;

import static service.MessageProvider.BIRTHDATE_INVALID_ERROR;
import static service.MessageProvider.EMAIL_INVALID_ERROR;
import static service.MessageProvider.FIRSTNAME_INVALID_ERROR;
import static service.MessageProvider.GROUP_DESC_INVALID_ERROR;
import static service.MessageProvider.GROUP_NAME_INVALID_ERROR;
import static service.MessageProvider.LASTNAME_INVALID_ERROR;
import static service.MessageProvider.PASSWORD_INVALID_ERROR;
import static service.MessageProvider.PHONE_INVALID_ERROR;
import static service.MessageProvider.REPEAT_PASSWORD_INVALID_ERROR;
import static service.MessageProvider.SEX_INVALID_ERROR;
import static service.MessageProvider.VALID;

import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Created by Silin on __.07.2018.
 */

/**
 * InputValidator provides functionality for testing user input such as email,
 * phone, name, etc Returns error code reason if validation fails
 */
public class InputValidator {

	private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();
	private static final DateValidator DATE_VALIDATOR = DateValidator.getInstance();

	private static final String NAME_REGEX = "^\\pL*";
	private static final String ID_REGEX = "^[0-9]+$";
	private static final String PHONE_REGEX = "\\+\\d{3}\\s\\d{2}\\s\\d{3}-\\d{2}-\\d{2}";
	private static final String GROUP_GIFT_NAME_REGEX = "^\\pL[\\pL\\pZ]*";
	private static final String GROUP_GIFT_DESCRIPTION_REGEX = "^[\\pL\\pZ\\pN\\pP]*";

	/**
	 * Validate email string using Apache Commons EmailValidator
	 *
	 * @param email
	 * @return true if email is valid
	 */
	public static boolean validateEmail(String email) {
		return EMAIL_VALIDATOR.isValid(email);
	}

	/**
	 * Validate name length and allowed characters (only Unicode)
	 *
	 * @param name
	 * @return true if name is valid
	 */
	public static boolean validateName(String name) {
		return name != null && !name.isEmpty() && name.length() <= 35 && name.matches(NAME_REGEX);
	}

	/**
	 * Validate password length
	 *
	 * @param password
	 * @return true if password is valid
	 */
	public static boolean validatePassword(String password) {
		return password != null && !password.isEmpty() && password.length() <= 40 && password.length() >= 6;
	}

	/**
	 * Validate id
	 *
	 * @param id
	 * @return true if id is valid
	 */
	public static boolean validateId(String id) {
		return id != null && !id.isEmpty() && id.matches(ID_REGEX);
	}

	/**
	 * Validate user sex
	 *
	 * @param sex
	 * @return true if sex is valid
	 */
	public static boolean validateSex(String sex) {
		return (sex != null && !sex.isEmpty() && sex.length() == 1) && (sex.equals("m") || sex.equals("f"));
	}

	/**
	 * Validate user phone
	 *
	 * @param phone
	 * @return true if phone is valid
	 */
	public static boolean validatePhone(String phone) {
		return phone != null && !phone.isEmpty() && phone.length() == 17 && phone.matches(PHONE_REGEX);
	}

	/**
	 * Validate user birth date using Apache Common DateValidator
	 *
	 * @param birthDate
	 * @return true if birth date is valid
	 */
	public static boolean validateBirthDate(String birthDate) {
		return DATE_VALIDATOR.isValid(birthDate);
	}

	/**
	 * Validate group or gift name
	 *
	 * @param name
	 * @return true if name is valid
	 */
	public static boolean validateGroupOrGiftName(String name) {
		return name != null && !name.isEmpty() && name.length() <= 35 && name.matches(GROUP_GIFT_NAME_REGEX);
	}

	/**
	 * Validate group or gift description
	 *
	 * @param description
	 * @return true if valid
	 */
	public static boolean validateGroupOrGiftDescription(String description) {
		return description != null && !description.isEmpty() && description.length() <= 255
				&& description.matches(GROUP_GIFT_DESCRIPTION_REGEX);
	}

	/**
	 * Validate login form parameters
	 *
	 * @param email
	 * @param password
	 * @return valid information code if valid
	 */
	public static MessageProvider validateLogin(String email, String password) {
		if (!validateEmail(email)) {
			return EMAIL_INVALID_ERROR;
		}
		if (!validatePassword(password)) {
			return PASSWORD_INVALID_ERROR;
		}
		return VALID;
	}

	/**
	 * Validate registration form parameters
	 *
	 * @param email
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param repeatPassword
	 * @return valid information code if valid
	 */
	public static MessageProvider validateRegistration(String email, String firstName, String lastName, String password,
			String repeatPassword) {
		MessageProvider loginResult = validateLogin(email, password);
		if (loginResult != VALID) {
			return loginResult;
		}

		if (!validateName(firstName)) {
			return FIRSTNAME_INVALID_ERROR;
		}

		if (!validateName(lastName)) {
			return LASTNAME_INVALID_ERROR;
		}

		if (!validatePassword(repeatPassword)) {
			return PASSWORD_INVALID_ERROR;
		}

		if (!repeatPassword.equals(password)) {
			return REPEAT_PASSWORD_INVALID_ERROR;
		}

		return VALID;
	}

	/**
	 * Validate change password form parameters
	 *
	 * @param oldPassword
	 * @param newPassword
	 * @param repeatPassword
	 * @return valid information code if valid
	 */
	public static MessageProvider validatePasswordChange(String oldPassword, String newPassword,
			String repeatPassword) {
		if (!validatePassword(oldPassword) || !validatePassword(newPassword) || !validatePassword(repeatPassword)) {
			return PASSWORD_INVALID_ERROR;
		}

		if (!repeatPassword.equals(newPassword)) {
			return REPEAT_PASSWORD_INVALID_ERROR;
		}

		return VALID;
	}

	/**
	 * Validate profile settings form parameters
	 *
	 * @param email
	 * @param firstName
	 * @param lastName
	 * @param sex
	 * @param phone
	 * @param birthDate
	 * @return valid information code if valid
	 */
	public static MessageProvider validateProfileChange(String email, String firstName, String lastName, String sex,
			String phone, String birthDate, String country, String city) {
		if (!validateEmail(email)) {
			return EMAIL_INVALID_ERROR;
		}

		if (!validateName(firstName)) {
			return FIRSTNAME_INVALID_ERROR;
		}

		if (!validateName(lastName)) {
			return LASTNAME_INVALID_ERROR;
		}

		if (!validateName(country)) {
			return LASTNAME_INVALID_ERROR;
		}

		if (!validateName(city)) {
			return LASTNAME_INVALID_ERROR;
		}

		if (!validateSex(sex)) {
			return SEX_INVALID_ERROR;
		}

		if (!validatePhone(phone)) {
			return PHONE_INVALID_ERROR;
		}

		if (!validateBirthDate(birthDate)) {
			return BIRTHDATE_INVALID_ERROR;
		}

		return VALID;
	}

	/**
	 * Validate edit/create new Group form parameters
	 *
	 * @param name
	 * @param description
	 * @return valid information code if valid
	 */
	public static MessageProvider validateGroup(String name, String description) {
		if (!validateGroupOrGiftName(name)) {
			return GROUP_NAME_INVALID_ERROR;
		}

		if (!validateGroupOrGiftDescription(description)) {
			return GROUP_DESC_INVALID_ERROR;
		}
		return VALID;
	}

	/**
	 * Validate edit/create new Gift form parameters
	 *
	 * @param name
	 * @param description
	 * @return valid information code if valid
	 */
	public static MessageProvider validateGift(String name, String description) {
		if (!validateGroupOrGiftName(name)) {
			return GROUP_NAME_INVALID_ERROR;
		}

		if (!validateGroupOrGiftDescription(description)) {
			return GROUP_DESC_INVALID_ERROR;
		}
		return VALID;
	}
}
