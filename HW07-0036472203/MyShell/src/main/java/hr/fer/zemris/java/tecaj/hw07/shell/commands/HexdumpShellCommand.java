package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.ArgumentExtractor;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.environment.Environment;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a {@code hexdump} command. This command takes a single argument:
 * file name. It produces hex-output of a given file.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class HexdumpShellCommand implements ShellCommand {

	/**
	 * Command name.
	 */
	private static final String commandName = "hexdump";

	/**
	 * Number of bytes shown in one line.
	 */
	private static final int BYTES_PER_LINE = 16;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = ArgumentExtractor.extract(arguments);

		try {

			if (args.size() == 1) {

				Path file = Paths.get(args.get(0));

				if (!Files.isReadable(file)) {
					env.writeln("Can not read a file.");
					return ShellStatus.CONTINUE;
				}

				InputStream input = new BufferedInputStream(
						new FileInputStream(file.toFile()));
				byte[] data = new byte[BYTES_PER_LINE];
				int lineNumber = 0;
				while (true) {
					int dataSize = input.read(data);
					if (dataSize == -1) {
						break;
					}
					String hexLineNumber = getHexLineNumber(lineNumber++);
					String hexData = getHexFromBytes(data);
					String subsetOfChars = getSubsetOfCharacters(data);

					String line = hexLineNumber + ": " + hexData + "| "
							+ subsetOfChars;
					env.writeln(line);
				}

				input.close();

			} else {
				env.writeln("Hexdump command expects one argument.");
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
		description.add("Produces hex-output of a file.");
		return Collections.unmodifiableList(description);
	}

	/**
	 * Returns hexidecimal representation of a line number.
	 * 
	 * @param lineNumber
	 *            The line number.
	 * @return Hexidecimal representation of a line number.
	 */
	private String getHexLineNumber(int lineNumber) {
		String hexNumber = Integer.toHexString(lineNumber * 16).toUpperCase();
		int leadingZeros = 8 - hexNumber.length();
		return String.format("%0" + leadingZeros + "d%s", 0, hexNumber);
	}

	/**
	 * Returns hexidecimal representation of a single line. Every character is
	 * represented as hexidecimal number.
	 * 
	 * @param data
	 *            The line to represent in hexidecimal.
	 * @return Hexidecimal representation of given line.
	 */
	private String getHexFromBytes(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < BYTES_PER_LINE; i++) {
			String hexNumber = Integer.toHexString(data[i] & 0xFF)
					.toUpperCase();
			hexNumber = hexNumber.length() < 2 ? "0" + hexNumber : hexNumber;
			sb.append(hexNumber);
			if (i == BYTES_PER_LINE / 2 - 1) {
				sb.append("|");
			} else {
				sb.append(" ");
			}
		}
		return sb.toString();
	}

	/**
	 * Returns the representation of a line using standard characters. If a
	 * character is not standard, "." is shown.
	 * 
	 * @param data
	 *            The line to represent using standard characters.
	 * @return The representation of a line using standard characters.
	 */
	private String getSubsetOfCharacters(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < BYTES_PER_LINE; i++) {
			String character = (data[i] < 32 || data[i] > 127) ? "." : String
					.format("%c", data[i]);
			sb.append(character);
		}
		return sb.toString();
	}
}
