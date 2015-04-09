package hr.fer.zemris.java.hw2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * This class demonstrates a document parsing. The great test is to parse a
 * document into a tree structure using {@link SmartScriptParser} class. Then
 * create a document out of the obtained tree. Repeat the process with the
 * obtained document. If the output is structurally equal, the parser is good.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class SmartScriptTester {

	/**
	 * <p>
	 * Demonstrates a parser usage.
	 * </p>
	 * 
	 * <p>
	 * This method is called once the program is run
	 * </p>
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {

		String docBody = null;

		try {
			docBody = new String(Files.readAllBytes(Paths
					.get("examples/doc4.txt")), StandardCharsets.UTF_8);

		} catch (IOException e) {
			System.err.println("Invalid path.");
			System.exit(-1);
		}

		// Create a tree structure of a document.
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.err.println("Unable to parse the document!");
			System.err.println(e.getMessage());
			System.exit(-1);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		// Create a document out of the obtained tree structure.
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);

		System.out.println();
		System.out.println();

		// Pass the obtained document in order to create a tree structure.
		SmartScriptParser parser2 = null;
		try {
			parser2 = new SmartScriptParser(originalDocumentBody);
		} catch (SmartScriptParserException e) {
			System.err.println("Unable to parse the document!");
			System.err.println(e.getMessage());
			System.exit(-1);
		}

		// Create a document out of the second tree structure.
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = createOriginalDocumentBody(document2);
		System.out.println(originalDocumentBody2);

	}

	/**
	 * Returns a string representation of a tree structure of a document.
	 * 
	 * @param document
	 *            The reference of a tree structure of a document
	 * @return The string representation of a tree structure of a document.
	 */
	private static String createOriginalDocumentBody(DocumentNode document) {
		return document.toString();
	}

}
