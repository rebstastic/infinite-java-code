package hr.fer.zemris.linearna;

import java.util.Formatter;

/**
 * This class represents a matrix. Common matrix operations are provided.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public abstract class AbstractMatrix implements IMatrix {

	@Override
	public IMatrix nTranspose(boolean liveView) {
		return new MatrixTransposeView((liveView ? this : this.copy()));
	}

	@Override
	public IMatrix add(IMatrix other) {
		if (other == null) {
			throw new IllegalArgumentException("Argument is null.");
		}
		if (this.getRowsCount() != other.getRowsCount()
				|| this.getColsCount() != other.getColsCount()) {
			throw new IncompatibleOperandException(
					"Matrix addition is not defined for two matrices of different dimension.");
		}
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				this.set(i, j, this.get(i, j) + other.get(i, j));
			}
		}
		return this;
	}

	@Override
	public IMatrix nAdd(IMatrix other) {
		return this.copy().add(other);
	}

	@Override
	public IMatrix sub(IMatrix other) {
		if (other == null) {
			throw new IllegalArgumentException("Argument is null.");
		}
		if (this.getRowsCount() != other.getRowsCount()
				|| this.getColsCount() != other.getColsCount()) {
			throw new IncompatibleOperandException(
					"Matrix subtraction is not defined for two matrices of different dimension.");
		}
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				this.set(i, j, this.get(i, j) - other.get(i, j));
			}
		}
		return this;
	}

	@Override
	public IMatrix nSub(IMatrix other) {
		return this.copy().sub(other);
	}

	@Override
	public IMatrix nMultiply(IMatrix other) {
		if (other == null) {
			throw new IllegalArgumentException("Argument is null.");
		}
		if (this.getColsCount() != other.getRowsCount()) {
			throw new IncompatibleOperandException(
					"Matrix multiplication is not defined for given matrices.");
		}
		IMatrix ret = new Matrix(this.getRowsCount(), other.getColsCount());
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = other.getColsCount() - 1; j >= 0; j--) {
				double value = 0;
				for (int k = this.getColsCount() - 1; k >= 0; k--) {
					value += this.get(i, k) * other.get(k, j);
				}
				ret.set(i, j, value);
			}
		}
		return ret;
	}

	@Override
	public double determinant() {
		if (this.getRowsCount() != this.getColsCount()) {
			throw new IncompatibleOperandException(
					"Determinant is not defined for this matrix.");
		}
		double det = 0;
		if (this.getRowsCount() == 1 && this.getColsCount() == 1) {
			return this.get(0, 0);
		}
		for (int i = 0; i < this.getColsCount(); i++) {
			IMatrix smallerMatrix = new MatrixSubMatrixView(this, 0, i);
			det += Math.pow(-1, i) * this.get(0, i)
					* smallerMatrix.determinant();
		}
		return det;
	}

	@Override
	public IMatrix nInvert() {
		if (this.determinant() == 0
				|| this.getRowsCount() != this.getColsCount()) {
			throw new IncompatibleOperandException(
					"Matrix doesn't have an inverse.");
		}
		IMatrix cofactorMatrix = new Matrix(this.getRowsCount(),
				this.getColsCount());
		for (int i = cofactorMatrix.getRowsCount() - 1; i >= 0; i--) {
			for (int j = cofactorMatrix.getColsCount() - 1; j >= 0; j--) {
				double value = (1.0 / this.determinant()) * Math.pow(-1, i + j)
						* ((new MatrixSubMatrixView(this, i, j)).determinant());
				cofactorMatrix.set(i, j, value);
			}
		}
		return cofactorMatrix.nTranspose(true);
	}

	@Override
	public double[][] toArray() {
		double[][] elements = new double[this.getRowsCount()][this
				.getColsCount()];
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				elements[i][j] = this.get(i, j);
			}
		}
		return elements;
	}

	@Override
	public IVector toVector(boolean liveView) {
		if (this.getRowsCount() != 1 && this.getColsCount() != 1) {
			throw new IncompatibleOperandException(
					"Can not make a vector from a matrix. Rows or columns number should be 1.");
		}
		return new VectorMatrixView(liveView ? this : this.copy());
	}

	@Override
	public IMatrix scalarMultiply(double value) {
		for (int i = 0, rows = this.getRowsCount(); i < rows; i++) {
			for (int j = 0, cols = this.getColsCount(); j < cols; j++) {
				this.set(i, j, this.get(i, j) * value);
			}
		}
		return this;
	}

	@Override
	public IMatrix nScalarMultiply(double value) {
		return this.copy().scalarMultiply(value);
	}

	@Override
	public IMatrix makeIdentity() {
		if (this.getColsCount() != this.getRowsCount()) {
			throw new IncompatibleOperandException("Matrix must be square.");
		}

		for (int i = 0, rows = this.getRowsCount(); i < rows; i++) {
			for (int j = 0, cols = this.getColsCount(); j < cols; j++) {
				this.set(i, j, (i == j) ? 1 : 0);
			}
		}
		return this;
	}

	@Override
	public IMatrix subMatrix(int rowIndex, int columnIndex, boolean liveView) {
		if (rowIndex < 0 || rowIndex > this.getRowsCount() - 1) {
			throw new IllegalArgumentException(
					"Invalid row index. Allowed values for row index: [0, "
							+ this.getRowsCount() + "].");
		}

		if (columnIndex < 0 || columnIndex > this.getColsCount() - 1) {
			throw new IllegalArgumentException(
					"Invalid column index. Allowed values for colum index: [0, "
							+ this.getColsCount() + "].");
		}

		return new MatrixSubMatrixView(liveView ? this : this.copy(), rowIndex,
				columnIndex);
	}

	/**
	 * Returns a pretty formatted string representing the matrix elements with
	 * given precision.
	 * 
	 * @param precision
	 *            The precision.
	 * @return String representation of this matrix
	 */
	public String toString(int precision) {
		String format = "%." + String.valueOf(precision) + "f ";
		StringBuilder row = new StringBuilder();
		StringBuilder stringRows = new StringBuilder();
		Formatter formatter = new Formatter(row);
		for (int i = 0; i < this.getRowsCount(); i++) {
			row.append("[");
			for (int j = 0; j < this.getColsCount(); j++) {
				formatter.format(format, this.get(i, j));
			}
			// Deleting the last blank.
			row.deleteCharAt(row.length() - 1);
			row.append("]");
			stringRows.append(row).append(String.format("%n"));
			row.delete(0, row.length());
		}

		// Deleting the last newline.
		stringRows.deleteCharAt(stringRows.length() - 1);
		formatter.close();
		return stringRows.toString();
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

		if (!(obj instanceof IMatrix))
			return false;

		IMatrix other = (IMatrix) obj;

		if ((this.getColsCount() != other.getColsCount())
				|| this.getRowsCount() != other.getRowsCount()) {
			return false;
		}

		for (int i = 0, rows = this.getRowsCount(); i < rows; i++) {
			for (int j = 0, cols = this.getColsCount(); j < cols; j++) {
				if (Math.abs(Math.abs(this.get(i, j))
						- Math.abs(other.get(i, j))) > 1E-7) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public int hashCode() {
		return this.toArray().hashCode();
	}

}
