package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represents a {@code add} instruction. Format of this instruction
 * is {@code add rX, rY, rZ}. All three arguments represent registers. The sum
 * of the values stored in the registers {@code rY} and {@code rZ} is going to
 * be stored in the {@code rX} register.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class InstrAdd implements Instruction {

	/**
	 * The register which is going to store a sum of the values stored in other
	 * two registers.
	 */
	private int indexR1;

	/**
	 * The register that stores first value to add.
	 */
	private int indexR2;

	/**
	 * The register that stores second value to add.
	 */
	private int indexR3;

	/**
	 * Constructor that initializes the {@code add} instruction arguments.
	 * 
	 * @param args
	 *            {@code add} instruction arguments.
	 * @throws IllegalArgumentException
	 *             - if the number of arguments is not three.
	 */
	public InstrAdd(List<InstructionArgument> args) {
		if (args.size() != 3) {
			throw new IllegalArgumentException(
					"Add instruction expects 3 arguments.");
		}
		if (!args.get(0).isRegister()) {
			throw new IllegalArgumentException(
					"Add instruction expects a register as a first argument. Given argument: "
							+ args.get(0));
		}
		if (!args.get(1).isRegister()) {
			throw new IllegalArgumentException(
					"Add instruction expects a register as a second argument. Given argument: "
							+ args.get(1));
		}
		if (!args.get(2).isRegister()) {
			throw new IllegalArgumentException(
					"Add instruction expects a register as a third argument. Given argument: "
							+ args.get(2));
		}

		indexR1 = ((Integer) args.get(0).getValue()).intValue();
		indexR2 = ((Integer) args.get(1).getValue()).intValue();
		indexR3 = ((Integer) args.get(2).getValue()).intValue();
	}

	@Override
	public boolean execute(Computer computer) {
		int value1 = ((Integer) computer.getRegisters().getRegisterValue(
				indexR2)).intValue();
		int value2 = ((Integer) computer.getRegisters().getRegisterValue(
				indexR3)).intValue();

		computer.getRegisters().setRegisterValue(indexR1, value1 + value2);

		return false;
	}

}
