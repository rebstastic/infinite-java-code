package hr.fer.zemris.java.tecaj.hw5.db.filters;

import static org.junit.Assert.*;
import hr.fer.zemris.java.tecaj.hw5.db.DatabaseException;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

import org.junit.Test;

public class FilterTest {


	@Test
	public void jmbagQueryWildCardWithEquals_ExpectEmptyOptional() throws DatabaseException {
		String query = "jmbag=\"0036*\"";
		QueryFilter filter = new QueryFilter(query);
		assertFalse(filter.getJMBAG().isPresent());
	}
	
	@Test
	public void FullJmbagQueryWithEquals_ExpectNonEmptyOptional() throws DatabaseException {
		String query = "jmbag=\"0036472203\"";
		QueryFilter filter = new QueryFilter(query);
		assertTrue(filter.getJMBAG().isPresent());
	}
	
	@Test
	public void jmbagQueryWildCardWithoutEquals_ExpectEmptyOptional() throws DatabaseException {
		String query = "jmbag<\"0036*\"";
		QueryFilter filter = new QueryFilter(query);
		assertFalse(filter.getJMBAG().isPresent());
	}
	
	@Test
	public void FullJmbagQueryWithoutEquals_ExpectEmptyOptional() throws DatabaseException {
		String query = "jmbag>\"0036472203\"";
		QueryFilter filter = new QueryFilter(query);
		assertFalse(filter.getJMBAG().isPresent());
	}
	
	@Test(expected=DatabaseException.class)
	public void leftSideIsNotField_ExceptionThrown() throws DatabaseException {
		String query = "notAFieldName<=\"Petra\"";
		new QueryFilter(query);
	}
	
	@Test(expected=DatabaseException.class)
	public void notSupportedOperatord_ExceptionThrown() throws DatabaseException {
		String query = "firstName==\"Petra\"";
		new QueryFilter(query);
	}

	@Test(expected=DatabaseException.class)
	public void moreThanOneWildCharEquals_ExceptionThrown() throws DatabaseException {
		String query = "firstName=\"Pe*t*ra\"";
		new QueryFilter(query);
	}
	
	@Test
	public void acceptsJMBAGEquals() throws DatabaseException {
		StudentRecord record = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		String query = "jmbag = \"0036472203\"";
		IFilter filter = new QueryFilter(query);
		assertTrue(filter.accepts(record));
	}
	
	@Test
	public void acceptsLastNameNotEquals() throws DatabaseException {
		StudentRecord record = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		String query = "lastName != \"Rebrnjak\"";
		IFilter filter = new QueryFilter(query);
		assertTrue(filter.accepts(record));
	}
	
	@Test
	public void acceptsFirstNameLessThan() throws DatabaseException {
		StudentRecord record = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		String query = "firstName < \"Žanamarija\"";
		IFilter filter = new QueryFilter(query);
		assertTrue(filter.accepts(record));
	}
	
	@Test
	public void acceptsJMBAGLessThanOrEquals() throws DatabaseException {
		StudentRecord record = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		String query = "jmbag<=\"1111234567\"";
		IFilter filter = new QueryFilter(query);
		assertTrue(filter.accepts(record));
	}
	
	@Test
	public void acceptsLastNameGreaterThan() throws DatabaseException {
		StudentRecord record = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		String query = "lastName>\"Anković\"";
		IFilter filter = new QueryFilter(query);
		assertTrue(filter.accepts(record));
	}
	
	@Test
	public void acceptsFirstNameGreaterThanOrEquals() throws DatabaseException {
		StudentRecord record = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		String query = "firstName>=\"Petra\"";
		IFilter filter = new QueryFilter(query);
		assertTrue(filter.accepts(record));
	}
	
	@Test
	public void multipleConditions_Accepts() throws DatabaseException {
		StudentRecord record = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		String query = "lastName=\"Re*\" and firstName<\"Žanamarija\" and jmbag<=\"123456789*\"";
		IFilter filter = new QueryFilter(query);
		assertTrue(filter.accepts(record));
	}
	
	@Test
	public void multipleConditions_DoesNotAccepts() throws DatabaseException {
		StudentRecord record = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		String query = "lastName=\"Re*\" and firstName=\"Žanamarija\" and jmbag<=\"123456789*\"";
		IFilter filter = new QueryFilter(query);
		assertFalse(filter.accepts(record));
	}
}
