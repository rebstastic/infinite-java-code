package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.ArgumentExtractor;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.environment.Environment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Represents a {@code ls} command. This command takes a single argument:
 * directory. It writes a directory listing (non recursive). Additional
 * information of a listed file is shown.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class LsShellCommand implements ShellCommand {

	/**
	 * Command name.
	 */
	private static final String commandName = "ls";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = ArgumentExtractor.extract(arguments);

		try {

			if (args.size() == 1) {

				Path file = Paths.get(args.get(0));

				if (!Files.isDirectory(file)) {
					env.writeln("Ls command expects directory.");
					return ShellStatus.CONTINUE;

				}

				File[] children = file.toFile().listFiles();

				for (File child : children) {
					try {
						String firstColumn = getFileInfo(child.toPath());
						String secondColumn = getFileSize(child.toPath());
						String thirdColumn = getFileDateAndTime(child.toPath());
						String forthColumn = getFileName(child.toPath());

						env.writeln(String.format("%s %s %s %s", firstColumn,
								secondColumn, thirdColumn, forthColumn));

					} catch (IOException e) {
						env.writeln(e.getMessage());
					}

				}

			} else {
				env.writeln("Ls command expects one argument.");
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
		description.add("Lists a directory content (not recursive).");
		return Collections.unmodifiableList(description);
	}

	/**
	 * Returns short string representation of a file info. Sequentially: is file
	 * directory, is file readable, is file writable, is file executable.
	 * 
	 * @param file
	 *            The file to collect the info.
	 * @return Short string representation of a file info.
	 */
	private String getFileInfo(Path file) {
		StringBuilder sb = new StringBuilder(4);
		if (Files.isDirectory(file)) {
			sb.append("d");
		} else {
			sb.append("-");
		}
		if (Files.isReadable(file)) {
			sb.append("r");
		} else {
			sb.append("-");
		}
		if (Files.isWritable(file)) {
			sb.append("w");
		} else {
			sb.append("-");
		}
		if (Files.isExecutable(file)) {
			sb.append("x");
		} else {
			sb.append("-");
		}
		return sb.toString();
	}

	/**
	 * Returnes the file size.
	 * 
	 * @param file
	 *            The file to get the size of.
	 * @return The file size.
	 * @throws IOException
	 *             - if IOException occurs
	 */
	private String getFileSize(Path file) throws IOException {
		try {
			return String.format("%10d", Files.size(file));
		} catch (IOException e) {
			throw new IOException("File size could not be read: "
					+ file.getFileName());
		}
	}

	/**
	 * Returns date and time when given file is created.
	 * 
	 * @param file
	 *            The file to get the creation time of.
	 * @return Date and time when given file is created.
	 * @throws IOException
	 *             - if IOException occurs
	 */
	private String getFileDateAndTime(Path file) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasicFileAttributeView faView = Files.getFileAttributeView(file,
				BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes;
		try {
			attributes = faView.readAttributes();
		} catch (IOException e) {
			throw new IOException("File creation time could not be read: "
					+ file.getFileName());
		}
		FileTime fileTime = attributes.creationTime();
		return sdf.format(new Date(fileTime.toMillis()));
	}

	/**
	 * Returns the file name.
	 * 
	 * @param file
	 *            The file to get the name of.
	 * @return The file name.
	 */
	private String getFileName(Path file) {
		return file.getFileName().toString();
	}

}
