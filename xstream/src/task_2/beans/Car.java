package task_2.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import task_2.constants.Constants;

@XStreamAlias("car")
class Car {

	@XStreamAlias("color")
	@XStreamAsAttribute
	private String carColor;

	@XStreamAlias("used")
	@XStreamAsAttribute
	private boolean isUsed;

	@XStreamAlias("make")
	private String carBbrand;

	@XStreamAlias("model")
	private String carModel;

	@XStreamAlias("year")
	private Date manufacturedYear;

	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat(Constants.ONLY_YEAR);
		StringBuilder builder = new StringBuilder();
		builder.append("\ncar color=");
		builder.append(carColor);
		builder.append(", used=");
		builder.append(isUsed);
		builder.append("\n    make=");
		builder.append(carBbrand);
		builder.append("\n    model=");
		builder.append(carModel);
		builder.append("\n    year=");
		builder.append(dateFormat.format(manufacturedYear));
		return builder.toString();
	}

}
