package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * <p>
 * This class represents a variable token. It inherits from <code>Token</code>
 * class. Valid variable name starts with a letter after which follows zero or
 * more letters, digits or underscores.
 * </p>
 * 
 * <p>
 * Valid variable names: <code>A7_bb</code>, <code>counter</code>,
 * <code>tmp_34</code>
 * </p>
 * 
 * <p>
 * Invalid variable names: <code>_a21</code>, <code>32</code>,
 * <code>3s_ee</code>
 * </p>
 * 
 * @author Petra Rebernjak - 0036472203
 * @see {@link Token}
 */
public class TokenVariable extends Token {

	/**
	 * The variable name of a token.
	 */
	private String name;

	/**
	 * Constructor. Initializes the variable name of a token.
	 * 
	 * @param name
	 *            The variable name of a token.
	 */
	public TokenVariable(String name) {
		this.name = name;
	}

	/**
	 * Returns the variable name of a token.
	 * 
	 * @return The variable name of a token.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns a string representation of a token.
	 * 
	 * @return The string representation of a token.
	 */
	@Override
	public String asText() {
		return name;
	}

}
