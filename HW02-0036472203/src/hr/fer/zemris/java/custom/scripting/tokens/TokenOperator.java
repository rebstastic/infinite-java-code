package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * <p>
 * This class represents an operator token. It inherits from <code>Token</code>
 * class.
 * </p>
 *
 * <p>
 * Example: <code>+</code>, <code>%</code>, <code>^</code>
 * </p>
 * 
 * @author Petra Rebernjak - 0036472203
 * @see {@link Token}
 */
public class TokenOperator extends Token {

	/**
	 * The symbol of an operator token.
	 */
	private String symbol;

	/**
	 * Constructor. Initializes the symbol of an operator token.
	 * 
	 * @param symbol
	 *            The symbol of an operator token.
	 */
	public TokenOperator(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Returns a symbol of an operator token.
	 * 
	 * @return The symbol of an operator token.
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * The string representation of a token.
	 * 
	 * @return The string representation of a token.
	 */
	@Override
	public String asText() {
		return symbol;
	}

}
