package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * This class represents a computer registers. At disposal are general purpose
 * registers, program counter and one flag. Opposed to regular computer
 * registers, this registers can store arbitrary Java objects such as
 * {@code String}s and {@code Integer}s.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class RegisterImpl implements Registers {

	/**
	 * The number of computer registers.
	 */
	private int regsLen;

	/**
	 * The array of registers.
	 */
	private Object[] registers;

	/**
	 * The register that represents program counter.
	 */
	private int programCounter;

	/**
	 * The register that represent a flag.
	 */
	private boolean flag;

	/**
	 * Constructor that initializes the number of registers and all the
	 * registers.
	 * 
	 * @param regsLen
	 *            The number of registers.
	 */
	public RegisterImpl(int regsLen) {
		this.regsLen = regsLen;
		registers = new Object[regsLen];
		programCounter = 0;
		flag = false;
	}

	@Override
	public Object getRegisterValue(int index) {
		if (index < 0 || index >= regsLen) {
			throw new IndexOutOfBoundsException("Invalid register index: "
					+ index);
		}
		return registers[index];
	}

	@Override
	public void setRegisterValue(int index, Object value) {
		if (index < 0 || index >= regsLen) {
			throw new IndexOutOfBoundsException("Invalid register index: "
					+ index);
		}
		registers[index] = value;
	}

	@Override
	public int getProgramCounter() {
		return programCounter;
	}

	@Override
	public void setProgramCounter(int value) {
		programCounter = value;
	}

	@Override
	public void incrementProgramCounter() {
		programCounter += 1;
	}

	@Override
	public boolean getFlag() {
		return flag;
	}

	@Override
	public void setFlag(boolean value) {
		flag = value;
	}

}
