package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Arrays;

import hr.fer.zemris.java.custom.scripting.tokens.Token;

/**
 * This class is a node representing a command which generates some textual
 * output dynamically. It inherits from <code>Node</code> class.
 * 
 * @author Petra Rebernjak - 0036472203
 * @see {@link Node}
 */
public class EchoNode extends Node {

	/**
	 * The list of the tokens in ECHO-tag.
	 */
	private Token[] tokens;

	/**
	 * Constructor. Initializes the tokens.
	 * 
	 * @param tokens
	 */
	public EchoNode(Token[] tokens) {
		// Defensive copy. Arrays are mutable. This prevents object changing if
		// the given array changes.
		this.tokens = Arrays.copyOf(tokens, tokens.length, Token[].class);
	}

	/**
	 * Returns the array of tokens in the ECHO-tag.
	 * 
	 * @return The array of tokens in the ECHO-tag.
	 */
	public Token[] getTokens() {
		return tokens;
	}

	/**
	 * Returns the string representation of the ECHO-tag.
	 */
	@Override
	public String toString() {
		String body = "{$= ";
		for (int i = 0; i < tokens.length; i++) {
			body += tokens[i].asText() + " ";
		}
		return body + "$}";
	}

}
