package hr.fer.zemris.linearna;

/**
 * This class represents a live matrix view of an original vector.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class MatrixVectorView extends AbstractMatrix {

	/**
	 * The original vector.
	 */
	private IVector original;

	/**
	 * {@code true} if this view is row matrix view - it has one column,
	 * {@code false} otherwise.
	 */
	private boolean asRowMatrix;

	/**
	 * Constructor.
	 * 
	 * @param original
	 *            The original vector.
	 * @param asRowMatrix
	 *            {@code true} if this view is row matrix view - it has one
	 *            column, {@code false} otherwise.
	 */
	public MatrixVectorView(IVector original, boolean asRowMatrix) {
		this.original = original;
		this.asRowMatrix = asRowMatrix;
	}

	@Override
	public int getRowsCount() {
		if (asRowMatrix) {
			return 1;
		} else {
			return original.getDimension();
		}
	}

	@Override
	public int getColsCount() {
		if (asRowMatrix) {
			return original.getDimension();
		} else {
			return 1;
		}
	}

	@Override
	public double get(int rowIndex, int columnIndex) {
		if (asRowMatrix
				&& (rowIndex != 0 || (columnIndex < 0 || columnIndex > original
						.getDimension() - 1))) {
			throw new IllegalArgumentException(
					"Row index must be 0. Allowed values for column index: [0, "
							+ (original.getDimension() - 1) + "].");
		}
		if (!this.asRowMatrix
				&& (columnIndex != 0 || (rowIndex < 0 || rowIndex > original
						.getDimension() - 1))) {
			throw new IllegalArgumentException(
					"Column index must be 0. Allowed values for row index: [0, "
							+ (original.getDimension() - 1) + "].");
		}
		if (asRowMatrix) {
			return original.get(columnIndex);
		} else {
			return original.get(rowIndex);
		}
	}

	@Override
	public IMatrix set(int rowIndex, int columnIndex, double value) {
		if (rowIndex + 1 > getRowsCount() || columnIndex + 1 > getColsCount()) {
			throw new IncompatibleOperandException("Index out of bound.");
		}
		if (asRowMatrix) {
			original.set(columnIndex, value);
		} else {
			original.set(rowIndex, value);
		}
		return this;
	}

	@Override
	public IMatrix copy() {
		return new MatrixVectorView(original, asRowMatrix);
	}

	@Override
	public IMatrix newInstance(int rowNumber, int columnNumber) {
		if (asRowMatrix) {
			return new MatrixVectorView(
					LinAlgDefaults.defaultVector(columnNumber), asRowMatrix);
		} else {
			return new MatrixVectorView(
					LinAlgDefaults.defaultVector(rowNumber), asRowMatrix);
		}
	}

}
