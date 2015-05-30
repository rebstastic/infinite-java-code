package hr.fer.zemris.linearna;

/**
 * Represents a live transposed matrix view of original matrix. If original
 * matrix changes, the change is visible in correspondent matrix view as well.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class MatrixTransposeView extends AbstractMatrix {

	/**
	 * The original matrix.
	 */
	private IMatrix original;

	/**
	 * Constructor.
	 * 
	 * @param original
	 *            The original matrix.
	 */
	public MatrixTransposeView(IMatrix original) {
		this.original = original;
	}

	@Override
	public int getRowsCount() {
		return original.getColsCount();
	}

	@Override
	public int getColsCount() {
		return original.getRowsCount();
	}

	@Override
	public double get(int rowIndex, int columnIndex) {
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
		return original.get(columnIndex, rowIndex);
	}

	@Override
	public IMatrix set(int rowIndex, int columnIndex, double value) {
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
		return original.set(columnIndex, rowIndex, value);
	}

	@Override
	public IMatrix copy() {
		return new MatrixTransposeView(original.copy());
	}

	@Override
	public IMatrix newInstance(int rowNumber, int columnNumber) {
		return new MatrixTransposeView(original.newInstance(rowNumber,
				columnNumber));
	}

	@Override
	public double[][] toArray() {
		double[][] elements = new double[original.getColsCount()][original
				.getRowsCount()];
		for (int i = original.getRowsCount() - 1; i >= 0; i--) {
			for (int j = original.getColsCount() - 1; j >= 0; j--) {
				elements[j][i] = original.get(i, j);
			}
		}
		return elements;
	}

}
