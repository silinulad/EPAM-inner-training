package calculator;

import static constants.Constants.CLOSE_BRACKET;
import static constants.Constants.ONE_OR_MORE_SPACES;
import static constants.Constants.OPEN_BRACKET;
import static constants.Constants.SPACE;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;

import factories.MathFactory;
import interfaces.Computable;
import interfaces.Verifiable;

public class CalculatorComputableImpl implements Computable {

	private static MathFactory	mathFactory		= MathFactory.getInstance();
	private static final String	REGEX_DIGITS	= "\\d+";
	private Verifiable<String>	validator;
	private BigDecimal			result;

	public CalculatorComputableImpl() {}

	public CalculatorComputableImpl(Verifiable<String> validator) {
		this.validator = validator;
	}

	@Override
	public void calculate() {
		Deque<BigDecimal> operandStack = new ArrayDeque<>();
		String postfixNotation = sortingStation();
		String[] tokens = postfixNotation.trim().split(ONE_OR_MORE_SPACES);
		for (String token : tokens) {
			if (token.matches(REGEX_DIGITS)) {
				operandStack.push(new BigDecimal(Integer.parseInt(token)));
			} else {
				BigDecimal operand2 = operandStack.pop();
				BigDecimal operand1 = operandStack.pop();
				BigDecimal result = mathFactory.getAction(token, operand1, operand2);
				operandStack.push(result);
			}
		}
		result = operandStack.pop();
	}

	@Override
	public String getResultAsString() {
		return String.valueOf(result);
	}

	private void append(String token, StringBuilder builder) {
		builder.append(SPACE);
		builder.append(token);
		builder.append(SPACE);
	}

	private String sortingStation() {
		String[] infixNotationString = validator.getValidExpression();
		StringBuilder postfixNotationString = new StringBuilder();
		Deque<String> operationStack = new ArrayDeque<>();

		for (String token : infixNotationString) {
			if (token.matches(REGEX_DIGITS)) {
				append(token, postfixNotationString);
			} else if (OPEN_BRACKET.equals(token)) {
				operationStack.push(token);
			} else if (CLOSE_BRACKET.equals(token)) {
				String topToken = operationStack.pop();
				while (!OPEN_BRACKET.equals(topToken)) {
					append(topToken, postfixNotationString);
					topToken = operationStack.pop();
				}
			} else {
				while (!operationStack.isEmpty()
						&& mathFactory.getPriority(operationStack.peek()) >= mathFactory.getPriority(token)) {
					append(operationStack.pop(), postfixNotationString);
				}
				operationStack.push(token);
			}
		}

		while (!operationStack.isEmpty()) {
			append(operationStack.pop(), postfixNotationString);
		}
		return postfixNotationString.toString();
	}

}
