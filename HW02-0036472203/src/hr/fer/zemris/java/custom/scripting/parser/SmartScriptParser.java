package hr.fer.zemris.java.custom.scripting.parser;

import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

/**
 * <p>
 * This class is used for: parsing a document into tree structure; creating a
 * document from tree structure. The supported language consist of text,
 * FOR-tags and ECHO-tags. FOR-tag is non-empty tag, therefore it has to be
 * closed with END-tag. Otherwise, the exception is thrown.
 * </p>
 * 
 * @author Petra Rebernjak - 0036472203
 * 
 */
public class SmartScriptParser {

	/**
	 * States of state machine used for splitting a document in text and tags.
	 */
	private enum DocState {
		INITIAL, TEXT, ESCAPED_TEXT, START_TAG, TAG, END_TAG
	}

	/**
	 * States of state machine used for parsing a tag.
	 */
	private enum TagState {
		INIT, VAR, INT, DOUBLE, SIGN, STR, ENTER_INNER_STR, INNER_STR, EXIT_INNER_STR, FUN, OPERATOR, ENTER_FUN
	}

	/**
	 * Document data in character array.
	 */
	private char[] data;

	/**
	 * A collection used for separating text and tags.
	 */
	private ArrayBackedIndexedCollection textAndTags;

	/**
	 * A stack used for generating a tree structure of a document.
	 */
	private ObjectStack stack;

	/**
	 * A base node in document tree structure.
	 */
	private DocumentNode documentNode;

	/**
	 * Assistant string builder.
	 */
	StringBuilder sb;

	/**
	 * Constructor. It accepts a document body and delegates the parsing to
	 * separate method that will perform an actual job.
	 * 
	 * @param documentBody
	 *            String representation of the document.
	 */
	public SmartScriptParser(String documentBody) {
		// Delegate the parsing to separate method (in the same class) that will
		// perform the actual job.
		sb = new StringBuilder();
		data = documentBody.toCharArray();
		stack = new ObjectStack();
		try {
			parse();
		} catch (SmartScriptParserException e) {
			throw new SmartScriptParserException(e.getMessage());
		}
	}

	/**
	 * Returns the base node of document tree structure.
	 * 
	 * @return The base node of document tree structure.
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}

	/**
	 * The central method. It creates a tree structure of a document.
	 * 
	 * @throws SmartScriptParserException
	 *             - if document can not be parsed.
	 */
	public void parse() {
		try {
			getTextAndTags();
		} catch (SmartScriptParserException e) {
			throw new SmartScriptParserException(e.getMessage());
		}
		documentNode = new DocumentNode();
		int currentNode = 0;
		stack.push(documentNode);
		while (currentNode < textAndTags.size()) {
			String node = textAndTags.get(currentNode).toString();
			if (node.matches("[{][$]\\s*(?iu)for\\s+.*")) {
				parseForLoopNode(node
						.replaceAll("[{][$]\\s*(?iu)for\\s+", "")
						.replaceAll("\\s*[$][}]\\z", "").trim());
			} else if (node.matches("[{][$]\\s*[=]\\s*.*")) {
				parseEchoNode(node.replaceAll("[{][$]\\s*[=]\\s*", "")
						.replaceAll("\\s*[$][}]\\z", "").trim());
			} else if (node.matches("[{][$]\\s*(?iu)end\\s*[$][}]")) {
				parseEndNode(node.replaceAll(
						"[{][$]\\s*(?iu)end\\s*[$][}]", "").trim());
			} else if (node.matches("[{][$].*")) {
				throw new SmartScriptParserException("Nonexistent tag: " + node);
			} else {
				Node parent;
				try {
					parent = (Node) stack.peek();
				} catch (EmptyStackException e) {
					throw new SmartScriptParserException("END-tag missing.");
				}
				parent.addChildNode(new TextNode(node));

			}
			currentNode++;
		}
	}

