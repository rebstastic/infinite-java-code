package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represents an {@code iinput} instruction. Format of this
 * instruction is {@code iinput location}. Argument {@code location} represents
 * a memory location to set a value that user provides through console.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class InstrIinput implements Instruction {

	/**
	 * The memory location to set a value that user provides through console.
	 */
	private int location;

	/**
	 * Constructor that initializes the {@code iinput} instruction arguments.
	 * 
	 * @param args
	 *            {@code iinput} instruction arguments.
	 * @throws IllegalArgumentException
	 *             - if the number of arguments in not one.
	 */
	public InstrIinput(List<InstructionArgument> args) {
		if (args.size() != 1) {
			throw new IllegalArgumentException(
					"Iinput instruction expects one argument.");
		}
		if (!args.get(0).isNumber()) {
			throw new IllegalArgumentException(
					"Iinput instruction expects a memory location as an argument. Given argument: "
							+ args.get(0));
		}

		location = ((Integer) args.get(0).getValue()).intValue();
	}

	@Override
	public boolean execute(Computer computer) {
		try {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			int number = Integer.parseInt(scanner.nextLine());
			computer.getMemory().setLocation(location, number);
			computer.getRegisters().setFlag(true);
		} catch (Exception e) {
			computer.getRegisters().setFlag(false);
		} 
		return false;
	}

}
