package database.connectionpool;

import java.util.ResourceBundle;

/**
 * Created by Silin on 07.2018.
 */

/**
 * DBResourceManager provides database connection settings from ResourceBundle
 */
public class DBResourceManager {
	private final static DBResourceManager instance = new DBResourceManager();

	private ResourceBundle bundle = ResourceBundle.getBundle("connectionpool.db");

	public static DBResourceManager getInstance() {
		return instance;
	}

	public String getValue(String key) {
		return bundle.getString(key);
	}
}