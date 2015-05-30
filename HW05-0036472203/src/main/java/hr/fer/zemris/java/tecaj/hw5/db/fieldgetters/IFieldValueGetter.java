package hr.fer.zemris.java.tecaj.hw5.db.fieldgetters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * A strategy which is responsible for obtaining a requested field value from
 * <code>StudentRecord</code>.
 * 
 * @author Petra Rebernjak - 0036472203
 */
public interface IFieldValueGetter {

	/**
	 * <p>
	 * Obtains a requested field value from <code>StudentRecord</code>.
	 * </p>
	 * <p>
	 * Every class that implements <code>IFieldValueGetter</code> interface must
	 * offer an implementation of this method
	 * </p>
	 * 
	 * @param record
	 *            The record from which the field value is got.
	 * @return The field value.
	 */
	String get(StudentRecord record);

}
