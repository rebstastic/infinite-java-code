package hr.fer.zemris.java.tecaj.hw5.db.comparisons;

/**
 * This class represents a greater than operator ">".
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class GreaterThanCondition implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		int result = value1.compareTo(value2);
		return result > 0;
	}

}
