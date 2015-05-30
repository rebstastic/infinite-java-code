package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.environment.Environment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents an {@code exit} command. This command takes no arguments. It
 * terminates the shell work.
 * 
 * @author Petra Rebernjak - 0036472203
 * 
 */
public class ExitShellCommand implements ShellCommand {

	/**
	 * Command name.
	 */
	private static final String commandName = "exit";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {

			if (arguments != null) {
				env.writeln("Exit command does not expects any arguments.");
				return ShellStatus.CONTINUE;
			}

		} catch (IOException e) {
			throw new RuntimeException("Can not write to the environment.");
		}

		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Termination of a shell.");
		return Collections.unmodifiableList(description);
	}
}
