package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represents a {@code move} instruction. Format of this instruction
 * is {@code move rX rY}. The value stored in the register {@code rY} is going
 * to be moved to the register {@code rX}.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class InstrMove implements Instruction {

	/**
	 * The index of a register to store a value from the second given register.
	 */
	private int indexR1;

	/**
	 * The index of a register that contains a value to be stored in first given
	 * register.
	 */
	private int indexR2;

	/**
	 * Constructor that initializes the {@code move} instruction arguments.
	 * 
	 * @param args
	 *            {@code move} instruction arguments.
	 * @throws IllegalArgumentException
	 *             - if the number of arguments is not two.
	 */
	public InstrMove(List<InstructionArgument> args) {
		if (args.size() != 2) {
			throw new IllegalArgumentException(
					"Move instruction expects 2 arguments.");
		}
		if (!args.get(0).isRegister()) {
			throw new IllegalArgumentException(
					"Move instruction expects a register as a first argument. Given argument: "
							+ args.get(0));
		}
		if (!args.get(1).isRegister()) {
			throw new IllegalArgumentException(
					"Move instruction expects a register as a second argument. Given argument: "
							+ args.get(1));
		}

		indexR1 = ((Integer) args.get(0).getValue()).intValue();
		indexR2 = ((Integer) args.get(1).getValue()).intValue();
	}

	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue(indexR1,
				computer.getRegisters().getRegisterValue(indexR2));
		return false;
	}

}