	/**
	 * FOR-tag parser method. It extracts and checks the parameters validation.
	 * First parameter must be a variable, second, third, and optionally fourth
	 * must be either integer, double, string or a variable.
	 * 
	 * @param parameters
	 *            The FOR-tag parameters that needs to be parsed.
	 * @throws SmartScriptParserException
	 *             - if FOR-tag cannot be parsed.
	 */
	private void parseForLoopNode(String parameters) {
		Token[] tokens = null;
		try {
			tokens = extractTokens(parameters);
		} catch (SmartScriptParserException e) {
			throw new SmartScriptParserException(e.getMessage());
		}

		if (tokens.length < 3 || tokens.length > 4) {
			throw new SmartScriptParserException(
					"Invalid number of parameters in FOR-tag.");
		}

		if (!(tokens[0] instanceof TokenVariable)) {
			throw new SmartScriptParserException(
					"First parameter in FOR-tag must be a variable.");
		}

		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i] instanceof TokenFunction
					|| tokens[i] instanceof TokenOperator) {
				throw new SmartScriptParserException(
						"Invalid parameter in FOR-tag " + tokens[i].asText());
			}
		}

		ForLoopNode forLoopNode;
		if (tokens.length == 3) {
			forLoopNode = new ForLoopNode((TokenVariable) tokens[0], tokens[1],
					tokens[2], null);
		} else {
			forLoopNode = new ForLoopNode((TokenVariable) tokens[0], tokens[1],
					tokens[2], tokens[3]);
		}

		Node parent;
		try {
			parent = (Node) stack.peek();
		} catch (EmptyStackException e) {
			throw new SmartScriptParserException("END-tag missing.");
		}
		parent.addChildNode(forLoopNode);

		stack.push(forLoopNode);
	}

	/**
	 * ECHO-tag parser method. It extracts and checks the validation of
	 * parameters. There is no number or type constraint.
	 * 
	 * @param parameters
	 *            The ECHO-node parameters that needs to be parsed.
	 * @throws SmartScriptParserException
	 *             - if ECHO-tag can not be parsed.
	 */
	private void parseEchoNode(String parameters) {
		Token[] tokens = null;
		try {
			tokens = extractTokens(parameters);
		} catch (SmartScriptParserException e) {
			throw new SmartScriptParserException(e.getMessage());
		}

		Node parent;
		try {
			parent = (Node) stack.peek();
		} catch (EmptyStackException e) {
			throw new SmartScriptParserException("END-tag missing.");
		}
		parent.addChildNode(new EchoNode(tokens));
	}

	/**
	 * END-node parser method. Check if there's an unclosed FOR-tag.
	 * 
	 * @param parameters
	 *            - The END-tag.
	 * @throws SmartScriptParserException
	 *             - if the END-tag can not be parsed or if there are unclosed
	 *             FOR-tags,
	 */
	private void parseEndNode(String parameters) {
		if (!parameters.equals("")) {
			throw new SmartScriptParserException("Invalid END-tag.");
		}
		try {
			stack.pop();
		} catch (EmptyStackException e) {
			throw new SmartScriptParserException("END-tag missing.");
		}
	}

	/**
	 * <p>
	 * State machine used for parsing a tag. The method extracts parameters from
	 * a parameter string. There is a various number of valid parameter types,
	 * such as, constant integer, constant double, function, variable, string
	 * and operator. It finds the longest possible prefix of a valid token type
	 * before it stores it and moves on.
	 * </p>
	 * 
	 * <p>
	 * Example: {$=
	 * variable123_someMore@function_999"I'm inside of a string \"NOW\"."2.158
	 * $}<br>
	 * Variable : variable123_someMore<br>
	 * Function: @function_999<br>
	 * String: I'm inside of a string \"NOW\".<br>
	 * Constant double: 2.158
	 * </p>
	 * 
	 * @param parameters
	 *            The parameters to extract.
	 * @return The {@link Token} array of parsed parameters.
	 * @throws SmartScriptParserException . if the tag can not be parsed.
	 */
	private Token[] extractTokens(String parameters) {
		ArrayBackedIndexedCollection tokens = new ArrayBackedIndexedCollection();
		int currentPosition = 0;
		TagState state = TagState.INIT;

		while (currentPosition < parameters.length()) {
			char c = parameters.charAt(currentPosition);
			switch (state) {
			case INIT:
				if (Character.isWhitespace(c)) {
					// Nothing happens here.
				} else if (Character.isLetter(c)) {
					sb.append(c);
					state = TagState.VAR;
				} else if (Character.isDigit(c)) {
					sb.append(c);
					state = TagState.INT;
				} else if (c == '@') {
					sb.append(c);
					state = TagState.ENTER_FUN;
				} else if (c == '"') {
					state = TagState.STR;
				} else if (isOperator(String.valueOf(c))) {
					sb.append(c);
					state = TagState.OPERATOR;
				} else {
					throw new SmartScriptParserException(
							"Invalid parameters. Parameter can not start with "
									+ c);
				}
				break;
			case VAR:
				if (Character.isLetter(c) || Character.isDigit(c) || c == '_') {
					sb.append(c);
				} else {
					if (Character.isWhitespace(c)) {
						state = TagState.INIT;
					} else if (c == '@') {
						state = TagState.ENTER_FUN;
					} else if (c == '"') {
						state = TagState.STR;
					} else if (isOperator(String.valueOf(c))) {
						state = TagState.OPERATOR;
					} else {
						throw new SmartScriptParserException(
								"Invalid parameters.");
					}
					TokenVariable var = new TokenVariable(sb.toString());
					tokens.add(var);
					cleanStringBuilder();
					if(!Character.isWhitespace(c) && !(c == '"')) {
						sb.append(c);
					}
				}
				break;
			case INT:
				if (Character.isDigit(c)) {
					sb.append(c);
				} else if (c == '.') {
					sb.append(c);
					state = TagState.DOUBLE;
				} else {
					if (Character.isWhitespace(c)) {
						state = TagState.INIT;
					} else if (Character.isLetter(c)) {
						state = TagState.VAR;
					} else if (c == '@') {
						state = TagState.ENTER_FUN;
					} else if (c == '"') {
						state = TagState.STR;
					} else if (isOperator(String.valueOf(c))) {
						state = TagState.OPERATOR;
					} else {
						throw new SmartScriptParserException(
								"Invalid parameters.");
					}

					TokenConstantInteger tokenInt = new TokenConstantInteger(
							Integer.parseInt(sb.toString()));
					tokens.add(tokenInt);
					cleanStringBuilder();
					if(!Character.isWhitespace(c) && !(c == '"')) {
						sb.append(c);
					}
				}
				break;
			case DOUBLE:
				if (Character.isDigit(c)) {
					sb.append(c);
				} else {
					if (Character.isWhitespace(c)) {
						state = TagState.INIT;
					} else if (Character.isLetter(c)) {
						state = TagState.VAR;
					} else if (c == '@') {
						state = TagState.ENTER_FUN;
					} else if (c == '"') {
						state = TagState.STR;
					} else if (isOperator(String.valueOf(c))) {
						state = TagState.OPERATOR;
					} else {
						throw new SmartScriptParserException(
								"Invalid parameters.");
					}

					TokenConstantDouble tokenDouble = new TokenConstantDouble(
							Double.parseDouble(sb.toString()));
					tokens.add(tokenDouble);
					cleanStringBuilder();
					if(!Character.isWhitespace(c) && !(c == '"')) {
						sb.append(c);
					}
				}
				break;
			case STR:
				if (c == '\\') {
					state = TagState.ENTER_INNER_STR;
				} else if (c == '"') {
					TokenString tokenStr = new TokenString(sb.toString());
					tokens.add(tokenStr);
					cleanStringBuilder();
					state = TagState.INIT;
				} else {
					sb.append(c);
				}
				break;
			case ENTER_INNER_STR:
				if (c == '"') {
					sb.append('\\');
					sb.append(c);
					state = TagState.INNER_STR;
				} else {
					sb.append('\\');
					sb.append(c);
					state = TagState.STR;
				}
				break;
			case INNER_STR:
				if (c == '\\') {
					state = TagState.EXIT_INNER_STR;
				} else if(c == '"') {
					//Outer string closed.
					TokenString tokenStr = new TokenString(sb.toString());
					tokens.add(tokenStr);
					cleanStringBuilder();
					state = TagState.INIT;
				} else {
					sb.append(c);
				}
				break;
			case EXIT_INNER_STR:
				if (c == '"') {
					sb.append('\\');
					sb.append(c);
					state = TagState.STR;
				} else {
					sb.append('\\');
					sb.append(c);
					state = TagState.INNER_STR;
				}
				break;
			case ENTER_FUN:
				if (Character.isLetter(c)) {
					sb.append(c);
					state = TagState.FUN;
				} else {
					throw new SmartScriptParserException(
							"Invalid function name.");
				}
				break;
			case FUN:
				if (Character.isLetter(c) || Character.isDigit(c) || c == '_') {
					sb.append(c);
				} else {
					if (Character.isWhitespace(c)) {
						state = TagState.INIT;
					} else if (c == '@') {
						state = TagState.ENTER_FUN;
					} else if (c == '"') {
						state = TagState.STR;
					} else if (isOperator(String.valueOf(c))) {
						state = TagState.OPERATOR;
					} else {
						throw new SmartScriptParserException(
								"Invalid parameters.");
					}

					TokenFunction tokenFun = new TokenFunction(sb.toString());
					tokens.add(tokenFun);
					cleanStringBuilder();
					if(!Character.isWhitespace(c) && !(c == '"')) {
						sb.append(c);
					}
				}
				break;
			case OPERATOR:
				if (Character.isWhitespace(c)) {
					state = TagState.INIT;
				} else if (Character.isLetter(c)) {
					state = TagState.VAR;
				} else if (Character.isDigit(c)) {
					state = TagState.INT;
				} else if (c == '@') {
					state = TagState.ENTER_FUN;
				} else if (c == '"') {
					state = TagState.STR;
				} else if (isOperator(String.valueOf(c))) {
					state = TagState.OPERATOR;
				} else {
					throw new SmartScriptParserException("Invalid parameters.");
				}

				TokenOperator tokenOperator = new TokenOperator(sb.toString());
				tokens.add(tokenOperator);
				cleanStringBuilder();
				if(!Character.isWhitespace(c) && !(c == '"')) {
					sb.append(c);
				}

				break;
			default:
				break;
			}
			currentPosition++;
		}

		// Add the last token to the array.
		switch (state) {
		case VAR:
			TokenVariable tokenVar = new TokenVariable(sb.toString());
			tokens.add(tokenVar);
			break;
		case INT:
			TokenConstantInteger tokenInt = new TokenConstantInteger(
					Integer.parseInt(sb.toString()));
			tokens.add(tokenInt);
			break;
		case DOUBLE:
			TokenConstantDouble tokenDouble = new TokenConstantDouble(
					Double.parseDouble(sb.toString()));
			tokens.add(tokenDouble);
			break;
		case STR:
			TokenString tokenStr = new TokenString(sb.toString());
			tokens.add(tokenStr);
			break;
		case OPERATOR:
			TokenOperator tokenOperator = new TokenOperator(sb.toString());
			tokens.add(tokenOperator);
			break;
		case FUN:
			TokenFunction tokenFun = new TokenFunction(sb.toString());
			tokens.add(tokenFun);
			break;
		default:
			break;
		}

		// If the string is not closed:
		if (state == TagState.ENTER_INNER_STR || state == TagState.INNER_STR
				|| state == TagState.EXIT_INNER_STR) {
			throw new SmartScriptParserException("Invalid tag parameters.");
		}
		cleanStringBuilder();
		return Arrays.copyOf(tokens.toArray(), tokens.size(), Token[].class);
	}

	/**
	 * State machine used for parsing a document into text and tags.
	 * 
	 * @throws SmartScriptParserException
	 *             - if document can not be parsed.
	 */
	public void getTextAndTags() {
		textAndTags = new ArrayBackedIndexedCollection();
		int currentPosition = 0;
		DocState state = DocState.INITIAL;

		while (currentPosition < data.length) {
			char c = data[currentPosition];
			switch (state) {
			case INITIAL:
				if (c == '\\') {
					sb.append(c);
					state = DocState.ESCAPED_TEXT;
				} else if (c == '{') {
					sb.append(c);
					state = DocState.START_TAG;
				} else {
					sb.append(c);
					state = DocState.TEXT;
				}
				break;
			case ESCAPED_TEXT:
				// This can happen only outside of tags.
				if (c == '\\' || c == '{') {
					sb.append(c);
				} else {
					sb.append('\\');
					sb.append(c);
				}
				state = DocState.TEXT;
				break;
			case TEXT:
				if (c == '{') {
					sb.append(c);
					state = DocState.START_TAG;
				} else if (c == '\\') {
					sb.append(c);
					state = DocState.ESCAPED_TEXT;
				} else {
					sb.append(c);
				}
				break;
			case START_TAG:
				if (c == '$') {
					sb.deleteCharAt(sb.lastIndexOf("{"));
					if(sb.length() != 0) {
						textAndTags.add(sb.toString());
						cleanStringBuilder();
					}
					sb.append("{$");
					state = DocState.TAG;
				} else if (c == '{') {
					sb.append(c);
					state = DocState.START_TAG;
				} else if (c == '\\') {
					sb.append(c);
					state = DocState.ESCAPED_TEXT;
				} else {
					sb.append(c);
					state = DocState.TEXT;
				}
				break;
			case TAG:
				if (c == '$') {
					sb.append(c);
					state = DocState.END_TAG;
				} else {
					sb.append(c);
				}
				break;
			case END_TAG:
				if (c == '}') {
					sb.append(c);
					textAndTags.add(sb.toString());
					cleanStringBuilder();
					state = DocState.INITIAL;
				} else {
					sb.append(c);
					state = DocState.TAG;
				}
				break;
			default:
				break;
			}
			currentPosition++;
		}

		if (state == DocState.TAG || state == DocState.END_TAG) {
			throw new SmartScriptParserException("Tag is not closed.");
		}

		if(sb.length() != 0) {
			textAndTags.add(sb.toString());
		}
		cleanStringBuilder();
	}

	/**
	 * Checks if given string is an operator.
	 * 
	 * @param c
	 *            The string to check.
	 * @return true if c is an operator, false otherwise.
	 */
	private static boolean isOperator(String c) {
		return c.matches("[+-/*%^]");
	}

	/**
	 * Cleans the assistant string builder.
	 */
	private void cleanStringBuilder() {
		sb.delete(0, sb.length());
	}

}
