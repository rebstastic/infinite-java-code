package hr.fer.zemris.java.tecaj.hw5.db.fieldgetters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Last name field value getter.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class LastNameFieldGetter implements IFieldValueGetter {

	@Override
	public String get(StudentRecord record) {
		return record.getLastName();
	}

}
