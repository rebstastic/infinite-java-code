package hr.fer.zemris.java.tecaj.hw07.shell.environment;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;

import java.io.IOException;

/**
 * This is an abstraction through which the shell communicates with the user. It
 * needs to be passed to each defined shell method.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public interface Environment {

	/**
	 * Reads a single line from the environment.
	 * 
	 * @return Line that is read.
	 * @throws IOException
	 *             - if IOException occurs
	 */
	String readLine() throws IOException;

	/**
	 * Writes given {@code text} to the environment.
	 * 
	 * @param text
	 *            Text to write.
	 * @throws IOException
	 */
	void write(String text) throws IOException;

	/**
	 * Writes given {@code text} followed by the newline character to the
	 * environment.
	 * 
	 * @param text
	 *            Text to write.
	 * @throws IOException
	 *             -if IOException occurs
	 */
	void writeln(String text) throws IOException;

	/**
	 * Allows the supported commands of the environment to be the target of the
	 * "for-each" loop.
	 * 
	 * @return Factory method to instantiate {@code ShellCommand} iterators.
	 */
	Iterable<ShellCommand> commands();

	/**
	 * Returns currently used multiline symbol.
	 * 
	 * @return Currently used multiline symbol.
	 */
	Character getMultilineSymbol();

	/**
	 * Sets multiline symbol to given {@code symbol}.
	 * 
	 * @param symbol
	 *            The new multiline symbol.
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Returns currently used prompt symbol.
	 * 
	 * @return Currently used prompt symbol.
	 */
	Character getPromptSymbol();

	/**
	 * Sets prompt symbol to given {@code symbol}.
	 * 
	 * @param symbol
	 *            The new prompt symbol.
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Returns currently used morelines symbol.
	 * 
	 * @return Currently used morelines symbol.
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets morelines symbol to given {@code symbol}.
	 * 
	 * @param symbol
	 *            The new morelines symbol.
	 */
	void setMorelinesSymbol(Character symbol);

}
