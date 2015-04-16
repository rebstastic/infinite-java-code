package hr.fer.zemris.java.tecaj.hw5.db.comparisons;

/**
 * This class represents an equals operator "=" when the left side of comparison
 * contains at a wildcard "*".
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class WildCardEqualsCondition implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		int asterisk = value2.indexOf("*");
		String start = value2.substring(0, asterisk);
		String end = value2.substring(asterisk + 1);
		return value1.startsWith(start) && value1.endsWith(end);
	}

}
