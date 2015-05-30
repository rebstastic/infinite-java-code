package hr.fer.zemris.linearna;

import java.util.Formatter;

/**
 * This class represents a vector. Common vector operations are provided.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public abstract class AbstractVector implements IVector {

	@Override
	public IVector copyPart(int newDimension) {
		if (newDimension < 0) {
			throw new IllegalArgumentException("Dimension must be positive.");
		}
		double[] elements = new double[newDimension];
		for (int i = newDimension - 1; i >= 0; i--) {
			if (i > this.getDimension() - 1) {
				elements[i] = 0;
				continue;
			} else {
				elements[i] = this.get(i);
			}
		}
		return new Vector(elements);
	}

	@Override
	public IVector add(IVector other) throws IncompatibleOperandException {
		if (other == null) {
			throw new IllegalArgumentException("Argument is null.");
		}
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException(
					"Vector addition is not defined for two vectors of different dimension.");
		}
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) + other.get(i));
		}
		return this;
	}

	@Override
	public IVector nAdd(IVector other) throws IncompatibleOperandException {
		return this.copy().add(other);
	}

	@Override
	public IVector sub(IVector other) throws IncompatibleOperandException {
		if (other == null) {
			throw new IllegalArgumentException("Argument is null.");
		}
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException(
					"Vector substraction is not defined for two vectors of different dimension.");
		}
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) - other.get(i));
		}
		return this;
	}

	@Override
	public IVector nSub(IVector other) throws IncompatibleOperandException {
		return this.copy().sub(other);
	}

	@Override
	public IVector scalarMultiply(double scalar) {
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) * scalar);
		}
		return this;
	}

	@Override
	public IVector nScalarMultiply(double scalar) {
		return this.copy().scalarMultiply(scalar);
	}

	@Override
	public double norm() {
		double[] elements = this.toArray();
		double sum = 0;
		for (int i = elements.length - 1; i >= 0; i--) {
			sum += elements[i] * elements[i];
		}
		return Math.sqrt(sum);
	}

	@Override
	public IVector normalize() {
		final double norm = this.norm();
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) / norm);
		}
		return this;
	}

	@Override
	public IVector nNormalize() {
		return this.copy().normalize();
	}

	@Override
	public double cosine(IVector other) {
		return (this.nNormalize()).scalarProduct(other.nNormalize());
	}

	@Override
	public double scalarProduct(IVector other)
			throws IncompatibleOperandException {
		if (other == null) {
			throw new IllegalArgumentException("Argument is null.");
		}
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException(
					"Scalar product is not defined for two vectors of different dimension.");
		}
		double result = 0;
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			result += this.get(i) * other.get(i);
		}
		return result;
	}

	@Override
	public IVector nVectorProduct(IVector other) {
		if (other == null) {
			throw new IllegalArgumentException("Argument is null.");
		}
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException(
					"Vector product is not defined for two vectors of different dimension.");
		}
		if (this.getDimension() == 2) {
			double[][] m = new double[2][2];
			m[0] = this.toArray();
			m[1] = other.toArray();
			return new Vector((new Matrix(2, 2, m, true)).determinant());
		}
		return new Vector(this.get(1) * other.get(2) - this.get(2)
				* other.get(1), this.get(2) * other.get(0) - this.get(0)
				* other.get(2), this.get(0) * other.get(1) - this.get(1)
				* other.get(0));
	}

	@Override
	public IVector nFromHomogeneus() {
		if (this.getDimension() == 1) {
			throw new IncompatibleOperandException(
					"Homogeneus vector dimension must be greater than one.");
		}

		double lastElement = this.get(this.getDimension() - 1);
		IVector novi = this.copyPart(this.getDimension() - 1);
		return novi.scalarMultiply(1 / lastElement);
	}

	@Override
	public IMatrix toRowMatrix(boolean liveView) {
		return new MatrixVectorView(liveView ? this : this.copy(), false);
	}

	@Override
	public IMatrix toColumnMatrix(boolean liveView) {
		return new MatrixVectorView(liveView ? this : this.copy(), false);
	}

	@Override
	public double[] toArray() {
		double[] elements = new double[this.getDimension()];
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			elements[i] = this.get(i);
		}
		return elements;
	}

	/**
	 * Returns a pretty formatted string representing the vector elements with
	 * given precision.
	 * 
	 * @param precision
	 *            The precision.
	 * @return String representation of this Vector
	 */
	public String toString(int precision) {
		String format = "%." + String.valueOf(precision) + "f ";
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		Formatter formatter = new Formatter(sb);
		for (int i = 0; i < this.getDimension(); i++) {
			formatter.format(format, this.get(i));
		}
		// Deleting the last blank.
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		formatter.close();
		return sb.toString();
	}

	@Override
	public String toString() {
		return toString(3);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof IVector))
			return false;

		IVector other = (IVector) obj;

		if (this.getDimension() != other.getDimension()) {
			return false;
		}

		for (int i = 0, length = this.getDimension(); i < length; i++) {
			if (Math.abs(Math.abs(this.get(i)) - Math.abs(other.get(i))) > 1E-7) {
				return false;
			}
		}

		return true;
	}

	@Override
	public int hashCode() {
		return this.toArray().hashCode();
	}

}