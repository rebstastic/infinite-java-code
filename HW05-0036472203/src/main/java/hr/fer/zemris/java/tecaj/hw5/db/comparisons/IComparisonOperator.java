package hr.fer.zemris.java.tecaj.hw5.db.comparisons;

/**
 * A strategy for checking the satisfaction of two value comparison with
 * specified operator.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public interface IComparisonOperator {

	/**
	 * <p>
	 * Returns <code>true</code> if comparison of two values with specified
	 * operator is true, <code>false</code> otherwise.
	 * </p>
	 * <p>
	 * Every class that implements <code>IComparisonOperator</code> interface
	 * must offer the implementation of this method.
	 * 
	 * @param value1
	 *            Fist value to compare.
	 * @param value2
	 *            Second value to compare.
	 * @return <code>true</code> if comparison of two values with specified
	 *         operator is true, <code>false</code> otherwise.
	 */
	boolean satisfied(String value1, String value2);

}
