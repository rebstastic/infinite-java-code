package hr.fer.zemris.java.custom.scripting.parser;

/**
 * This class represents an exception that's thrown when the document can not be
 * parsed.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SmartScriptParserException() {

	}

	public SmartScriptParserException(String message) {
		super(message);
	}

	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}

	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}

}
