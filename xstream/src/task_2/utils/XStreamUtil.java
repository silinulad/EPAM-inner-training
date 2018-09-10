package task_2.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.thoughtworks.xstream.XStream;

public final class XStreamUtil {

	private XStreamUtil() {
	}

	public static final <T> void createObjectFromXMLFile(String[] sourceFilesArray, T[] objectList, XStream xStream)
			throws FileNotFoundException {
		// getting an xml-file for processing
		for (int fileIndex = 0; fileIndex < sourceFilesArray.length; fileIndex++) {
			FileInputStream fis = new FileInputStream(new File(sourceFilesArray[fileIndex]));
			@SuppressWarnings("unchecked")
			T obj = (T) xStream.fromXML(fis);
			objectList[fileIndex] = obj;
		}
	}

	public static <T> void printObjects(T[] objectList) {
		for (T obj : objectList) {
			System.out.println(obj);
		}
	}
}
