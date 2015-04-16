package hr.fer.zemris.java.tecaj.hw5.db.filters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * This class represents a filter which function is to recognize the
 * <code>StudentRecord</code> that satisfies this filter.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public interface IFilter {

	/**
	 * Returns <code>true</code> if filter accepts given student record,
	 * <code>false</code> otherwise.
	 * 
	 * @param record
	 *            The student record to check acceptance within this filter.
	 * @return <code>true</code> if filter accepts given student record,
	 *         <code>false</code> otherwise.
	 */
	boolean accepts(StudentRecord record);
}
