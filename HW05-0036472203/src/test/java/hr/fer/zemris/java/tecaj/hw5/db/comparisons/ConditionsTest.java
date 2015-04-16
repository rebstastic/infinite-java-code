package hr.fer.zemris.java.tecaj.hw5.db.comparisons;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class ConditionsTest {

	static String value1;
	static String value2;
	IComparisonOperator operator;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		value1 = "mocca";
		value2 = "cappucino";
	}
	
	@Test
	public void Equals_DifferentValues_NotSatisfied() {
		operator = new EqualsCondition();
		assertFalse(operator.satisfied(value1, value2));
	}
	
	@Test
	public void Equals_EqualValues_Satisfied() {
		operator = new EqualsCondition();
		assertTrue(operator.satisfied(value1, value1));
	}
	
	@Test
	public void NotEquals_DifferentValues_Satisfied() {
		operator = new NotEqualsCondition();
		assertTrue(operator.satisfied(value1, value2));
	}
	
	@Test
	public void NotEquals_SameValues_NotSatisfied() {
		operator = new NotEqualsCondition();
		assertFalse(operator.satisfied(value1, value1));
	}
	
	@Test
	public void LessThan_DifferentValues_Satisfied() {
		operator = new LessThanCondition();
		assertTrue(operator.satisfied(value2, value1));
	}
	
	@Test
	public void LessThan_DifferentValues_NotSatisfied() {
		operator = new LessThanCondition();
		assertFalse(operator.satisfied(value1, value2));
	}
	
	@Test
	public void LessThan_SameValues_NotSatisfied() {
		operator = new LessThanCondition();
		assertFalse(operator.satisfied(value1, value1));
	}
	
	@Test
	public void LessOrEquals_DifferentValues_Satisfied() {
		operator = new LessThanOrEqualsCondition();
		assertTrue(operator.satisfied(value2, value1));
	}
	
	@Test
	public void LessOrEquals_DifferentValues_NotSatisfied() {
		operator = new LessThanOrEqualsCondition();
		assertFalse(operator.satisfied(value1, value2));
	}
	
	@Test
	public void LessOrEquals_SameValues_Satisfied() {
		operator = new LessThanOrEqualsCondition();
		assertTrue(operator.satisfied(value1, value1));
	}
	
	@Test
	public void GreaterThan_DifferentValues_Satisfied() {
		operator = new GreaterThanCondition();
		assertTrue(operator.satisfied(value1, value2));
	}
	
	@Test
	public void GreaterThan_DifferentValues_NotSatisfied() {
		operator = new GreaterThanCondition();
		assertFalse(operator.satisfied(value2, value1));
	}
	
	@Test
	public void GreaterThan_SameValues_NotSatisfied() {
		operator = new GreaterThanCondition();
		assertFalse(operator.satisfied(value1, value1));
	}
	
	@Test
	public void GreaterOrEquals_DifferentValues_Satisfied() {
		operator = new GreaterThanOrEqualsCondition();
		assertTrue(operator.satisfied(value1, value2));
	}
	
	@Test
	public void GreaterOrEquals_DifferentValues_NotSatisfied() {
		operator = new GreaterThanOrEqualsCondition();
		assertFalse(operator.satisfied(value2, value1));
	}
	
	@Test
	public void GreaterOrEquals_SameValues_Satisfied() {
		operator = new GreaterThanOrEqualsCondition();
		assertTrue(operator.satisfied(value1, value1));
	}
	
	@Test
	public void WildCardsEquals_InTheMiddle_Satisfied() {
		String s2 = "m*a"; 
		operator = new WildCardEqualsCondition();
		assertTrue(operator.satisfied(value1, s2));
	}
	
	@Test
	public void WildCardsEquals_InTheMiddle_NotSatisfied() {
		String s2 = "ma*a"; 
		operator = new WildCardEqualsCondition();
		assertFalse(operator.satisfied(value2, s2));
	}
	
	@Test
	public void WildCardsEquals_AtTheBeginning_Satisfied() {
		String s2 = "*a";
		operator = new WildCardEqualsCondition();
		assertTrue(operator.satisfied(value1, s2));
	}
	
	@Test
	public void WildCardsEquals_AtTheBeginning_NotSatisfied() {
		String s2 = "*eh";
		operator = new WildCardEqualsCondition();
		assertFalse(operator.satisfied(value1, s2));
	}
	
	@Test
	public void WildCardsEquals_AtTheEnd_Satisfied() {
		String s2 = "moc*";
		operator = new WildCardEqualsCondition();
		assertTrue(operator.satisfied(value1, s2));
	}
	
	@Test
	public void WildCardsEquals_AtTheEnd_NotSatisfied() {
		String s2 = "eh*";
		operator = new WildCardEqualsCondition();
		assertFalse(operator.satisfied(value1, s2));
	}
	
}
