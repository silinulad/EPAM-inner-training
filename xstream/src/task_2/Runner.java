package task_2;

import java.io.FileNotFoundException;
import java.io.UncheckedIOException;
import java.security.ProviderException;
import java.util.TimeZone;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

import task_2.beans.CarShop;
import task_2.constants.Constants;
import task_2.utils.XStreamUtil;

public class Runner {
	public static void main(String args[]) {
		// check if the args array is empty
		if (args.length == 0) {
			throw new ProviderException("There are no elements in the args array");
		}

		try {

			XStream xStream = new XStream(new DomDriver());
			xStream.processAnnotations(CarShop.class);
			xStream.registerConverter(
					new DateConverter(Constants.ONLY_YEAR, new String[] {}, TimeZone.getTimeZone("UTC")));
			CarShop[] objectCars = new CarShop[args.length];

			XStreamUtil.createObjectFromXMLFile(args, objectCars, xStream);
			XStreamUtil.printObjects(objectCars);

		} catch (FileNotFoundException e) {
			throw new UncheckedIOException("The file is missing or has an incorrect name/path", e);
		}
	}
}
