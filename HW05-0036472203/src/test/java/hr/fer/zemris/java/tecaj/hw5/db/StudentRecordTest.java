package hr.fer.zemris.java.tecaj.hw5.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class StudentRecordTest {

	@Test
	public void studentRecordFromLine_Equals() {
		StudentRecord record = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		String line = "0036472203	Rebernjak	Petra	5";
		StudentRecord extractedRecord = StudentRecord.studentRecordFromLine(line);
		boolean jmbag = record.getJmbag().equals(extractedRecord.getJmbag());
		boolean lastName = record.getLastName().equals(extractedRecord.getLastName());
		boolean firstName = record.getFirstName().equals(extractedRecord.getFirstName());
		boolean finalGrade = record.getFinalGrade() == extractedRecord.getFinalGrade();
		assertTrue(jmbag&&lastName&&firstName&&finalGrade);
	}
	
	@Test
	public void studentRecordFromLine_NotEquals() {
		StudentRecord record = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		String line = "0036472200	Srebernjak	Petra	5";
		StudentRecord extractedRecord = StudentRecord.studentRecordFromLine(line);
		boolean jmbag = record.getJmbag().equals(extractedRecord.getJmbag());
		boolean lastName = record.getLastName().equals(extractedRecord.getLastName());
		boolean firstName = record.getFirstName().equals(extractedRecord.getFirstName());
		boolean finalGrade = record.getFinalGrade() == extractedRecord.getFinalGrade();
		assertFalse(jmbag&&lastName&&firstName&&finalGrade);
	}
	
	@Test
	public void isValid_ExpectedTrue() {
		StudentRecord record = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		assertTrue(record.isValid());
	}
	
	@Test
	public void isValid_InvalidJMBAG_ExpectedFalse() {
		StudentRecord record = new StudentRecord("123456789123", "Rebernjak", "Petra", 5);
		assertFalse(record.isValid());
	}
	
	@Test
	public void isValid_InvalidLastName_ExpectedFalse() {
		StudentRecord record = new StudentRecord("0036472203", "Rebernj021ak", "Petra", 5);
		assertFalse(record.isValid());
	}
	
	@Test
	public void isValid_InvalidFirstName_ExpectedFalse() {
		StudentRecord record = new StudentRecord("0036472203", "Rebernjak", "Petr!!a", 5);
		assertFalse(record.isValid());
	}
	
	@Test
	public void isValid_InvalidFinalGrade_ExpectedFalse() {
		StudentRecord record = new StudentRecord("0036472203", "Rebernjak", "Petra", 12);
		assertFalse(record.isValid());
	}
	
	@Test
	public void canNotParse_ExpectedNullRecord() {
		String line = "0022114455	hgo	ohdg	ohsgo";
		assertNull(StudentRecord.studentRecordFromLine(line));
	}
	
	@Test
	public void equals_SameObject_ExpectedTrue() {
		StudentRecord record = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		assertTrue(record.equals(record));
	}
	
	@Test
	public void equals_SameJMBAG_ExpectedTrue() {
		StudentRecord record1 = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		StudentRecord record2 = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		assertTrue(record1.equals(record2));
	}
	
	@Test
	public void equals_NotStudentRecordInstance_ExpectedFalse() {
		StudentRecord record = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		assertFalse(record.equals(new String("Lala")));
	}
	
	@Test
	public void equals_DifferentJMBAG_ExpectedFalse() {
		StudentRecord record1 = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		StudentRecord record2 = new StudentRecord("0036472000", "Rebernjak", "Petra", 5);
		assertFalse(record1.equals(record2));
	}
	
	@Test
	public void hashCode_Equals() {
		StudentRecord record1 = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		StudentRecord record2 = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		assertEquals(record1.hashCode(), record2.hashCode());
	}
	
	@Test
	public void hashCode_Null() {
		StudentRecord record1 = new StudentRecord("0036472203", "Rebernjak", "Petra", 5);
		StudentRecord record2 = new StudentRecord(null, "lala", "nana", 5);
		assertNotEquals(record1.hashCode(), record2.hashCode());
	}


}
