package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This class is a base node representing an entire document. It inherits from
 * <code>Node</code> class.
 * 
 * @author Petra Rebernjak - 0036472203
 * @see {@link Node}
 */
public class DocumentNode extends Node {

	/**
	 * Returns a string representation of a tree structure of a document.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < this.numberOfChildren(); i++) {
			sb.append(this.getChild(i).toString());
		}
		return sb.toString();
	}

}
