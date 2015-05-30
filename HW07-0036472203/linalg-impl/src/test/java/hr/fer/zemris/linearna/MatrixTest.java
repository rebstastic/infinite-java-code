package hr.fer.zemris.linearna;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MatrixTest {

	IMatrix matrix;

	@Before
	public void before() {
		matrix = new Matrix(3, 3, new double[][] { { 1, 2, 3 }, { 4, 5, 6 },
				{ 7, 8, 9 } }, true);
	}

	@Test
	public void simpleConstructor() {
		IMatrix matrix = new Matrix(2, 2);
		assertArrayEquals(new double[][] { { 0, 0 }, { 0, 0 } },
				matrix.toArray());
	}

	@Test
	public void biggerConstructor_NotImmutable() {
		IMatrix matrix = new Matrix(3, 3, new double[][] { { 1, 2, 3 },
				{ 4, 5, 6 }, { 7, 8, 9 } }, false);
		matrix.set(1, 1, 20);
		assertTrue(Math.abs(20 - matrix.get(1, 1)) < 1e-7);
	}

	@Test(expected = UnmodifiableObjectException.class)
	public void biggerConstructor_Immutable() {
		matrix.set(1, 1, 20);
	}

	@Test(expected = IllegalArgumentException.class)
	public void get_InvalidRowSmaller() {
		matrix.get(-1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void get_InvalidRowBigger() {
		matrix.get(20, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void set_InvalidRowSmaller() {
		IMatrix matrix = new Matrix(3, 3, new double[][] { { 1, 2, 3 },
				{ 4, 5, 6 }, { 7, 8, 9 } }, false);
		matrix.set(-1, 1, 30);
	}

	@Test(expected = IllegalArgumentException.class)
	public void set_InvalidRowBigger() {
		IMatrix matrix = new Matrix(3, 3, new double[][] { { 1, 2, 3 },
				{ 4, 5, 6 }, { 7, 8, 9 } }, false);
		matrix.set(20, 1, 30);
	}

	@Test(expected = IllegalArgumentException.class)
	public void get_InvalidColumnSmaller() {
		matrix.get(1, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void get_InvalidCollumnBigger() {
		matrix.get(1, 20);
	}

	@Test(expected = IllegalArgumentException.class)
	public void set_InvalidColumnSmaller() {
		IMatrix matrix = new Matrix(3, 3, new double[][] { { 1, 2, 3 },
				{ 4, 5, 6 }, { 7, 8, 9 } }, false);
		matrix.set(1, -1, 30);
	}

	@Test(expected = IllegalArgumentException.class)
	public void set_InvalidColumnBigger() {
		IMatrix matrix = new Matrix(3, 3, new double[][] { { 1, 2, 3 },
				{ 4, 5, 6 }, { 7, 8, 9 } }, false);
		matrix.set(1, 10, 30);
	}

	@Test
	public void copy() {
		assertEquals(matrix, matrix.copy());
	}

	@Test
	public void newInstance() {
		assertEquals(Matrix.parseSimple("0"), matrix.newInstance(1, 1));
	}

	@Test
	public void parseSimple() {
		assertEquals(matrix, Matrix.parseSimple("1 2 3 | 4 5 6 | 7 8 9"));
	}
}
