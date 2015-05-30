package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This class is a node representing a piece of textual data. It inherits from
 * <code>Node</code> class.
 * 
 * @author Petra Rebernjak - 0036472203
 * @see {@link Node}
 */
public class TextNode extends Node {

	/**
	 * The text content.
	 */
	private String text;

	/**
	 * Constructor. Initializes the text content.
	 * 
	 * @param text
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Returns the text content.
	 * 
	 * @return The text content-
	 */
	public String getText() {
		return text;
	}

	/**
	 * The string representation of a text node.
	 */
	@Override
	public String toString() {
		return text;
	}

}
