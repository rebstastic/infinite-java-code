package hr.fer.zemris.java.simplecomp;

import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * This class represents an assembler code simulator. It reads from an assembler
 * code file which path is given either as an command line argument or through
 * the console. All the instructions are executed and the output is presented on
 * the standard output stream.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class Simulator {

	/**
	 * The method is invoked once the program is run.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		String asmExample = null;
		if (args.length == 0) {
			System.out.print("The path to the asembler code file: ");
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			asmExample = scanner.nextLine().trim();
			System.out.println();
		} else if (args.length == 1) {
			asmExample = args[0];
		} else {
			System.err.println("Invalid number of arguments.");
			System.exit(1);
		}

		Computer comp = new ComputerImpl(256, 16);
		InstructionCreator creator = new InstructionCreatorImpl(
				"hr.fer.zemris.java.simplecomp.impl.instructions");
		try {
			ProgramParser.parse(asmExample, comp, creator);
		} catch (Exception e) {
			System.err.println("Error occured: " + e.getMessage());
		}
		ExecutionUnit exec = new ExecutionUnitImpl();
		exec.go(comp);
	}

}
