package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * This class represents a computer which consist of a memory and registers. At
 * disposal are general purpose registers, program counter and one flag. Opposed
 * to regular computer systems, general purpose registers and memory store
 * arbitrary Java objects such as {@code String}s and {@code Integer}s.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class ComputerImpl implements Computer {

	/**
	 * Computer memory.
	 */
	private Memory memory;

	/**
	 * Computer registers.
	 */
	private Registers registers;

	/**
	 * Constructor that initializes computer memory and computer registers.
	 * 
	 * @param memorySize
	 *            The memory size.
	 * @param registerNumber
	 *            The number of registers.
	 */
	public ComputerImpl(int memorySize, int registerNumber) {
		memory = new MemoryImpl(memorySize);
		registers = new RegisterImpl(registerNumber);
	}

	@Override
	public Registers getRegisters() {
		return registers;
	}

	@Override
	public Memory getMemory() {
		return memory;
	}

}
