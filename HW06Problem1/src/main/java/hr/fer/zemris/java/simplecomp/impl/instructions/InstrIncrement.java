package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represents a {@code increment} instruction. Format of this
 * instruction is {@code increment rX}. Argument {@code rX} represents a
 * register that stores the value to be incremented by one.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class InstrIncrement implements Instruction {

	/**
	 * The index of a register that stores the value to be incremented by one.
	 */
	private int registerIndex;

	/**
	 * Constructor that initializes the {@code increment} instruction arguments.
	 * 
	 * @param args
	 *            {@code increment} instruction arguments.
	 * @throws IllegalArgumentException
	 *             - if the number of arguments is not one.
	 */
	public InstrIncrement(List<InstructionArgument> args) {
		if (args.size() != 1) {
			throw new IllegalArgumentException(
					"Increment instruction expects one argument.");
		}
		if (!args.get(0).isRegister()) {
			throw new IllegalArgumentException(
					"Increment instruction expects a register as a first argument. Given argument: "
							+ args.get(0));
		}

		registerIndex = ((Integer) args.get(0).getValue()).intValue();
	}

	@Override
	public boolean execute(Computer computer) {
		int value = ((Integer) computer.getRegisters().getRegisterValue(
				registerIndex)).intValue();
		computer.getRegisters().setRegisterValue(registerIndex, value + 1);
		return false;
	}

}
