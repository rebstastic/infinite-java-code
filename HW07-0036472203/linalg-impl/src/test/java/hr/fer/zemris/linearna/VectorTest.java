package hr.fer.zemris.linearna;

import static org.junit.Assert.*;

import org.junit.Test;

public class VectorTest {

	@Test
	public void constructor_AllGood() {
		double[] el = new double[] { 5, 6.58, 8.74 };
		IVector v = new Vector(false, false, el);
		el[2] = 5;
		assertEquals(Math.abs(v.get(2) - 8.74) < 1E-6, true);
	}

	@Test(expected = UnmodifiableObjectException.class)
	public void vector_ReadOnly() {
		IVector v = new Vector(true, true, new double[5]);
		v.set(0, 5);
	}

	@Test
	public void parseSimple_AllGood() {
		IVector v1 = Vector.parseSimple("0 8 4 1 7");
		IVector v2 = new Vector(0, 8, 4, 1, 7);
		assertEquals(v1.equals(v2), true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseString_Illegal() {
		Vector.parseSimple("  5    8   p");
	}

	@Test
	public void get_AllGood() {
		IVector v1 = Vector.parseSimple("0 8 4.358 1 7");
		assertEquals(Math.abs(v1.get(2) - 4.358) < 1E-6, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void get_InvalidIndex() {
		IVector v1 = new Vector(5, 8, 9.5870);
		v1.get(3);
	}

	@Test
	public void set_AllGood() {
		IVector v1 = new Vector(1, 2, 3.48);
		v1.set(2, 3.144);
		assertEquals(Math.abs(v1.get(2) - 3.144) < 1E-6, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void set_InvalidIndex() {
		IVector v1 = new Vector(5, 8, 9.5870);
		v1.set(3, 258);
	}

	@Test
	public void copy_AllGood() {
		IVector v1 = Vector.parseSimple("5.58 -4.878 5.58 -98");
		assertEquals(v1.equals(v1.copy()), true);
	}

	@Test
	public void copy_DifferentArrays_ChangesOnlyInOne() {
		IVector v1 = Vector.parseSimple("-5 -4 -3 -7");
		IVector v2 = v1.copy();
		v2.set(2, -50);
		assertEquals(Math.abs(v1.get(2) + 3) < 1E-6, true);
		assertEquals(Math.abs(v2.get(2) + 50) < 1E-6, true);
	}

	@Test
	public void newInstance_AllGood() {
		IVector v1 = Vector.parseSimple("0 0 0 0 0 0");
		IVector v2 = v1.newInstance(6);
		assertEquals(v1.equals(v2), true);
	}

	@Test
	public void copyPartWithPartSmallerThanOriginalTest() {
		IVector v1 = Vector.parseSimple("5 8 7 1.147");
		assertEquals(v1.copyPart(2).equals(Vector.parseSimple("5 8")), true);
	}

	@Test
	public void copyPartWithPartBiggerThanOriginalTest() {
		IVector v1 = Vector.parseSimple("5 8 7 1.147");
		assertEquals(
				v1.copyPart(7).equals(Vector.parseSimple("5 8 7 1.147 0 0 0")),
				true);
	}

}
