package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represents a {@code testEquals} instruction. Format of this
 * instruction is {@code testEquals rX rY}. If values stored in registers
 * {@code rX} and {@code rY} are equals, the flag register is set to true.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class InstrTestEquals implements Instruction {

	/**
	 * The index of a register that stores a first value to compare.
	 */
	private int indexR1;

	/**
	 * The index of a register that stores a second value to compare.
	 */
	private int indexR2;

	/**
	 * Constructor that initializes the {@code testEquals} instruction
	 * arguments.
	 * 
	 * @param args
	 *            {@code testEquals} instruction arguments.
	 * @throws IllegalArgumentException
	 *             - if the number of arguments in not one.
	 */
	public InstrTestEquals(List<InstructionArgument> args) {
		if (args.size() != 2) {
			throw new IllegalArgumentException(
					"TestEquals instruction expects 2 arguments.");
		}
		if (!args.get(0).isRegister()) {
			throw new IllegalArgumentException(
					"TestEquals instruction expects a register as a first argument. Given argument: "
							+ args.get(0));
		}
		if (!args.get(1).isRegister()) {
			throw new IllegalArgumentException(
					"TestEquals instruction expects a register as a second argument. Given argument: "
							+ args.get(1));
		}

		indexR1 = ((Integer) args.get(0).getValue()).intValue();
		indexR2 = ((Integer) args.get(1).getValue()).intValue();
	}

	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(
				indexR1);
		Object value2 = computer.getRegisters().getRegisterValue(
				indexR2);

		computer.getRegisters().setFlag(value1.equals(value2));

		return false;
	}

}
