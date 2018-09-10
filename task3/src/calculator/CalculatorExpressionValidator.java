package calculator;

import static constants.Constants.ALL_LINE;
import static constants.Constants.BRACKETS;
import static constants.Constants.END_LINE;
import static constants.Constants.ERROR_BRACKETS;
import static constants.Constants.ERROR_EXPRESSION;
import static constants.Constants.LINE_WITH_CLOSE_BRACKET;
import static constants.Constants.LINE_WITH_OPEN_BRACKET;
import static constants.Constants.ONE_OR_MORE_SPACES;
import static constants.Constants.ONLY_DIGITS;
import static constants.Constants.REPEATED_OPERATION;
import static constants.Constants.SPACE;
import static constants.Constants.START_LINE;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import interfaces.Verifiable;

public class CalculatorExpressionValidator implements Verifiable<String> {

	private String		inputExpression;
	private String		errorMessage;
	private String[]	validExpression;

	public CalculatorExpressionValidator(String inputExpression) {
		this.inputExpression = inputExpression;
	}

	@Override
	public String[] getValidExpression() {
		return validExpression;
	}

	@Override
	public String getInvalidMessage() {
		return errorMessage;
	}

	@Override
	public boolean isValidExpression() {
		boolean isValid = true;
		String[] regexList = { ALL_LINE, REPEATED_OPERATION, START_LINE, END_LINE, LINE_WITH_OPEN_BRACKET,
				LINE_WITH_CLOSE_BRACKET, BRACKETS, ONLY_DIGITS };
		inputExpression = inputExpression.trim();
		// check an input string expression for according to the regex
		for (String regex : regexList) {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(inputExpression);
			if (matcher.find() || inputExpression.isEmpty()) {
				return hasErrorMessage(ERROR_EXPRESSION);
			}
		}
		isValid = hasPreparedString();
		return isValid;
	}

	private void append(char param, StringBuilder builder) {
		builder.append(SPACE);
		builder.append(param);
		builder.append(SPACE);
	}

	private boolean hasErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return false;
	}

	private boolean hasPreparedString() {
		boolean isValid = true;
		Deque<Character> bracketStack = new ArrayDeque<>(inputExpression.length());
		StringBuilder builder = new StringBuilder();

		// check a bracket balance
		for (char charFromArray : inputExpression.toCharArray()) {
			switch (charFromArray) {
				case '(':
					append(charFromArray, builder);
					bracketStack.push(charFromArray);
					break;
				case ')':
					append(charFromArray, builder);
					if (!bracketStack.isEmpty()) {
						char tmpChar = bracketStack.pop();
						if (tmpChar != '(') {
							return hasErrorMessage(ERROR_EXPRESSION + "2");
						}
					} else {
						return hasErrorMessage(ERROR_BRACKETS + "3");
					}
					break;
				case '*':
				case '/':
				case '+':
				case '-':
					append(charFromArray, builder);
					break;
				default:
					builder.append(charFromArray);
					break;
			}
		}
		if (!bracketStack.isEmpty()) {
			return hasErrorMessage(ERROR_BRACKETS);
		}
		String expressionResult = builder.toString().trim();
		validExpression = expressionResult.split(ONE_OR_MORE_SPACES);
		return isValid;
	}
}
