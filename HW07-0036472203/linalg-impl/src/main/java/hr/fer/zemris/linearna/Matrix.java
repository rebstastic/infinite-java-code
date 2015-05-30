package hr.fer.zemris.linearna;

/**
 * This class represents a concrete matrix. Common matrix operations are
 * provided.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class Matrix extends AbstractMatrix {

	/**
	 * Matrix elements.
	 */
	private double[][] elements;

	/**
	 * Number of matrix rows.
	 */
	private int rows;

	/**
	 * Number of matrix columns.
	 */
	private int cols;

	private boolean immutable = false;

	/**
	 * Constructor. If immutable, matrix can not be modified.
	 * 
	 * @param rows
	 *            Number of matrix rows.
	 * @param cols
	 *            Number of matrix columns.
	 * @param elements
	 *            Matrix elements.
	 * @param immutable
	 *            {@code true} if matrix is immutable, {@code false} otherwise.
	 */
	public Matrix(int rows, int cols, double[][] elements, boolean immutable) {
		this(rows, cols);
		this.immutable = immutable;
		if (immutable) {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					this.elements[i][j] = elements[i][j];
				}
			}
		} else {
			this.elements = elements;
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param rows
	 *            Number of matrix rows.
	 * @param cols
	 *            Number of matrix columns.
	 */
	public Matrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.elements = new double[rows][cols];
	}

	@Override
	public int getRowsCount() {
		return rows;
	}

	@Override
	public int getColsCount() {
		return cols;
	}

	@Override
	public double get(int rowIndex, int columnIndex) {
		if (rowIndex < 0 || rowIndex > getRowsCount() - 1) {
			throw new IllegalArgumentException(
					"Invalid row index. Allowed values for row index: [0, "
							+ (getRowsCount() - 1) + "].");
		}
		if (columnIndex < 0 || columnIndex > getColsCount() - 1) {
			throw new IllegalArgumentException(
					"Invalid column index. Allowed values for colum index: [0, "
							+ (getColsCount() - 1) + "].");
		}
		return this.elements[rowIndex][columnIndex];
	}

	@Override
	public IMatrix set(int rowIndex, int columnIndex, double value) {
		if (immutable) {
			throw new UnmodifiableObjectException("Matrix is immutable.");
		}
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
		this.elements[rowIndex][columnIndex] = value;
		return this;
	}

	@Override
	public IMatrix copy() {
		return new Matrix(rows, cols, this.elements, false);
	}

	@Override
	public IMatrix newInstance(int rowNumber, int columnNumber) {
		return new Matrix(rowNumber, columnNumber);
	}

	/**
	 * Parses the string representation of a matrix and creates a matrix from
	 * given string.
	 * 
	 * @param string
	 *            String representation of a matrix.
	 * @return Matrix from string representation.
	 */
	public static IMatrix parseSimple(String string) {
		String[] stringRows = string.trim().split("\\|");
		int rowNumber = stringRows.length;
		int columnNumber = stringRows[0].trim().split("\\s+").length;
		double[][] elements = new double[rowNumber][columnNumber];
		for (int i = 0; i < rowNumber; i++) {
			String row[] = stringRows[i].trim().split("\\s+");
			for (int j = 0; j < columnNumber; j++) {
				try {
					elements[i][j] = Double.parseDouble(row[j]);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException(
							"Can not parse string to matrix: " + string);
				}
			}
		}
		return new Matrix(rowNumber, columnNumber, elements, false);
	}

}
