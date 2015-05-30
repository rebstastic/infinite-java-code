package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * This class represents a computer memory. Opposed to regular computer memory,
 * this memory can store arbitrary Java objects such as {@code String}s and
 * {@code Integer}s.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class MemoryImpl implements Memory {

	/**
	 * The memory size.
	 */
	private int size;

	/**
	 * The array that stores memory objects.
	 */
	private Object[] memory;

	/**
	 * Constructor that initializes the memory size and the memory array.
	 * 
	 * @param size
	 *            The memory size.
	 */
	public MemoryImpl(int size) {
		this.size = size;
		memory = new Object[size];
	}

	@Override
	public void setLocation(int location, Object value) {
		if(location < 0 || location >= size) {
			throw new IndexOutOfBoundsException("Invalid memory access: " + location);
		}
		memory[location] = value;
	}

	@Override
	public Object getLocation(int location) {
		if(location < 0 || location >= size) {
			throw new IndexOutOfBoundsException("Invalid memory access: " + location);
		}
		return memory[location];
	}

}
