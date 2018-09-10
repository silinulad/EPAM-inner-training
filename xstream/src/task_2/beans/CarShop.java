package task_2.beans;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("cars")
public class CarShop {

	@XStreamImplicit
	private List<Car> cars = new ArrayList<>();

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("cars\n");
		builder.append(cars);
		return builder.toString();
	}
}
