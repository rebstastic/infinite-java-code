package hr.fer.zemris.linearna;

/**
 * This class represents a live vector view of original matrix.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class VectorMatrixView extends AbstractVector {

	/**
	 * The original matrix.
	 */
	private IMatrix original;

	/**
	 * Matrix dimension.
	 */
	private int dimension;

	/**
	 * {@code true} if matrix view is row matrix, {@code false} otherwise.
	 */
	private boolean rowMatrix;

	/**
	 * Constructor.
	 * 
	 * @param original
	 *            The original matrix.
	 */
	public VectorMatrixView(IMatrix original) {
		this.original = original;
		if (original.getRowsCount() == 1) {
			this.rowMatrix = true;
			this.dimension = original.getColsCount();
		} else if (original.getColsCount() == 1) {
			this.rowMatrix = false;
			this.dimension = original.getRowsCount();
		} else {
			throw new IllegalArgumentException(
					"Matrix can not be viewed as vector.");
		}
	}

	@Override
	public double get(int index) {
		if (rowMatrix && (index < 0 || index > (original.getColsCount() - 1))) {
			throw new IllegalArgumentException(
					"Invalid index. Allowed values for index: [0, "
							+ (original.getColsCount() - 1) + "].");
		}
		if (!rowMatrix && (index < 0 || index > (original.getRowsCount() - 1))) {
			throw new IllegalArgumentException(
					"Invalid index. Allowed values for index: [0, "
							+ (original.getRowsCount() - 1) + "].");
		}
		if (rowMatrix) {
			return original.get(0, index);
		} else {
			return original.get(index, 0);
		}
	}

	@Override
	public IVector set(int index, double value) {
		if (rowMatrix && (index < 0 || index > (original.getColsCount() - 1))) {
			throw new IllegalArgumentException(
					"Invalid index. Allowed values for index: [0, "
							+ (original.getColsCount() - 1) + "].");
		}
		if (!rowMatrix && (index < 0 || index > (original.getRowsCount() - 1))) {
			throw new IllegalArgumentException(
					"Invalid index. Allowed values for index: [0, "
							+ (original.getRowsCount() - 1) + "].");
		}
		if (rowMatrix) {
			original.set(0, index, value);
		} else {
			original.set(index, 0, value);
		}
		return this;
	}

	@Override
	public int getDimension() {
		return this.dimension;
	}

	@Override
	public IVector copy() {
		return new VectorMatrixView(original.copy());
	}

	@Override
	public IVector newInstance(int dimension) {
		if (rowMatrix) {
			return new VectorMatrixView(LinAlgDefaults.defaultMatrix(1,
					dimension));
		} else {
			return new VectorMatrixView(LinAlgDefaults.defaultMatrix(dimension,
					1));
		}
	}

}
