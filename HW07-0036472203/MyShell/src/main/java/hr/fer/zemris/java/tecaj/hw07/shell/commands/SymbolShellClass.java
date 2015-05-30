package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.ArgumentExtractor;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.environment.Environment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a {@code symbol} command. If started with one argument, currently
 * used environment symbol that the user invoked is printed. If started with two
 * arguments, environment symbol that the user invoked is set to given symbol.
 * Supported environment symbols are: MULTILINE, PROMPT and MORELINES.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class SymbolShellClass implements ShellCommand {

	/**
	 * Command name.
	 */
	private static final String commandName = "symbol";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = ArgumentExtractor.extract(arguments);
		try {

			if (args.size() > 0 && args.size() <= 2) {

				if (args.size() == 1) {
					printSymbol(env, args.get(0));
				} else {
					if (args.get(1).length() != 1) {
						env.writeln("New symbol should be one character: "
								+ args.get(1));
						return ShellStatus.CONTINUE;
					}
					changeSymbol(env, args.get(0), args.get(1).charAt(0));
				}

			} else {
				env.writeln("Symbol command expects one or two arguments.");
			}

		} catch (IOException e) {
			throw new RuntimeException("Can not write to the environment.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Used to change symbols. If"
				+ " started with one argument"
				+ " it prints the currently used"
				+ " symbol. If started with two arguments,"
				+ " it changes the symbol to second argument.");
		return Collections.unmodifiableList(description);
	}

	/**
	 * Prints the currently used environment symbol invoked by user.
	 * 
	 * @param env
	 *            The shell environment.
	 * @param envSymbol
	 *            Enviroment symbol of interest.
	 * @throws IOException
	 *             - if IOException occurs
	 */
	private void printSymbol(Environment env, String envSymbol)
			throws IOException {
		switch (envSymbol) {
		case "MULTILINE":
			env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol()
					+ "'.");
			break;
		case "PROMPT":
			env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'.");
			break;
		case "MORELINES":
			env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol()
					+ "'.");

			break;
		default:
			env.writeln("Symbol of this type not supported: " + envSymbol);
			break;
		}
	}

	/**
	 * Changes currently used environment symbol invoked by user to given
	 * {@code symbol}.
	 * 
	 * @param env
	 *            The shell environment.
	 * @param envSymbol
	 *            Enviroment symbol of interest.
	 * @param symbol
	 *            The new symbol.
	 * @throws IOException
	 *             - if IOException occurs
	 */
	private void changeSymbol(Environment env, String envSymbol,
			Character symbol) throws IOException {
		switch (envSymbol) {
		case "MULTILINE":
			env.setMultilineSymbol(symbol);
			break;
		case "PROMPT":
			env.setPromptSymbol(symbol);
			break;
		case "MORELINES":
			env.setMorelinesSymbol(symbol);
			break;
		default:
			env.writeln("Symbol of this type not supported: " + envSymbol);
			break;
		}

	}

}
