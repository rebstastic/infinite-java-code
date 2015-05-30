package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represents a {@code jumpifTrue} instruction. Format of this
 * instruction is {@code jumpIfTrue location}. Argument {@code location}
 * represents a location of a next wanted instruction to jump if the value in
 * the register flag is set to {@code true}.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class InstrJumpIfTrue implements Instruction {

	/**
	 * The location of a next wanted instruction to jump if true.
	 */
	private int location;

	/**
	 * Constructor that initializes the {@code jumpIfTrue} instruction
	 * arguments.
	 * 
	 * @param args
	 *            {@code jumpifTrue} instruction arguments.
	 * @throws IllegalArgumentException
	 *             - if the number of arguments in not one.
	 */
	public InstrJumpIfTrue(List<InstructionArgument> args) {
		if (args.size() != 1) {
			throw new IllegalArgumentException(
					"JumpIfTrue instruction expects one argument.");
		}
		if (!args.get(0).isNumber()) {
			throw new IllegalArgumentException(
					"JumpIfTrue instruction expects an instruction location as an argument. Given argument: "
							+ args.get(0));
		}

		location = ((Integer) args.get(0).getValue()).intValue();
	}

	@Override
	public boolean execute(Computer computer) {
		if (computer.getRegisters().getFlag()) {
			computer.getRegisters().setProgramCounter(location);
		}
		return false;
	}

}
