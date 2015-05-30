package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represents a {@code jump} instruction. Format of this instruction
 * is {@code jump location}. Argument {@code location} represents a location of
 * a next wanted instruction to jump.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class InstrJump implements Instruction {

	/**
	 * The location of a next wanted instruction to jump.
	 */
	private int location;

	/**
	 * Constructor that initializes the {@code jump} instruction arguments.
	 * 
	 * @param args
	 *            {@code jump} instruction arguments.
	 * @throws IllegalArgumentException
	 *             - if the number of arguments in not one.
	 */
	public InstrJump(List<InstructionArgument> args) {
		if (args.size() != 1) {
			throw new IllegalArgumentException(
					"Jump instruction expects one argument.");
		}
		if (!args.get(0).isNumber()) {
			throw new IllegalArgumentException(
					"Jump instruction expects an instruction location as an argument. Given argument: "
							+ args.get(0));
		}

		location = ((Integer) args.get(0).getValue()).intValue();
	}

	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setProgramCounter(location);
		return false;
	}

}
