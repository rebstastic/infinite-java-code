package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Instances of this class represent records for each student.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class StudentRecord {

	/**
	 * Student's JMBAG.
	 */
	private String jmbag;

	/**
	 * Student's last name.
	 */
	private String lastName;

	/**
	 * Student's first name.
	 */
	private String firstName;

	/**
	 * Student's final grade.
	 */
	private int finalGrade;

	/**
	 * Constructs a student record and initializes needed information about this
	 * student record.
	 * 
	 * @param jmbag
	 *            Student's JMBAG.
	 * @param lastName
	 *            Student's last name.
	 * @param firstName
	 *            Student's first name.
	 * @param finalGrade
	 *            Student's final grade.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName,
			int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Creates a <code>StudentRecord</code> instance by parsing and extracting
	 * student record informations from a line.
	 * 
	 * @param line
	 *            A line to parse needed informations about student record.
	 * @return Instance of student record.
	 */
	public static StudentRecord studentRecordFromLine(String line) {
		String[] parts = line.split("[\t]");
		String jmbag = null;
		String lastName = null;
		String firstName = null;
		int finalGrade = 0;
		try {
			// Number and type of arguments check.
			jmbag = parts[0];
			lastName = parts[1];
			firstName = parts[2];
			finalGrade = Integer.parseInt(parts[3]);
		} catch (Exception e) {
			return null;
		}
		return new StudentRecord(jmbag, lastName, firstName, finalGrade);
	}

	/**
	 * Returns student's JMBAG.
	 * 
	 * @return Student's JMBAG.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns student's last name.
	 * 
	 * @return Student's last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns student's first name.
	 * 
	 * @return Student's first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns student's final grade.
	 * 
	 * @return Student's final grade.
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Returns <code>true</code> if and only if all of the student record
	 * informations are valid. JMBAG is a string of 10 digits. Last name is a
	 * string of letters and single spaces. First name is a string of letters,
	 * single spaces and "-". Final grade is a digit 1-5.
	 * 
	 * @return <code>true</code> if student record is valid, <code>false</code>
	 *         otherwise.
	 */
	public boolean isValid() {
		if (!jmbag.matches("[0-9]{10}")) {
			return false;
		}
		if (!lastName.matches("(\\p{L}+)([\\s+-]\\p{L}+)*")) {
			return false;
		}
		if (!firstName.matches("(\\p{L}+)(-\\p{L}+)*")) {
			return false;
		}
		if (finalGrade < 1 || finalGrade > 5) {
			return false;
		}
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		// Self-comparison.
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof StudentRecord)) {
			return false;
		}

		StudentRecord student = (StudentRecord) obj;
		if (this.jmbag.equals(student.jmbag)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return jmbag == null ? 0 : jmbag.hashCode();
	}

}
