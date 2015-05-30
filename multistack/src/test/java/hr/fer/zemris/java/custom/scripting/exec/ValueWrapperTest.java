package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.Assert;
import org.junit.Test;

public class ValueWrapperTest {
	
	@Test
	public void getValueTest() {
		Assert.assertEquals(15, new ValueWrapper(15).getValue());
	}
	
	@Test
	public void setValueTest() {
		ValueWrapper value = new ValueWrapper(15);
		value.setValue(100);
		Assert.assertEquals(100, value.getValue());
	}
	
	@Test
	public void incrementTest() {
		ValueWrapper value = new ValueWrapper(15);
		value.increment(15);
		Assert.assertEquals(30, value.getValue());
	}
	
	@Test
	public void decrementTest() {
		ValueWrapper value = new ValueWrapper(15);
		value.decrement(10.0);
		Assert.assertEquals(5.0, value.getValue());
	}
	
	@Test
	public void multiplyTest() {
		ValueWrapper value = new ValueWrapper(15);
		value.multiply("2");
		Assert.assertEquals(30, value.getValue());
	}
	
	@Test
	public void divideTest() {
		ValueWrapper value = new ValueWrapper("15.0");
		value.divide(15);
		Assert.assertEquals(1.0, value.getValue());
	}
	
	@Test
	public void numCompareTest_Equals() {
		ValueWrapper value = new ValueWrapper("15");
		Assert.assertEquals(0, value.numCompare("15.0"));
	}
	
	@Test
	public void numCompareTest_Less() {
		ValueWrapper value = new ValueWrapper("15");
		Assert.assertEquals(-1, value.numCompare(30.0));
	}
	
	@Test
	public void numCompareTest_Greater() {
		ValueWrapper value = new ValueWrapper("15.0");
		Assert.assertEquals(1, value.numCompare(10));
	}
	
	@Test
	public void numCompareTest_ZeroEqualsNull() {
		ValueWrapper value = new ValueWrapper(null);
		Assert.assertEquals(0, value.numCompare(0));
	}
	
	@Test
	public void numCompareTest_NullEqualsZero() {
		ValueWrapper value = new ValueWrapper(0);
		Assert.assertEquals(0, value.numCompare(null));
	}
	
	@Test(expected=RuntimeException.class) 
	public void nonParsable() {
		ValueWrapper value = new ValueWrapper(null);
		value.increment("nanana");
	}
}
