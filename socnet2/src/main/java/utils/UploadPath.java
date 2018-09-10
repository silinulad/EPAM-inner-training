package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Created by Silin on 08.2018.
 */

/**
 * UploadPath initializes paths to folders for uploading files and images
 */
public class UploadPath {

	private static final String	UPLOAD_FOLDER			= "upload";
	private static final String	UPLOAD_FILE_FOLDER		= "files";
	private static final String	UPLOAD_IMAGES_FOLDER	= "images";
	private static final String	WEB_INF					= "WEB-INF";
	private static final String	ERROR_MESSAGE			= "The class is not initialized";
	private static final String	SLASH					= "/";

	private static String		pathFileUpload;
	private static String		pathImageUpload;
	private static UploadPath	instance;

	private UploadPath() {}

	public static void initialize(String realPath) {
		if (!"".equals(realPath)) {
			synchronized (UploadPath.class) {
				if (instance == null) {
					String filePath = realPath + WEB_INF + File.separator + UPLOAD_FOLDER + File.separator;
					String imagePath = realPath + File.separator + UPLOAD_FOLDER + File.separator;
					pathFileUpload = filePath + UPLOAD_FILE_FOLDER + File.separator;
					pathImageUpload = imagePath + UPLOAD_IMAGES_FOLDER + File.separator;
					instance = new UploadPath();
				}
			}
		}
	}

	public static String getPathImageUpload() {
		if (pathImageUpload == null) {
			throw new RuntimeException(ERROR_MESSAGE);
		}
		return pathImageUpload;
	}

	public static String getPathFileUpload() {
		if (pathFileUpload == null) {
			throw new RuntimeException(ERROR_MESSAGE);
		}
		return pathFileUpload;
	}

	public static void creatDirectory(String pathDir) throws IOException {
		Path path = Paths.get(pathDir);
		Files.createDirectories(path);
	}

	/**
	 * 
	 * @param originName
	 * @return the file name that will be saved on the server
	 */
	public static String getUploadName(String originName) {
		int lastDotIndex = originName.lastIndexOf(".");
		String extention = originName.substring(lastDotIndex);
		String hash = UUID.randomUUID().toString();
		String uploadFileName = hash + extention;
		return uploadFileName.trim();
	}

	public static String getRelativePath(String uploadFileName) {
		String relativePath = UPLOAD_FOLDER + SLASH + UPLOAD_IMAGES_FOLDER + SLASH + uploadFileName;
		return relativePath;
	}
}
