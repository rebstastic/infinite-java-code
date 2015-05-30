package hr.fer.zemris.linearna;

/**
 * Represents a live matrix view of original matrix. If original matrix changes,
 * the change is visible in correspondent matrix view as well.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class MatrixSubMatrixView extends AbstractMatrix {

	/**
	 * The original matrix.
	 */
	private IMatrix original;

	/**
	 * Row indexes of matrix view from original matrix.
	 */
	private int[] rowIndexes;

	/**
	 * Column indexes of matrix view from original matrix.
	 */
	private int[] columnIndexes;

	/**
	 * Constructor takes original matrix and row and column which live matrix
	 * view will not contain.
	 * 
	 * @param original
	 *            The original matrix.
	 * @param rowIndex
	 *            Row index to delete.
	 * @param columnIndex
	 *            Column index to delete.
	 */
	public MatrixSubMatrixView(IMatrix original, int rowIndex, int columnIndex) {
		this(original, liveIndexes(original, rowIndex, true), liveIndexes(
				original, columnIndex, false));
	}

	/**
	 * Creates an array of live indexes.
	 * 
	 * @param original
	 *            The original matrix.
	 * @param value
	 *            The row or column index to delete.
	 * @param isRow
	 *            {@code true} if row index was passed, {@code false} otherwise.
	 * @return
	 */
	private static int[] liveIndexes(IMatrix original, int value, boolean isRow) {
		if (isRow && (value < 0 || value > original.getRowsCount() - 1)) {
			throw new IllegalArgumentException(
					"Invalid row index. Allowed values for row index: [0, "
							+ (original.getRowsCount() - 1) + "].");
		}
		if (!isRow && (value < 0 || value > original.getColsCount() - 1)) {
			throw new IllegalArgumentException(
					"Invalid column index. Allowed values for colum index: [0, "
							+ (original.getColsCount() - 1) + "].");
		}

		int length = (isRow) ? original.getRowsCount() : original
				.getColsCount();

		int[] indexes = new int[length - 1];

		for (int i = 0, j = 0; i < length; i++) {
			if (i != value) {
				indexes[j++] = i;
			}
		}
		return indexes;
	}

	/**
	 * Constructor takes original matrix and arrays of rows and columns which
	 * live matrix view contains.
	 * 
	 * @param original
	 *            The original matrix.
	 * @param rowIndexes
	 *            Row indexes to save.
	 * @param columnIndexes
	 *            Column indexes to save.
	 */
	private MatrixSubMatrixView(IMatrix original, int[] rowIndexes,
			int[] columnIndexes) {
		this.original = original;
		this.rowIndexes = rowIndexes;
		this.columnIndexes = columnIndexes;
	}

	@Override
	public int getRowsCount() {
		return rowIndexes.length;
	}

	@Override
	public int getColsCount() {
		return columnIndexes.length;
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
		return original.get(rowIndexes[rowIndex], columnIndexes[columnIndex]);
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
		return original.set(rowIndexes[rowIndex], columnIndexes[columnIndex],
				value);
	}

	@Override
	public IMatrix copy() {
		return new MatrixSubMatrixView(original.copy(), rowIndexes,
				columnIndexes);
	}

	@Override
	public IMatrix newInstance(int rowNumber, int columnNumber) {
		return new MatrixSubMatrixView(original.newInstance(
				original.getRowsCount(), original.getColsCount()), rowNumber,
				columnNumber);
	}

	@Override
	public IMatrix subMatrix(int rowIndex, int columnIndex, boolean liveView) {
		if (liveView) {
			return new MatrixSubMatrixView(original, rowIndex, columnIndex);
		} else {
			return new MatrixSubMatrixView(original.copy(), rowIndex,
					columnIndex);
		}
	}
}
