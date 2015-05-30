package hr.fer.zemris.java.tecaj.hw5.db.filters;

import hr.fer.zemris.java.tecaj.hw5.db.ConditionalExpression;
import hr.fer.zemris.java.tecaj.hw5.db.DatabaseException;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.comparisons.EqualsCondition;
import hr.fer.zemris.java.tecaj.hw5.db.comparisons.GreaterThanOrEqualsCondition;
import hr.fer.zemris.java.tecaj.hw5.db.comparisons.GreaterThanCondition;
import hr.fer.zemris.java.tecaj.hw5.db.comparisons.LessThanOrEqualsCondition;
import hr.fer.zemris.java.tecaj.hw5.db.comparisons.LessThanCondition;
import hr.fer.zemris.java.tecaj.hw5.db.comparisons.NotEqualsCondition;
import hr.fer.zemris.java.tecaj.hw5.db.comparisons.WildCardEqualsCondition;
import hr.fer.zemris.java.tecaj.hw5.db.comparisons.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.fieldgetters.FirstNameFieldGetter;
import hr.fer.zemris.java.tecaj.hw5.db.fieldgetters.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.fieldgetters.JMBAGFieldGetter;
import hr.fer.zemris.java.tecaj.hw5.db.fieldgetters.LastNameFieldGetter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class represents an implementation that filters student records that
 * satisfies conditional expressions in given <code>query</code> command.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * JMBAG, if present in the query.
	 */
	private Optional<String> jmbag;

	/**
	 * A list of conditional expressions combined with <code>and</code> keyword.
	 */
	private List<ConditionalExpression> conditionalExpressions;

	/**
	 * Constructs the list of conditional expressions. Conditional expressions
	 * must be in a form: <code>fieldName operator stringLiteral</code>. String
	 * literals must be surrounded with quotation marsk and can contain up to
	 * one asterisk.
	 * 
	 * @param query
	 *            The given conditional expressions.
	 * @throws DatabaseException
	 *             - if query is invalid.
	 */
	public QueryFilter(String query) throws DatabaseException {
		jmbag = Optional.empty();
		conditionalExpressions = new ArrayList<>();
		extractConditions(query);
	}

	/**
	 * Extracts conditional expressions from given query line.
	 * 
	 * @param query
	 *            The given conditional expression.
	 * @throws DatabaseException
	 *             - if query is invalid.
	 */
	private void extractConditions(String query) throws DatabaseException {
		String[] conditions = query.trim().split("\\s+(?iu)and\\s+");

		// Loop through all conditional expressions.
		for (String condition : conditions) {

			// Extract left side of the conditional expression.
			IFieldValueGetter field = extractFieldValueGetter(condition);
			if (field == null) {
				throw new DatabaseException(
						"Left side of the comparison should be field name. Invalid condition: "
								+ condition);
			}

			// Extract operator.
			IComparisonOperator operator = extractOperator(condition);
			if (operator == null) {
				throw new DatabaseException(
						"Operator not suported. Invalid condition: "
								+ condition);
			}

			// Extract right side of the conditional expression.
			String literal = extractStringLiteral(condition);

			// Check if operator is "=" and if string literal has more than one
			// asterisk.
			if (operator.getClass().equals(EqualsCondition.class)) {
				int asterisk = numberOfWildCards(literal);
				if (asterisk > 1) {
					throw new DatabaseException(
							"String literal can not contain more than one wildchar: "
									+ condition);
				} else if (asterisk == 1) {
					// String literal has one asterisk.
					operator = new WildCardEqualsCondition();
				} else {
					// Check if condition contains full JMBAG. If it does save
					// it for
					// fast record retrieval.
					if (field.getClass().equals(JMBAGFieldGetter.class)) {
						jmbag = Optional.of(literal);
					}
				}
			}

			// Store conditional expression.
			ConditionalExpression expression = new ConditionalExpression(field,
					literal, operator);
			conditionalExpressions.add(expression);
		}

	}

	/**
	 * Counts the number of asterisks in string literal.
	 * 
	 * @param literal
	 *            Stirng literal.
	 * @return Number of asterisks in string literal.
	 */
	private int numberOfWildCards(String literal) {
		return literal.length() - literal.replace("*", "").length();
	}

	/**
	 * Recognizes the field and returns the extracted field value getter.
	 * 
	 * @param condition
	 *            Given condition,
	 * @return The extracted field value getter.
	 */
	private IFieldValueGetter extractFieldValueGetter(String condition) {
		IFieldValueGetter getter = null;
		if (condition.startsWith("lastName")) {
			getter = new LastNameFieldGetter();
		} else if (condition.startsWith("firstName")) {
			getter = new FirstNameFieldGetter();
		} else if (condition.startsWith("jmbag")) {
			getter = new JMBAGFieldGetter();
		}
		return getter;
	}

	/**
	 * Recognizes the operator and returns the instance of extracted comparison
	 * operator.
	 * 
	 * @param condition
	 *            Given condition.
	 * @return The instance of extracted comparison operator.
	 */
	private IComparisonOperator extractOperator(String condition) {
		IComparisonOperator operator = null;
		if (condition.matches(".+[a-z\\s][=]\\s*[\"].+")) {
			operator = new EqualsCondition();
		} else if (condition.matches(".+[a-z\\s][!][=]\\s*[\"].+")) {
			operator = new NotEqualsCondition();
		} else if (condition.matches(".+[a-z\\s][<]\\s*[\"].+")) {
			operator = new LessThanCondition();
		} else if (condition.matches(".+[a-z\\s][<][=]\\s*[\"].+")) {
			operator = new LessThanOrEqualsCondition();
		} else if (condition.matches(".+[a-z\\s][>]\\s*[\"].+")) {
			operator = new GreaterThanCondition();
		} else if (condition.matches(".+[a-z\\s][>][=]\\s*[\"].+")) {
			operator = new GreaterThanOrEqualsCondition();
		}
		return operator;
	}

	/**
	 * Extracts the sting literal from the given condition.
	 * 
	 * @param condition
	 *            Given condition.
	 * @return String literal from given condition.
	 */
	private String extractStringLiteral(String condition) {
		String literal = null;
		int spliterBegin = condition.indexOf("\"");
		int spliterEnd = condition.lastIndexOf("\"");
		literal = condition.substring(spliterBegin + 1, spliterEnd).trim();
		return literal;
	}

	/**
	 * This method is used when it is possible to decide if index can be used
	 * for record retrieval or filtering is required.
	 * 
	 * @return JMBAG from the conditional expression.
	 */
	public Optional<String> getJMBAG() {
		return jmbag;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		// If every condition is true return true, otherwise false.
		for (ConditionalExpression expression : conditionalExpressions) {
			boolean result = expression.getOperator().satisfied(
					expression.getGetter().get(record), expression.getValue());
			if (!result) {
				return false;
			}
		}
		return true;
	}

}
