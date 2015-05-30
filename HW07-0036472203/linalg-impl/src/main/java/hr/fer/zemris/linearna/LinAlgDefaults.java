package hr.fer.zemris.linearna;

/**
 * This class represents a factory which produces default vectors and matrices.
 * By default, created instances are modifiable.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class LinAlgDefaults {

	/**
	 * Factory method for producing default vectors.
	 * 
	 * @param dimension
	 *            Vector dimension.
	 * @return Vector instance.
	 */
	public static IVector defaultVector(int dimension) {
		return new Vector(new double[dimension]);
	}

	/**
	 * Factory methods for producing default matrices.
	 * 
	 * @param rows
	 *            Number of matrix rows.
	 * @param cols
	 *            Number of matrix columns.
	 * @return Matrix instance.
	 */
	public static IMatrix defaultMatrix(int rows, int cols) {
		return new Matrix(rows, cols);
	}
}
