package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;

/**
 * This class represents a base class for all graph nodes.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class Node {

	/**
	 * The collection used for children storing.
	 */
	private ArrayBackedIndexedCollection children;

	/**
	 * Adds given <code>child</code> to an internally managed collection of
	 * children. The method refuses to add null as an element.
	 * 
	 * @param child
	 *            The child node.
	 * @throws IllegalArgumentException
	 *             - if the given child is null.
	 */
	public void addChildNode(Node child) {
		if (children == null) {
			children = new ArrayBackedIndexedCollection();
		}
		children.add(child);
	}

	/**
	 * Returns a number of direct children.
	 * 
	 * @return The number of children.
	 */
	public int numberOfChildren() {
		if (children == null) return 0;
		return children.size();
	}

	/**
	 * Returns selected child. If the index is invalid, the
	 * <code>IllegalArgumentException</code> is thrown.
	 * 
	 * @param index
	 *            The index of a child to get.
	 * @return The value of the indexed child.
	 * @throws IllegalArgumentException
	 *             - if the index is invalid.
	 */
	public Node getChild(int index) {
		return (Node) children.get(index);
	}

	public String toString() {
		return "";
	}
}
