package hr.fer.zemris.java.tecaj.hw5.db;

import static org.junit.Assert.*;
import hr.fer.zemris.java.tecaj.hw5.db.filters.IFilter;
import hr.fer.zemris.java.tecaj.hw5.db.filters.QueryFilter;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StudentDatabaseTest {

	@Test(expected=DatabaseException.class)
	public void constructor_InvalidDatabaseRecordNullRecord_ExceptionThrown() throws DatabaseException {
		List<String> lines = new ArrayList<>();
		lines.add("123");
		new StudentDatabase(lines);
	}
	
	@Test(expected=DatabaseException.class)
	public void constructor_InvalidDatabaseRecord_ExceptionThrown() throws DatabaseException {
		List<String> lines = new ArrayList<>();
		lines.add("1231231234	Rebe!rnjak	Petra	5");
		new StudentDatabase(lines);
	}
	
	@Test
	public void forJMBAG() throws DatabaseException {
		List<String> lines = new ArrayList<>();
		lines.add("1231231234	Lalal	Nana	5");
		StudentRecord record = new StudentRecord("1231231234", "Lalal", "Nana", 5);
		StudentDatabase db = new StudentDatabase(lines);
		assertEquals(record, db.forJMBAG("1231231234"));
	}
	
	@Test
	public void filter() throws DatabaseException {
		StudentRecord record1 = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		StudentRecord record2 = new StudentRecord("1231234567", "lala", "nana", 5);
		List<String> list = new ArrayList<>();
		list.add("0036472203	Rebernjak	Petra	5");
		list.add("1231234567	lala	nana	5");
		StudentDatabase db = new StudentDatabase(list);
		IFilter filter = new QueryFilter("jmbag=\"0036472203\"");
		List<StudentRecord> records = db.filter(filter);
		assertTrue(records.contains(record1));	
		assertFalse(records.contains(record2));	

	}

}
