package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Exception thrown when errors occur while manipulating the database.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class DatabaseException extends Exception {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            The error message.
	 */
	public DatabaseException(String message) {
		super(message);
	}

}
