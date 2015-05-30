package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.Assert;

import org.junit.Test;

public class ObjectMultistackTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void pushTest_KeyNull_ExceptionThrown() {
		new ObjectMultistack().push(null, null);
	}
	
	@Test
	public void pushTest_FirstElement() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("new", new ValueWrapper("15"));
		Assert.assertFalse(multistack.isEmpty("new"));
	}
	
	@Test
	public void pushTest_NotFirstElement() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("new", new ValueWrapper("15"));
		multistack.push("new", new ValueWrapper(3));
		multistack.push("new", new ValueWrapper(12.0));
		Assert.assertFalse(multistack.isEmpty("new"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void popTest_KeyNull_ExceptionThrown() {
		new ObjectMultistack().pop(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void popTest_KeyDoesNotExists_ExceptionThrown() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("new", new ValueWrapper("15"));
		multistack.pop("nonexisting");
	}
	
	@Test(expected=RuntimeException.class)
	public void popTest_EmptyStack_ExceptionThrown(){
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("new", new ValueWrapper("15"));
		multistack.pop("new");
		multistack.pop("new");
	}
	
	@Test
	public void popTest_ProperValue() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("new", new ValueWrapper("15"));
		Assert.assertEquals("15", multistack.pop("new").getValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void peekTest_KeyNull_ExceptionThrown() {
		new ObjectMultistack().peek(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void peelTest_KeyDoesNotExists_ExceptionThrown() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("new", new ValueWrapper("15"));
		multistack.peek("nonexisting");
	}
	
	@Test(expected=RuntimeException.class)
	public void peekTest_EmptyStack_ExceptionThrown(){
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("new", new ValueWrapper("15"));
		multistack.pop("new");
		multistack.peek("new");
	}
	
	@Test
	public void peekTest_ProperValue() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("new", new ValueWrapper("15"));
		Assert.assertEquals("15", multistack.peek("new").getValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void isEmptyTest_KeyNull_ExceptionThrown() {
		new ObjectMultistack().isEmpty(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void isEmptyTest_KeyDoesNotExists_ExceptionThrown() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("new", new ValueWrapper("15"));
		multistack.isEmpty("nonexisting");
	}
	
	@Test
	public void isEmptyTest_Existing_FalseExpected(){
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("new", new ValueWrapper("15"));
		Assert.assertFalse(multistack.isEmpty("new"));
	}
	
	@Test
	public void isEmptyTest_NonExisting_TrueExpected(){
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("new", new ValueWrapper("15"));
		multistack.pop("new");
		Assert.assertTrue(multistack.isEmpty("new"));
	}
}
