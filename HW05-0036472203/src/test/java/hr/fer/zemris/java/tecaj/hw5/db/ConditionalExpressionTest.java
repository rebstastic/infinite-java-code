package hr.fer.zemris.java.tecaj.hw5.db;

import static org.junit.Assert.*;
import hr.fer.zemris.java.tecaj.hw5.db.comparisons.EqualsCondition;
import hr.fer.zemris.java.tecaj.hw5.db.comparisons.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.fieldgetters.FirstNameFieldGetter;
import hr.fer.zemris.java.tecaj.hw5.db.fieldgetters.IFieldValueGetter;

import org.junit.Test;

public class ConditionalExpressionTest {

	@Test
	public void constructor() {
		IFieldValueGetter field = new FirstNameFieldGetter();
		IComparisonOperator operator = new EqualsCondition();
		ConditionalExpression expression = new ConditionalExpression(field, "Petra", operator);
		assertTrue(field.getClass().equals(expression.getGetter().getClass()));
		assertTrue(operator.getClass().equals(expression.getOperator().getClass()));
		assertTrue("Petra".equals(expression.getValue()));
	}

}
