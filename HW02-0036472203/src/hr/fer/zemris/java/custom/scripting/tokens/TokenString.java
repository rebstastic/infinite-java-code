package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * <p>
 * This class represents a string token. It inherits from <code>Token</code>
 * class.
 * </p>
 * 
 * <p>
 * Example: <code>"This is a string."</code>, <code>"New line \n"</code>,
 * <code>"He said \"Hello!\"."</code>
 * </p>
 * 
 * @author Petra Rebernjak - 0036472203
 * @see {@link Token}
 */
public class TokenString extends Token {

	/**
	 * The string token.
	 */
	private String value;

	/**
	 * Constructor. Initializes the string token.
	 * 
	 * @param value
	 *            The string value of a token.
	 */
	public TokenString(String value) {
		this.value = value;
	}

	/**
	 * Returns the string token.
	 * 
	 * @return The string token.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Returns the string representation of a token.
	 * 
	 * @return The string representation of a token.
	 */
	@Override
	public String asText() {
		return "\"" + value + "\"";
	}

}
