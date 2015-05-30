package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represents an {@code echo} instruction. Format of this instruction
 * is {@code echo rX}. Argument {@code rX} is a register to be output.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class InstrEcho implements Instruction {

	/**
	 * The index of a register to output.
	 */
	private int registerIndex;

	/**
	 * Constructor that initializes the {@code echo} instruction arguments.
	 * 
	 * @param args
	 *            {@code echo} instruction arguments.
	 * @throws IllegalArgumentException
	 *             - if the number of arguments is not one.
	 */
	public InstrEcho(List<InstructionArgument> args) {
		if (args.size() != 1) {
			throw new IllegalArgumentException(
					"Echo instruction expects 1 argument.");
		}
		if (!args.get(0).isRegister()) {
			throw new IllegalArgumentException(
					"Echo instruction expects a register as an argument. Given argument: "
							+ args.get(0));
		}
		registerIndex = ((Integer) args.get(0).getValue()).intValue();
	}

	@Override
	public boolean execute(Computer computer) {
		System.out.print(computer.getRegisters().getRegisterValue(
				registerIndex));
		return false;
	}

}
