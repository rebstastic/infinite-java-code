package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

/**
 * This class is a node representing a single for-loop construct. It inherits
 * from <code>Node</code> class.
 * 
 * @author Petra Rebernjak - 0036472203
 * @see {@link Node}
 */
public class ForLoopNode extends Node {

	/**
	 * The first parameter in FOR-tag. It represents a variable that stores the
	 * iteration content.
	 */
	private TokenVariable variable;
	/**
	 * The second parameter in FOR-tag. The start expression of the variable.
	 */
	private Token startExpression;
	/**
	 * The third parameter in FOR-tag. The end expression of the variable.
	 */
	private Token endExpression;
	/**
	 * The fourth parameter in FOR-tag. Optional. The step expression of the
	 * variable.
	 */
	private Token stepExpression; // It can be null.

	/**
	 * Constructor. Initializes the FOR-tag parameters.
	 * 
	 * @param variable
	 *            The variable to iterate over.
	 * @param startExpression
	 *            The start expression of the variable.
	 * @param endExpression
	 *            The end expression of the variable.
	 * @param stepExpression
	 *            The step expression of the variable.
	 */
	public ForLoopNode(TokenVariable variable, Token startExpression,
			Token endExpression, Token stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Returns the iteration variable.
	 * 
	 * @return The iteration variable.
	 */
	public TokenVariable getVariable() {
		return variable;
	}

	/**
	 * Returns the start expression of an iteration variable.
	 * 
	 * @return The start expression of an iteration variable.
	 */
	public Token getStartExpression() {
		return startExpression;
	}

	/**
	 * Returns the end expression of an iteration variable.
	 * 
	 * @return The end expression of an iteration variable.
	 */
	public Token getEndExpression() {
		return endExpression;
	}

	/**
	 * Returns the step expression of an iteration variable.
	 * 
	 * @return The step expression of an iteration variable.
	 */
	public Token getStepExpression() {
		return stepExpression;
	}

	/**
	 * Returns the string representation of the FOR-tag and all of its children
	 * in the tree structure.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$ FOR ");
		sb.append(variable.asText() + " ");
		sb.append(startExpression.asText() + " ");
		sb.append(endExpression.asText() + " ");
		if (stepExpression != null) {
			sb.append(stepExpression.asText() + " ");
		}
		sb.append("$}");
		for (int i = 0; i < this.numberOfChildren(); i++) {
			sb.append(this.getChild(i).toString());
		}
		sb.append("{$END$}");
		return sb.toString();
	}

}
