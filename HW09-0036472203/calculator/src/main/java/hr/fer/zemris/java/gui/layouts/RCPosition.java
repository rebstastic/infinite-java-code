package hr.fer.zemris.java.gui.layouts;

/**
 * Represents the matrix position with row index and column index.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class RCPosition {

	/**
	 * Row index.
	 */
	private final int row;

	/**
	 * Column index.
	 */
	private final int column;

	/**
	 * Constructor that initializes row index and colum index to given values.
	 * 
	 * @param row
	 *            Row index.
	 * @param column
	 *            Column index.
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Returns row index.
	 * 
	 * @return Row index.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns column index.
	 * 
	 * @return Column index.
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Parses the given string to {@code RCPosition} object if possible, throws
	 * {@code IllegalArgumentException} otherwise.
	 * 
	 * @param constr
	 *            Layout positions to parse.
	 * @return {@code RCPosition} object represented by given string.
	 */
	public static RCPosition parse(String constr) {
		String[] constraints = constr.split(",");
		if (constraints.length != 2) {
			throw new IllegalArgumentException(
					"Given string cannot be parsed to layout position: "
							+ constr);
		}
		int row = 0;
		int column = 0;
		try {
			row = Integer.parseInt(constraints[0].trim());
			column = Integer.parseInt(constraints[1].trim());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"Given string cannot be parsed to layout position: "
							+ constr);
		}

		return new RCPosition(row, column);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof RCPosition)) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		RCPosition other = (RCPosition) obj;
		return (this.row == other.row) && (this.column == other.column);
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(row).hashCode()
				+ Integer.valueOf(column).hashCode();
	}

}
