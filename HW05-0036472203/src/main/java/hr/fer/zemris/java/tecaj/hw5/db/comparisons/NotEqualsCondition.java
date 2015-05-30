package hr.fer.zemris.java.tecaj.hw5.db.comparisons;

/**
 * This class represents a not equal than operator "!=".
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class NotEqualsCondition implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		return !(value1.equalsIgnoreCase(value2));
	}

}
