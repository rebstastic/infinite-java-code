package hr.fer.zemris.java.tecaj.hw5.db.fieldgetters;

import static org.junit.Assert.*;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

import org.junit.BeforeClass;
import org.junit.Test;

public class FieldGettersTest {

	static StudentRecord record;

	@BeforeClass
	public static void setUpBeforeClass() {
		record = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
	}

	@Test
	public void jmgabGetter_get_Equals() {
		IFieldValueGetter getter = new JMBAGFieldGetter();
		String jmbag = getter.get(record);
		assertEquals("0036472203", jmbag);
	}
	
	@Test
	public void lastNameGetter_get_Equals() {
		IFieldValueGetter getter = new LastNameFieldGetter();
		String lastName = getter.get(record);
		assertEquals("Rebernjak", lastName);
	}
	
	@Test
	public void firstNameGetter_get_Equals() {
		IFieldValueGetter getter = new FirstNameFieldGetter();
		String firstName = getter.get(record);
		assertEquals("Petra", firstName);
	}
	
}
