package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.environment.Environment;

/**
 * This interface represents a shell command that can be executed. All classes
 * that implements this interface should override the three methods:
 * {@code executeComand}, {@code getCommandName} and
 * {@code getCommandDescription}.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public interface ShellCommand {

	/**
	 * Executes the command.
	 * 
	 * @param env
	 *            The environment.
	 * @param arguments
	 *            The command arguments.
	 * @return The status of a shell after command execution.
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Returns command name.
	 * 
	 * @return Command name.
	 */
	String getCommandName();

	/**
	 * Returns command description.
	 * 
	 * @return Command description.
	 */
	List<String> getCommandDescription();

}
