package factories;

import java.math.BigDecimal;

public class MathFactory {

	private MathFactory() {}

	// lazy invoke the instance of the class
	public static MathFactory getInstance() {
		return InstanceHolder.INSTANCE;
	}

	private static class InstanceHolder {
		private static final MathFactory INSTANCE = new MathFactory();
	}

	public int getPriority(String key) {
		int priority = 0;
		for (MathOperation mathOperation : MathOperation.values()) {
			if (key.equalsIgnoreCase(mathOperation.mathOperation)) {
				priority = mathOperation.getPriority();
			}
		}
		return priority;
	}

	public BigDecimal getAction(String key, BigDecimal operand1, BigDecimal operand2) {
		BigDecimal action = null;
			for (MathOperation mathOperation : MathOperation.values()) {
				if (key.equalsIgnoreCase(mathOperation.mathOperation)) {
				action = mathOperation.getAction(operand1, operand2);
				}
			}
		return action;
	}

	private enum MathOperation {
		MULTIPLE("*", 3) {

			@Override
			protected BigDecimal getAction(BigDecimal operand1, BigDecimal operand2) {
				return operand1.multiply(operand2);
			}

		},
		DIVIDE("/", 3) {

			@Override
			protected BigDecimal getAction(BigDecimal operand1, BigDecimal operand2) {
				final int SINGS_AFTER_POINT = 2;
				return operand1.divide(operand2, SINGS_AFTER_POINT, BigDecimal.ROUND_HALF_UP);
			}

		},
		PLUS("+", 2) {

			@Override
			protected BigDecimal getAction(BigDecimal operand1, BigDecimal operand2) {
				return operand1.add(operand2);
			}

		},
		MINUS("-", 2) {

			@Override
			protected BigDecimal getAction(BigDecimal operand1, BigDecimal operand2) {
				return operand1.subtract(operand2);
			}

		},
		BRACKET("(", 1) {

			@Override
			protected BigDecimal getAction(BigDecimal operand1, BigDecimal operand2) {
				throw new UnsupportedOperationException();
			}

		};

		@SuppressWarnings("unused")
		public String getMathOperation() {
			return mathOperation;
		}

		public int getPriority() {
			return priority;
		}

		private String	mathOperation;
		private int		priority;

		private MathOperation(String mathOperation, int priority) {
			this.mathOperation = mathOperation;
			this.priority = priority;
		}

		protected abstract BigDecimal getAction(BigDecimal operand1, BigDecimal operand2);

	}

}
