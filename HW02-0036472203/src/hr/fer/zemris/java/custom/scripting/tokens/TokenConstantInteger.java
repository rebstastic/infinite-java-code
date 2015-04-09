package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * <p>
 * This class represents a constant integer token. It inherits from
 * <code>Token</code> class.
 * </p>
 * 
 * <p>
 * Example: <code>3</code>, <code>77</code>, <code>-1234</code>
 * </p>
 * 
 * @author Petra Rebernjak - 0036472203
 * @see {@link Token}
 */
public class TokenConstantInteger extends Token {

	/**
	 * The integer value of a token.
	 */
	private int value;

	/**
	 * Constructor. Initializes the integer value of a token.
	 * 
	 * @param value
	 *            The integer value of a token.
	 */
	public TokenConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * Returns the integer value of a token.
	 * 
	 * @return The integer value of a token.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns the string representation of a token.
	 * 
	 * @return The string representation of a token.
	 */
	@Override
	public String asText() {
		return String.valueOf(value);
	}

}
