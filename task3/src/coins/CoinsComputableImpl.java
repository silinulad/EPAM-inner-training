package coins;

import static constants.Constants.NEW_LINE;
import static constants.Constants.SPACE;
import static constants.Constants.ZERO_LENGTH_ARRAY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import interfaces.Computable;
import interfaces.Verifiable;

public class CoinsComputableImpl implements Computable {

	private int amountExchange;
	private int[] nominals = new int[ZERO_LENGTH_ARRAY];
	private List<Integer> moneys = new ArrayList<>();
	private int count = 0;
	private List<Integer> oneCombination = new ArrayList<>();
	private List<List<Integer>> allCombinations = new ArrayList<>();

	public CoinsComputableImpl(Verifiable<Integer> validator) {
		this.amountExchange = validator.getValidExpression()[0];
		setNominals(validator);
	}

	@Override
	public void calculate() {
		getCombinations(amountExchange);
	}

	@Override
	public String getResultAsString() {
		StringBuilder builder = new StringBuilder();
		// if can't exchange amount
		if (nominals.length == 1 && (amountExchange % nominals[0] != 0)) {
			builder.append("Can't exchange");
			return builder.toString();
		}
		builder.append(NEW_LINE);
		// sort for getting a descending order of the all combinations

		Collections.sort(allCombinations, (list1, list2) -> Integer.compare(list2.size(), list1.size()));
		for (List<Integer> list : allCombinations) {
			// sort for getting a ascending order of the combination list in an each line
			Collections.sort(list);
			for (Integer integer : list) {
				builder.append(integer);
				builder.append(SPACE);
			}
			builder.append(NEW_LINE);
		}

		return builder.toString();
	}

	private void createNominals(Verifiable<Integer> validator) {
		// forming an array of the nominal
		nominals = new int[validator.getValidExpression().length - 1];
		for (int i = 1; i < validator.getValidExpression().length; i++) {
			nominals[i - 1] = validator.getValidExpression()[i];
		}
	}

	private void setNominals(Verifiable<Integer> validator) {
		createNominals(validator);
	}

	private void getCombinations(int sum) {
		// it's the condition getting off from recursion
		if (sum < 0) {
			count--;
			return;
		}
		// it's the condition of forming a result list and getting off from recursion
		if (sum == 0) {
			// forming a list from the current combination
			for (int i = 0; i < count; i++) {
				oneCombination.add(moneys.get(i));
			}
			// add the current combination to the list of all combinations
			if (!allCombinations.contains(oneCombination)) {
				allCombinations.add(new ArrayList<>(oneCombination));
			}
			// clear the list for a next combination
			oneCombination.clear();
			count--;
			return;
		}
		for (int coin : nominals) {
			// forming the common list of money recursively
			if (count == 0 || moneys.get(count - 1) >= coin) {
				if (moneys.size() > count) {
					moneys.set(count, coin);
				} else {
					moneys.add(count, coin);
				}
				count++;
				getCombinations(sum - coin);
			}
		}
		count--;
	}
}
