package hr.fer.zemris.linearna;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AbstractMatrixTest {

	IMatrix matrix;

	@Before
	public void before() {
		matrix = new Matrix(3, 3, new double[][] { { 1, 2, 3 }, { 4, 5, 6 },
				{ 7, 8, 9 } }, true);
	}

	@Test
	public void nTranspose() {
		IMatrix other = matrix.nTranspose(true);
		IMatrix expected = new Matrix(3, 3, new double[][] { { 1, 4, 7 },
				{ 2, 5, 8 }, { 3, 6, 9 } }, true);
		assertEquals(expected, other);
	}

	@Test(expected = IllegalArgumentException.class)
	public void add_OtherIsNull() {
		matrix.add(null);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void add_IncompatibleRows() {
		IMatrix other = new Matrix(4, 3, new double[][] { { 1, 1, 1 },
				{ 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, true);
		matrix.add(other);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void add_IncompatibleColumns() {
		IMatrix other = new Matrix(3, 4, new double[][] { { 1, 1, 1, 1 },
				{ 1, 1, 1, 1 }, { 1, 1, 1, 1 } }, true);
		matrix.add(other);
	}

	@Test
	public void add_AllGood() {
		IMatrix other = new Matrix(3, 3, new double[][] { { 1, 1, 1 },
				{ 1, 1, 1 }, { 1, 1, 1 } }, true);
		IMatrix expected = new Matrix(3, 3, new double[][] { { 2, 3, 4 },
				{ 5, 6, 7 }, { 8, 9, 10 } }, true);
		assertEquals(expected, matrix.add(other));
	}

	@Test
	public void nAdd_AllGood() {
		IMatrix other = new Matrix(3, 3, new double[][] { { 1, 1, 1 },
				{ 1, 1, 1 }, { 1, 1, 1 } }, true);
		IMatrix expected = new Matrix(3, 3, new double[][] { { 2, 3, 4 },
				{ 5, 6, 7 }, { 8, 9, 10 } }, true);
		assertEquals(expected, matrix.nAdd(other));
	}

	@Test(expected = IllegalArgumentException.class)
	public void sub_OtherIsNull() {
		matrix.sub(null);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void sub_IncompatibleRows() {
		IMatrix other = new Matrix(4, 3, new double[][] { { 1, 1, 1 },
				{ 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, true);
		matrix.sub(other);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void sub_IncompatibleColumns() {
		IMatrix other = new Matrix(3, 4, new double[][] { { 1, 1, 1, 1 },
				{ 1, 1, 1, 1 }, { 1, 1, 1, 1 } }, true);
		matrix.sub(other);
	}

	@Test
	public void sub_AllGood() {
		IMatrix other = new Matrix(3, 3, new double[][] { { 1, 1, 1 },
				{ 1, 1, 1 }, { 1, 1, 1 } }, true);
		IMatrix expected = new Matrix(3, 3, new double[][] { { 0, 1, 2 },
				{ 3, 4, 5 }, { 6, 7, 8 } }, true);
		assertEquals(expected, matrix.sub(other));
	}

	@Test
	public void nSub_AllGood() {
		IMatrix other = new Matrix(3, 3, new double[][] { { 1, 1, 1 },
				{ 1, 1, 1 }, { 1, 1, 1 } }, true);
		IMatrix expected = new Matrix(3, 3, new double[][] { { 0, 1, 2 },
				{ 3, 4, 5 }, { 6, 7, 8 } }, true);
		assertEquals(expected, matrix.nSub(other));
	}

	@Test(expected = IllegalArgumentException.class)
	public void nMultiply_OtherIsNull() {
		matrix.nMultiply(null);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void nMultiply_Incompatible() {
		IMatrix other = new Matrix(2, 2, new double[][] { { 1, 1 }, { 1, 1 } },
				true);
		matrix.nMultiply(other);
	}

	@Test
	public void nMultiply_AllGood() {
		IMatrix other = new Matrix(3, 2, new double[][] { { 1, 1 }, { 1, 1 },
				{ 1, 1 } }, true);
		IMatrix result = new Matrix(3, 2, new double[][] { { 6, 6 },
				{ 15, 15 }, { 24, 24 } }, true);
		assertEquals(result, matrix.nMultiply(other));
	}

	@Test(expected = IncompatibleOperandException.class)
	public void determinant_NotDefined() {
		IMatrix matrix = new Matrix(3, 2, new double[][] { { 6, 6 },
				{ 15, 15 }, { 24, 24 } }, true);
		matrix.determinant();
	}

	@Test
	public void determinant_DimensionOne() {
		IMatrix matrix = new Matrix(1, 1, new double[][] { { 24 } }, true);
		assertTrue(Math.abs(24 - matrix.determinant()) < 1E-7);
	}

	@Test
	public void determinant_GreaterDimension() {
		assertTrue(Math.abs(matrix.determinant() - 0) < 1E-7);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void nInvert_Singular() {
		matrix.nInvert();
	}

	@Test(expected = IncompatibleOperandException.class)
	public void nInvert_NotSquare() {
		IMatrix matrix = new Matrix(3, 2, new double[][] { { 6, 6 },
				{ 15, 15 }, { 24, 24 } }, true);
		matrix.nInvert();
	}

	@Test
	public void nInvert_AllGood() {
		IMatrix matrix = new Matrix(2, 2,
				new double[][] { { 1, 3 }, { 5, 7 } }, true);
		IMatrix result = new Matrix(2, 2, new double[][] {
				{ -7.0 / 8, 3.0 / 8 }, { 5.0 / 8, -1.0 / 8 } }, true);
		assertEquals(result, matrix.nInvert());
	}

	@Test
	public void toArray_AllGood() {
		assertArrayEquals(new double[][] { { 1, 2, 3 }, { 4, 5, 6 },
				{ 7, 8, 9 } }, matrix.toArray());
	}

	@Test(expected = IncompatibleOperandException.class)
	public void toVector_CanNot() {
		matrix.toVector(true);
	}

	@Test
	public void toVector_AllGood() {
		IMatrix matrix = new Matrix(1, 2, new double[][] { { 1, 3 } }, true);
		assertEquals(new VectorMatrixView(matrix), matrix.toVector(true));
	}

	@Test
	public void scalarMultiply() {
		IMatrix result = new Matrix(3, 3, new double[][] { { 2, 4, 6 },
				{ 8, 10, 12 }, { 14, 16, 18 } }, true);
		assertEquals(result, matrix.scalarMultiply(2));
	}

	@Test
	public void nScalarMultiply() {
		IMatrix result = new Matrix(3, 3, new double[][] { { 2, 4, 6 },
				{ 8, 10, 12 }, { 14, 16, 18 } }, true);
		assertEquals(result, matrix.nScalarMultiply(2));
	}

	@Test(expected = IncompatibleOperandException.class)
	public void makeIdentity_Incompatible() {
		IMatrix matrix = new Matrix(3, 2, new double[][] { { 6, 6 },
				{ 15, 15 }, { 24, 24 } }, true);
		matrix.makeIdentity();
	}

	@Test
	public void makeIdentity_AllGood() {
		IMatrix result = new Matrix(3, 3, new double[][] { { 1, 0, 0 },
				{ 0, 1, 0 }, { 0, 0, 1 } }, true);
		assertEquals(result, matrix.makeIdentity());
	}

	@Test(expected = IllegalArgumentException.class)
	public void subMatrix_InvalidRow() {
		matrix.subMatrix(-1, 2, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void subMatrix_InvalidColumn() {
		matrix.subMatrix(1, -2, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void subMatrix_InvalidRow2() {
		matrix.subMatrix(5, 2, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void subMatrix_InvalidColumn2() {
		matrix.subMatrix(1, 10, true);
	}

	@Test
	public void subMatrix_AllGood() {
		assertEquals(new MatrixSubMatrixView(matrix, 1, 1),
				matrix.subMatrix(1, 1, true));
	}

}
