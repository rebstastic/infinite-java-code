package hr.fer.zemris.java.tecaj.hw07.shell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class that extracts arguments from a {@code String} object. Splitting
 * separator is whitespace. Whitespaces in the inner quotations are ignored.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class ArgumentExtractor {

	/**
	 * Extracts arguments from the given line by whitespace. Whitespaces in the
	 * inner quotations are ignored.
	 * 
	 * @param arguments
	 *            Arguments represented in single {@code String} line.
	 * @return List of extracted arguments.
	 */
	public static List<String> extract(String arguments) {
		List<String> extractedArgs = new ArrayList<>();
		if (arguments != null) {
			String regex = "\"?( )(?=(([^\"]*\"){2})*[^\"]*$)\"?";
			Collections.addAll(extractedArgs, arguments.split(regex));
			for (int i = 0, lenght = extractedArgs.size(); i < lenght; i++) {
				extractedArgs.set(i, extractedArgs.get(i).replaceAll("\"", ""));
			}
		}
		return extractedArgs;
	}
}
