package utils;

/**
 * Created by Silin on 08.2018.
 */

import org.mindrot.jbcrypt.BCrypt;

/**
 * Methods to process user hashes and passwords
 */
public class Encryption {

	/**
	 * Check that plain text password matches the provided hash
	 *
	 * @param password
	 * @param hash
	 * @return true if matches
	 */
	public static boolean checkPassword(String password, String hash) {
		return BCrypt.checkpw(password, hash);
	}

	/**
	 * Encrypt password with salt
	 *
	 * @param password
	 * @return hash
	 */
	public static String encryptPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	/**
	 * Check that plain text file file name matches the provided hash
	 *
	 * @param file
	 *            name
	 * @param hash
	 * @return true if matches
	 */
	public static boolean checkFileName(String fileName, String hash) {
		return BCrypt.checkpw(fileName, hash);
	}

	/**
	 * Encrypt file name with salt
	 *
	 * @param file
	 *            name
	 * @return hash
	 */
	public static String encryptFleName(String fileName) {
		return BCrypt.hashpw(fileName, BCrypt.gensalt());
	}
}
