package coins;

import static constants.Constants.ERROR_EXPRESSION;
import static constants.Constants.ERROR_FILE;
import static constants.Constants.NEW_LINE;
import static constants.Constants.ONE_OR_MORE_SPACES;
import static constants.Constants.ONLY_DIGITS;
import static constants.Constants.ZERO_LENGTH_ARRAY;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import interfaces.Verifiable;

public class CoinsExpressionValidator implements Verifiable<Integer> {

	private String			inputExpression;
	private int				sum;
	private Set<Integer>	nominals;
	private String			errorMessage;
	private Integer[]		validExpression	= new Integer[ZERO_LENGTH_ARRAY];

	public CoinsExpressionValidator(String inputExpression) {
		this.inputExpression = inputExpression;
	}

	@Override
	public Integer[] getValidExpression() {
		return validExpression;
	}

	@Override
	public String getInvalidMessage() {
		StringBuilder builder = new StringBuilder();
		builder.append(errorMessage);
		builder.append(NEW_LINE);
		return builder.toString();
	}

	@Override
	public boolean isValidExpression() {
		boolean isValid = true;
		inputExpression = inputExpression.trim();
		String[] numberStringArray = new String[ZERO_LENGTH_ARRAY];

		if (!inputExpression.matches(ONLY_DIGITS)) {
			return hasErrorMessage(ERROR_FILE);
		}
		if (inputExpression.isEmpty()) {
			return hasErrorMessage(ERROR_EXPRESSION);
		}

		numberStringArray = inputExpression.split(ONE_OR_MORE_SPACES);
		if (!hasValidSum(numberStringArray)) {
			isValid = false;
			return isValid;
		}

		nominals = new TreeSet<>();
		if (!hasValidNominal(numberStringArray)) {
			isValid = false;
			return isValid;
		}

		validExpression = createValidExpression();

		return isValid;
	}

	private Integer[] createValidExpression() {
		List<Integer> list = new ArrayList<>();
		list.add(sum);
		for (Iterator<Integer> iterator = nominals.iterator(); iterator.hasNext();) {
			Integer integer = iterator.next();
			list.add(integer);
		}
		return list.toArray(new Integer[list.size()]);
	}

	private boolean hasErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return false;
	}

	private boolean hasValidSum(String[] numberStr) {
		final int SUM_POSITION = 0;
		boolean flag = true;
		// getting the first number from the array
		sum = Integer.parseInt(numberStr[SUM_POSITION]);
		if (sum == 0) {
			flag = hasErrorMessage(ERROR_EXPRESSION);
		}
		return flag;
	}

	private boolean hasValidNominal(String[] numberStr) {
		boolean flag = true;
		final int NOMINAL_LENGTH_IN_ARRAY = numberStr.length - 1;
		// iteration the array from second to last elements
		for (int i = 1; i < numberStr.length; i++) {
			int nominal = Integer.parseInt(numberStr[i]);
			if (nominal == 0) {
				flag = hasErrorMessage(ERROR_EXPRESSION);
			}
			nominals.add(nominal);
		}
		// check whether the set of nominals contents duplicate values or it is empty
		if (nominals.isEmpty() || nominals.size() < NOMINAL_LENGTH_IN_ARRAY) {
			nominals.clear();
			nominals = null;
			flag = hasErrorMessage(ERROR_EXPRESSION);
		}
		return flag;
	}
}
