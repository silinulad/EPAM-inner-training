package interfaces;

// interface for getting of a validation result
public interface Verifiable<T> {

	boolean isValidExpression();

	T[] getValidExpression();

	String getInvalidMessage();
}
