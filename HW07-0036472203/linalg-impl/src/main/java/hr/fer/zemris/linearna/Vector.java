package hr.fer.zemris.linearna;

import java.util.Arrays;

/**
 * This class represents a concrete vector. Common vector operations are
 * provided.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class Vector extends AbstractVector {

	/**
	 * The vector elements.
	 */
	private double[] elements;

	/**
	 * Vector dimension.
	 */
	private int dimension;

	/**
	 * {@code true} if vector is read-only, {@code false} otherwise.
	 */
	private boolean readOnly = false;

	/**
	 * Constructs a vector from the array of elements.
	 * 
	 * @param elements
	 *            Elements of a vector.
	 */
	public Vector(double... elements) {
		this.elements = elements;
		this.dimension = elements.length;
	}

	/**
	 * Constructs the vector that can be read-only. If immutable, it copies
	 * given elements to vector array.
	 * 
	 * @param readOnly
	 *            {@code true} if vector is read-only, {@code false} otherwise.
	 * @param arrayImmutable
	 *            {@code true} if vector is immutable, {@code false} otherwise.
	 * @param elements
	 *            The vector elements.
	 */
	public Vector(boolean readOnly, boolean arrayImmutable, double... elements) {
		this.dimension = elements.length;
		this.readOnly = readOnly;
		this.elements = arrayImmutable ? elements : Arrays.copyOf(elements,
				elements.length);
	}

	@Override
	public double get(int index) {
		if (index < 0 || index > getDimension() - 1) {
			throw new IllegalArgumentException(
					"Invalid index. Allowed values for index: [0, "
							+ (getDimension() - 1) + "].");
		}
		return this.elements[index];
	}

	@Override
	public IVector set(int index, double value) {
		if (readOnly) {
			throw new UnmodifiableObjectException("Vector is read-only.");
		}
		if (index < 0 || index > getDimension() - 1) {
			throw new IllegalArgumentException(
					"Invalid index. Allowed values for index: [0, "
							+ (getDimension() - 1) + "].");
		}
		if (this.readOnly) {
			throw new IllegalArgumentException("This vector is read-only.");
		}
		this.elements[index] = value;
		return this;
	}

	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	public IVector copy() {
		return new Vector(false, false, this.elements);
	}

	@Override
	public IVector newInstance(int dimension) {
		if (dimension <= 0) {
			throw new IllegalArgumentException("Dimension must be positive.");
		}
		return new Vector(false, false, new double[dimension]);
	}

	/**
	 * Returns a vector created by parsing a given string.
	 * 
	 * @param string
	 *            String representation of a vector.
	 * @return The vector created by parsing a given string.
	 */
	public static Vector parseSimple(String string) {
		String[] stringVector = string.trim().split("\\s+");
		double[] elements = new double[stringVector.length];
		for (int i = stringVector.length - 1; i >= 0; i--) {
			try {
				elements[i] = Double.parseDouble(stringVector[i]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(
						"Can not parse string to vector: " + string);
			}
		}
		return new Vector(elements);
	}

}