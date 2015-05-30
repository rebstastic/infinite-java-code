package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represent a {@code load} instruction. Format of this instruction
 * is {@code load rX, memoryLocation}. Argument {@code rX} represents a register
 * in which the object, located on the {@code memoryLocation} given as a second
 * argument, is going to be stored.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class InstrLoad implements Instruction {

	/**
	 * The index of a register that is going to store the object located on the
	 * memory location given as a second argument.
	 */
	private int registerIndex;

	/**
	 * The memory location of an object that needs to be stored.
	 */
	private int memoryLocation;

	/**
	 * Constructor that initializes the {@code load} instruction arguments.
	 * 
	 * @param args
	 *            {@code load} instruction arguments.
	 * @throws IllegalArgumentException
	 *             - if the number of arguments is not two.
	 */
	public InstrLoad(List<InstructionArgument> args) {
		if (args.size() != 2) {
			throw new IllegalArgumentException(
					"Load instruction expects 2 arguments.");
		}
		if (!args.get(0).isRegister()) {
			throw new IllegalArgumentException(
					"Load instruction expects a register as a first argument. Given argument: "
							+ args.get(0));
		}
		if (!args.get(1).isNumber()) {
			throw new IllegalArgumentException(
					"Load instruction expects a memory location as a second argument. Given argument: "
							+ args.get(1));
		}

		registerIndex = ((Integer) args.get(0).getValue()).intValue();
		memoryLocation = ((Integer) args.get(1).getValue()).intValue();
	}

	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue(registerIndex,
				computer.getMemory().getLocation(memoryLocation));
		return false;
	}

}
