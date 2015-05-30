package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.environment.Environment;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Represents a {@code charset} command. This command takes no arguments. It
 * lists names of supported charsets for currently used Java Virtual Machine.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class CharsetShellCommand implements ShellCommand {

	/**
	 * Command name.
	 */
	private static final String commandName = "charset";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		try {

			if (arguments != null) {
				env.writeln("Charset command does not expect any arguments.");
			} else {
				Set<String> charsets = Charset.availableCharsets().keySet();
				for (String charset : charsets) {
					env.writeln(charset);
				}
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
		description.add("Lists names od supported charsets"
				+ " for currently  used Java platform.");
		return Collections.unmodifiableList(description);
	}

}
