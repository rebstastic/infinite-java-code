package hr.fer.zemris.java.tecaj.hw07.shell.environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import hr.fer.zemris.java.tecaj.hw07.shell.MyShell;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;

/**
 * This class represents an environment of {@code MyShell}. Every reading and
 * writing on the shell is performed through this environment.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class ShellEnvironment implements Environment {

	/**
	 * Reader.
	 */
	private BufferedReader reader;

	/**
	 * Writer.
	 */
	private BufferedWriter writer;

	/**
	 * Multiline symbol.
	 */
	private Character multilineSymbol;

	/**
	 * Prompt symbol.
	 */
	private Character promptSymbol;

	/**
	 * Morelines symbol.
	 */
	private Character morelinesSymbol;

	/**
	 * Constructor. Initializes environment symbols to default values.
	 */
	public ShellEnvironment() {
		this.reader = new BufferedReader(new InputStreamReader(System.in));
		this.writer = new BufferedWriter(new OutputStreamWriter(System.out));
		this.multilineSymbol = '|';
		this.promptSymbol = '>';
		this.morelinesSymbol = '\\';
	}

	@Override
	public String readLine() throws IOException {
		return reader.readLine().trim();
	}

	@Override
	public void write(String text) throws IOException {
		writer.write(text);
		writer.flush();
	}

	@Override
	public void writeln(String text) throws IOException {
		writer.write(text);
		writer.newLine();
		writer.flush();
	}

	@Override
	public Iterable<ShellCommand> commands() {
		return (() -> MyShell.getCommands().iterator());
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		morelinesSymbol = symbol;
	}

}
