package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.filters.IFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains internal list of all the student records in database. It
 * has a method which filters only the records that user wants.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class StudentDatabase {

	/**
	 * Internal list of student records.
	 */
	private List<StudentRecord> studentRecords;

	/**
	 * Collection for fast retrieval of student records when JMBAG is known.
	 */
	private Map<String, StudentRecord> indexMap;

	/**
	 * Creates an internal list of student records and an index for fast
	 * retrieval of student records when JMBAG is known.
	 * 
	 * @param lines
	 *            List of lines from database file.
	 * @throws DatabaseException
	 *             - if database contains invalid student records.
	 */
	public StudentDatabase(List<String> lines) throws DatabaseException {
		studentRecords = new ArrayList<>();
		indexMap = new HashMap<>();
		extractRecords(lines);
	}

	/**
	 * Extracts student records and stores them in the internal list of student
	 * records, and creates an index for fast retrieval of students when JMBAG
	 * is known.
	 * 
	 * @param lines
	 *            List of lines from database file.
	 * @throws DatabaseException
	 *             - if database contains invalid student records.
	 */
	private void extractRecords(List<String> lines) throws DatabaseException {
		for (String line : lines) {
			StudentRecord record = StudentRecord.studentRecordFromLine(line);
			if (record == null) {
				throw new DatabaseException("Invalid database record: " + line);
			}
			if (!record.isValid()) {
				throw new DatabaseException("Invalide database record: " + line);
			}
			studentRecords.add(record);
			indexMap.put(record.getJmbag(), record);
		}
	}

	/**
	 * Uses index to obtain requested record in O(1); if record does not exists,
	 * the method returns null.
	 * 
	 * @param jmbag
	 *            Student't JMBAG.
	 * @return <code>StudentRecord</code> instance with given JMBAG.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return indexMap.get(jmbag);
	}

	/**
	 * This method calls accepts <code>method</code> on given filter-object with
	 * current record; each record for which <code>accepts</code> returns
	 * <code>true</code> is added to temporary list and this list is then
	 * returned by the <code>filter<code/> method.
	 * 
	 * @param filter
	 *            The filter which filters all wanted records.
	 * @return The list of student records which satisfies given filter.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> temp = new ArrayList<>();

		for (StudentRecord record : studentRecords) {
			if (filter.accepts(record)) {
				temp.add(record);
			}
		}
		return temp;
	}

}
