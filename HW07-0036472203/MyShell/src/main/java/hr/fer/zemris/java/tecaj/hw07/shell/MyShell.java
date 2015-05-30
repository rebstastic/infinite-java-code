package hr.fer.zemris.java.tecaj.hw07.shell;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.CatShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CharsetShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.LsShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.SymbolShellClass;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.TreeShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.environment.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.environment.ShellEnvironment;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a shell. Supported commands are listed below. Optional
 * parameters are surrounded with square parenthesis.
 * <p>
 * <table border="1">
 * <thead>
 * <tr>
 * <th>Command</th>
 * <th>Arguments</th>
 * <th>Description</th>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>{@code exit}</td>
 * <td>none</td>
 * <td>Termination of a shell.</td>
 * </tr>
 * <tr>
 * <tr>
 * <td>{@code help}</td>
 * <td>[command_name]</td>
 * <td>If started with no arguments, it list names of all supported commands. If
 * started with single argument, it prints name and description of selected
 * command.</td>
 * </tr>
 * <tr>
 * <td>{@code symbol}</td>
 * <td>PROMPT [prompt_symbol]<br>
 * MORELINES [moreline_symbol]<br>
 * MULTILINES</td>
 * <td>Used to change symbols. If started with one argument it prints the
 * currently used symbol. If started with two arguments, it changes the symbol
 * to second argument.</td>
 * </tr>
 * <tr>
 * <td>{@code charset}</td>
 * <td>none</td>
 * <td>Lists names od supported charsets for your Java platform.</td>
 * </tr>
 * <tr>
 * <td>{@code cat}</td>
 * <td>path_to_file [charset_name]</td>
 * <td>Opens given file and writes its content to console using charset from an
 * argument if provided, default platform charset otherwsise.</td>
 * </tr>
 * <tr>
 * <td>{@code ls}</td>
 * <td>path_to_directory</td>
 * <td>Lists a directory content (not recursive).</td>
 * </tr>
 * <tr>
 * <td>{@code tree}</td>
 * <td>path_to_directory</td>
 * <td>Prints a tree of given directory recursively.</td>
 * </tr>
 * <tr>
 * <td>{@code copy}</td>
 * <td>source destination</td>
 * <td>Copys source file to destination file.</td>
 * </tr>
 * <tr>
 * <td>{@code mkdir}</td>
 * <td>directory_name</td>
 * <td>Creates the given directory structure.</td>
 * </tr>
 * <tr>
 * <td>{@code hexdump}</td>
 * <td>file_name</td>
 * <td>Produces hex-output of a file.</td>
 * </tr>
 * </tbody>
 * </table>
 * </p>
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class MyShell {

	/**
	 * Map of supported commands.
	 */
	private static Map<String, ShellCommand> commands;

	/**
	 * Builds map of supported commands during shell startup.
	 */
	static {
		commands = new HashMap<>();
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("symbol", new SymbolShellClass());
		commands.put("charset", new CharsetShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
	}

	/**
	 * Returns the map of supported commands.
	 * 
	 * @return The map of supported commands.
	 */
	public static Collection<ShellCommand> getCommands() {
		return commands.values();
	}

	/**
	 * Environment that communicates with user. It reads user input and writes
	 * response.
	 */
	private static Environment environment = new ShellEnvironment();

	/**
	 * Shell loop. This method is called once the program is run.
	 * 
	 * @param args
	 *            Command line arguments. Expected none.
	 */
	public static void main(String[] args) {

		try {

			environment.writeln("Welcome to MyShell v 1.0");

			while (true) {

				environment.write(environment.getPromptSymbol().toString()
						+ " ");

				String line = environment.readLine();
				if (hasMoreLines(line)) {
					line = removeMorelineSymbol(line);
					while (true) {
						environment.write(environment.getMultilineSymbol()
								.toString() + " ");
						String restOfTheLine = environment.readLine();
						if (hasMoreLines(restOfTheLine)) {
							restOfTheLine = removeMorelineSymbol(restOfTheLine);
							line += restOfTheLine;
						} else {
							line += restOfTheLine;
							break;
						}
					}
				}

				String[] lineParts = line.trim().split(" ", 2);
				String commandName = lineParts[0];
				String arguments = null;
				if (lineParts.length > 1) {
					arguments = lineParts[1].trim();
				}

				ShellCommand command = commands.get(commandName);
				if (command == null) {
					environment.writeln("Command " + commandName
							+ " is not supported in this shell.");
					continue;
				}
				ShellStatus status = command.executeCommand(environment,
						arguments);
				if (status.equals(ShellStatus.TERMINATE)) {
					environment
							.writeln("Thank you for using MyShell! Goodbye!");
					break;
				}
			}

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

	/**
	 * Returns {@code true} if the line ends with multiline symbol,
	 * {@code false} otherwise.
	 * 
	 * @param line
	 *            The line to check.
	 * @return {@code true} if the line ends with multiline symbol,
	 *         {@code false} otherwise.
	 */
	private static boolean hasMoreLines(String line) {
		String morelinesSymbol = environment.getMorelinesSymbol().toString();
		return line.endsWith(" " + morelinesSymbol);
	}

	/**
	 * Returns the line without multiline symbol.
	 * 
	 * @param line
	 *            The line to remove multiline symbol from the end.
	 * @return The line without multiline symbol.s
	 */
	private static String removeMorelineSymbol(String line) {
		return line.substring(0, line.length() - 1);
	}

}
