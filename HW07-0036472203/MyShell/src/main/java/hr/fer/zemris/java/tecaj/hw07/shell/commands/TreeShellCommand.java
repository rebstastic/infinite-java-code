package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.ArgumentExtractor;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.environment.Environment;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a {@code tree} command. This command expects a single argument:
 * directory name. It prints a directory tree.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * Command name.
	 */
	private static final String commandName = "tree";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = ArgumentExtractor.extract(arguments);

		try {

			if (args.size() == 1) {

				Path root = Paths.get(args.get(0));
				if (!Files.exists(root)) {
					env.writeln("File does not exists.");
					return ShellStatus.CONTINUE;
				}

				if (!Files.isDirectory(root)) {
					env.writeln("File is not directory.");
					return ShellStatus.CONTINUE;
				}

				Files.walkFileTree(root, new IndentPrint(env));

			} else {
				env.writeln("Tree command expects one argument.");
				return ShellStatus.CONTINUE;
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
		description.add("Prints a tree of given directory recursively.");
		return Collections.unmodifiableList(description);
	}

	/**
	 * This class represents a file visitor that prints the file names with
	 * suitable number of whitespaces that represent indent level.
	 * 
	 * @author Petra Rebernjak - 0036472203
	 *
	 */
	private static class IndentPrint implements FileVisitor<Path> {

		/**
		 * Indent level.
		 */
		private int indentLevel;

		/**
		 * Shell environment.
		 */
		private Environment env;

		/**
		 * Constructor.
		 * 
		 * @param env
		 *            Shell environment.
		 */
		public IndentPrint(Environment env) {
			this.env = env;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) throws IOException {
			if (indentLevel == 0) {
				env.writeln(dir.toString());
			} else {
				env.write(String.format("%" + indentLevel + "s%s%n", "",
						dir.getFileName()));
			}
			indentLevel += 2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			env.write(String.format("%" + indentLevel + "s%s%n", "",
					file.getFileName()));
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc)
				throws IOException {

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				throws IOException {
			indentLevel -= 2;
			return FileVisitResult.CONTINUE;
		}

	}

}
