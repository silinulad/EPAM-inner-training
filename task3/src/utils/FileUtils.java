package utils;

import static constants.Constants.ERROR_FILE;
import static constants.Constants.INITIAL_REGEX;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {

	private FileUtils() {
	}

	public static List<String> getInputFileList() {
		return inputFileList;
	}

	private static List<String> inputFileList = new ArrayList<>();

	public static void createInitialExpression(Scanner in, PrintWriter out) {
		if (!in.hasNextLine()) {
			out.println(ERROR_FILE);
			throw new IndexOutOfBoundsException(ERROR_FILE);
		}
		String firstLine = in.nextLine().trim();
		if (!in.hasNextLine()) {
			out.println(ERROR_FILE);
			throw new IndexOutOfBoundsException(ERROR_FILE);
		}
		String secondLine = in.nextLine().trim();
		String initialString = firstLine + secondLine;

		// the regular expression for first and second lines. If true - the file is
		// valid; otherwise, false
		if (!initialString.toLowerCase().matches(INITIAL_REGEX)) {
			out.println(ERROR_FILE);
			return;
		}
		inputFileList.add(firstLine);
		out.println(firstLine);
	}

}
