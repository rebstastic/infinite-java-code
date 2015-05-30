package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.comparisons.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.fieldgetters.IFieldValueGetter;

/**
 * The complete conditional expression.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class ConditionalExpression {

	/**
	 * A reference to <code>IFieldValueGetter</code> strategy.
	 */
	IFieldValueGetter getter;

	/**
	 * A reference to string literal.
	 */
	String stringLiteral;

	/**
	 * A reference to <code>IComparisonOperator</code> strategy.
	 */
	IComparisonOperator operator;

	/**
	 * Constructs the complete conditional expression.
	 * 
	 * @param getter
	 *            A reference to <code>IFieldValueGetter</code> strategy.
	 * @param stringLiteral
	 *            A reference to string literal.
	 * @param operator
	 *            A reference to <code>IComparisonOperator</code> strategy.
	 */
	public ConditionalExpression(IFieldValueGetter getter,
			String stringLiteral, IComparisonOperator operator) {
		this.getter = getter;
		this.stringLiteral = stringLiteral;
		this.operator = operator;
	}

	/**
	 * Returns a reference to <code>IFieldValueGetter</code> strategy.
	 * 
	 * @return A reference to <code>IFieldValueGetter</code> strategy.
	 */
	public IFieldValueGetter getGetter() {
		return getter;
	}

	/**
	 * Returns a reference to string literal.
	 * 
	 * @return A reference to string literal.
	 */
	public String getValue() {
		return stringLiteral;
	}

	/**
	 * Returns a reference to <code>IComparisonOperator</code> strategy.
	 * 
	 * @return A reference to <code>IComparisonOperator</code> strategy.
	 */
	public IComparisonOperator getOperator() {
		return operator;
	}

}
