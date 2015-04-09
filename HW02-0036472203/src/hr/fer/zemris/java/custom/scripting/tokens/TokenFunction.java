package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * <p>
 * This class represents a function token. It inherits from <code>Token</code>
 * class. Valid function name starts with @ after which follows zero or more
 * letters, digits or underscores.
 * </p>
 * 
 * <p>
 * Example: <code>@sin()</code>, <code>@decfmt</code>, <code>@f</code>
 * </p>
 * 
 * @author Petra Rebernjak - 0036472203
 * @see {@link Token}
 */
public class TokenFunction extends Token {

	/**
	 * The name of a function token.
	 */
	private String name;

	/**
	 * Constructor. Initializes a name of a function token.
	 * 
	 * @param name
	 *            The name of a function token.
	 */
	public TokenFunction(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of a function token.
	 * 
	 * @return The name of a function token.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the string representation of a token.
	 * 
	 * @return The string representation of a token.
	 */
	@Override
	public String asText() {
		return name;
	}

}
