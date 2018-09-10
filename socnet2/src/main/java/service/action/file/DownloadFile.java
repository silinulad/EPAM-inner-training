package service.action.file;

import static service.action.common.Constants.MAIN;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.impl.FileDAOImpl;
import database.dao.interfaces.GenericDAO;
import exception.DaoException;
import exception.UploadException;
import model.AttachFile;
import service.InputValidator;
import service.action.common.Action;

public class DownloadFile implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws DaoException, ServletException, IOException, UploadException {

		final int EXPIRED_TERM = 0;
		final String CONTENT_TYPE = "Content-type: application/octet-stream\n";
		final String EXPIRES_HEADER = "Expires";
		final String SOURCE_CHARSET = "Cp1251";
		final String TARGET_CHARSET = "Cp1252";
		final String SPACE_REGEXP = "[\u00a0\\s]";
		final String UNDERLINE = "_";
		final String CONTENT_DISPOSITION_HEADER = "Content-Disposition";
		final String ATTACHMENT = "attachment; filename=\"";
		final String QUOTE = "\"";

		GenericDAO<AttachFile> fileDao = new FileDAOImpl();

		String idParam = request.getParameter("idFile");
		
		// validate id from request
		if (InputValidator.validateId(idParam)) {
			long fileId = Long.parseLong(idParam);
			
			AttachFile attachFile = fileDao.read(fileId);
			String downloadFileName = attachFile.getOriginName();
			String serverPath = attachFile.getPath();
			
			File downloadFile = new File(serverPath);
			
			// set response headers
			response.setContentType(CONTENT_TYPE);
			response.setDateHeader(EXPIRES_HEADER, EXPIRED_TERM);

			// change character set for Cyrillic and replace 'spaces and nbsp' to '_'
			String normalizeFileName = new String(downloadFileName.getBytes(SOURCE_CHARSET), TARGET_CHARSET);
			normalizeFileName = normalizeFileName.replaceAll(SPACE_REGEXP, UNDERLINE);

			response.addHeader(CONTENT_DISPOSITION_HEADER, ATTACHMENT + normalizeFileName + QUOTE);
			response.setContentLength((int) downloadFile.length());

			try (ServletOutputStream outputStream = response.getOutputStream();
					InputStream inputStream = new BufferedInputStream(new FileInputStream(downloadFile));) {

				int readBytes;
				while ((readBytes = inputStream.read()) != -1) {
					outputStream.write(readBytes);
				}
				return request.getHeader("Referer");
			}
		}
		return request.getContextPath() + MAIN;
	}

}
