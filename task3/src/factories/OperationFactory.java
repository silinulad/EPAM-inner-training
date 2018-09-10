package factories;

import calculator.CalculatorComputableImpl;
import calculator.CalculatorExpressionValidator;
import coins.CoinsComputableImpl;
import coins.CoinsExpressionValidator;
import interfaces.Computable;
import interfaces.Verifiable;

public class OperationFactory {

	private static Operation operation;

	public static void setOperation(String param) {
		operation = Operation.valueOf(param.toUpperCase());
	}

	public static Verifiable<?> getValidator(String expression) {
		return operation.getVerifiableImpl(expression);
	}

	public static Computable getPerformer(Verifiable<?> validator) {
		return operation.getComputableImpl(validator);
	}

	private enum Operation {
		CALCULATOR {

			@Override
			protected Verifiable<?> getVerifiableImpl(String expression) {
				return new CalculatorExpressionValidator(expression);
			}

			@SuppressWarnings("unchecked")
			@Override
			protected Computable getComputableImpl(Verifiable<?> validator) {
				return new CalculatorComputableImpl((Verifiable<String>) validator);
			}
		},

		COINS {

			@Override
			protected Verifiable<?> getVerifiableImpl(String expression) {
				return new CoinsExpressionValidator(expression);
			}

			@SuppressWarnings("unchecked")
			@Override
			protected Computable getComputableImpl(Verifiable<?> validator) {
				return new CoinsComputableImpl((Verifiable<Integer>) validator);
			}
		};

		protected abstract Verifiable<?> getVerifiableImpl(String expression);

		protected abstract Computable getComputableImpl(Verifiable<?> validator);

	}

}
