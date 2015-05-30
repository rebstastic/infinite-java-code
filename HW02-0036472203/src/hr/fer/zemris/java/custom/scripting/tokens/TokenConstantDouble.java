package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * <p>
 * This class represents a constant double token. It inherits from
 * <code>Token</code> class.
 * </p>
 * 
 * <p>
 * Example: <code>7.5</code>, <code>71.2</code>, <code>-12.74</code>
 * </p>
 * 
 * @author Petra Rebernjak - 0036472203
 * @see {@link Token}
 */
public class TokenConstantDouble extends Token {

	/**
	 * The double value of a token.
	 */
	private double value;

	/**
	 * Constructor. Initializes the double value of a token.
	 * 
	 * @param value
	 *            The double value of a token.
	 */
	public TokenConstantDouble(double value) {
		this.value = value;
	}

	/**
	 * Returns the double value of a token.
	 * 
	 * @return The double value of a token.
	 */
	public double getValue() {
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
