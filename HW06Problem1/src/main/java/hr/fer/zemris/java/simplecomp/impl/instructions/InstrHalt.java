package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represents a {@code halt} instruction. Format of this instruction
 * is {@code halt}. This instruction halts the processor.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class InstrHalt implements Instruction {

	/**
	 * Constructor.
	 * 
	 * @param args
	 *            No arguments expected.
	 * @throws IllegalArgumentException
	 *             - if the number of arguments is greater than zero.
	 */
	public InstrHalt(List<InstructionArgument> args) {
		if (args.size() != 0) {
			throw new IllegalArgumentException(
					"Halt instruction does not expect any arguments.");
		}
	}

	@Override
	public boolean execute(Computer computer) {
		return true;
	}

}
