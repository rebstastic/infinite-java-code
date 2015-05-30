package hr.fer.zemris.java.tecaj.hw3;

import java.util.Iterator;

/**
 * This class represents an integer sequence. It allows user to loop from
 * specified integer to specified integer with given step. The for-each
 * iteration is provided.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */

public class IntegerSequence implements Iterable<Integer> {
	/**
	 * The first value in the sequence.
	 */
	private int start;

	/**
	 * The last value in the sequence.
	 */
	private int end;

	/**
	 * The step between two contiguous values in the sequence.
	 */
	private int step;

	/**
	 * Constructor. Initializes the start, end and step value.
	 * 
	 * @param start
	 *            The first value in the sequence.
	 * @param end
	 *            The last value in the sequence.
	 * @param step
	 *            The step between two contiguous values in the sequence.
	 */
	public IntegerSequence(int start, int end, int step) {
		if (start > end && step <= 0 || start < end && step >= 0) {
			throw new IllegalArgumentException(
					"Can not iterate through given sequence.");
		}
		this.start = start;
		this.end = end;
		this.step = step;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new IntegerSequenceIterator();
	}

	/**
	 * An iterator over the integer sequence.
	 * 
	 * @author Petra Rebernjak - 0036472203
	 *
	 */
	private class IntegerSequenceIterator implements Iterator<Integer> {

		/**
		 * The current integer value in the sequence.
		 */
		private int current;

		/**
		 * Constructor. Initializes the first integer value in the sequence.
		 */
		public IntegerSequenceIterator() {
			current = start;
		}

		@Override
		public boolean hasNext() {
			return end - current >= 0;
		}

		@Override
		public Integer next() {
			int ret = current;
			current += step;
			return ret;
		}
	}
}
