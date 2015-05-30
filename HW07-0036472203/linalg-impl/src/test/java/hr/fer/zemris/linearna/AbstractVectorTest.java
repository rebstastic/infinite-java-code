package hr.fer.zemris.linearna;

import static org.junit.Assert.*;

import org.junit.Test;

public class AbstractVectorTest {

	@Test
	public void add_AllGood() {
		IVector v1 = Vector.parseSimple("4.15 8.75 2.25");
		assertEquals(
				v1.add(Vector.parseSimple("5.85  1.25   7.75")).equals(
						Vector.parseSimple("10 10 10")), true);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void add_Incompatible() {
		IVector v1 = Vector.parseSimple("1.859");
		v1.add(Vector.parseSimple("5 8"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void add_OtherIsNull() {
		Vector.parseSimple("5 2 8").add(null);
	}

	@Test
	public void nAdd_AllGood() {
		IVector v1 = Vector.parseSimple("4.15 8.75 2.25");
		assertEquals(
				v1.nAdd(Vector.parseSimple("5.85  1.25   7.75")).equals(
						Vector.parseSimple("10 10 10")), true);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void nAdd_Incompatible() {
		IVector v1 = Vector.parseSimple("1.859");
		v1.nAdd(Vector.parseSimple("5 8"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void nAdd_OtherIsNull() {
		Vector.parseSimple("5 2 8").nAdd(null);
	}

	@Test
	public void sub_AllGood() {
		IVector v1 = Vector.parseSimple("4.15 8.75 2.25");
		assertEquals(
				v1.sub(Vector.parseSimple("0.15  0.75   0.25")).equals(
						Vector.parseSimple("4 8 2")), true);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void sub_Incompatible() {
		IVector v1 = Vector.parseSimple("1.859");
		v1.add(Vector.parseSimple("5 8"));
	}

	@Test
	public void nSubAll_Good() {
		IVector v1 = Vector.parseSimple("4.15 8.75 2.25");
		assertEquals(
				v1.nSub(Vector.parseSimple("0.15  0.75   0.25")).equals(
						Vector.parseSimple("4 8 2")), true);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void nSub_Incompatible() {
		IVector v1 = Vector.parseSimple("1.859");
		v1.nSub(Vector.parseSimple("5 8"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void nSub_OtherIsNull() {
		Vector.parseSimple("5 2 8").nSub(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void sub_OtherIsNull() {
		Vector.parseSimple("5 2 8").sub(null);
	}

	@Test
	public void scalarMultiply_AllGood() {
		IVector v1 = Vector.parseSimple("4.15 8.75 2.25");
		assertEquals(
				v1.scalarMultiply(2).equals(Vector.parseSimple("8.3 17.5 4.5")),
				true);
	}

	@Test
	public void nScalarMultiply_AllGood() {
		IVector v1 = Vector.parseSimple("4.15 8.75 2.25");
		assertEquals(
				v1.nScalarMultiply(2)
						.equals(Vector.parseSimple("8.3 17.5 4.5")), true);
	}

	@Test
	public void norm_AllGood() {
		IVector v1 = Vector.parseSimple("3 4");
		assertEquals("Norma vektora s elementima 3, 4 je 5.",
				Math.abs(v1.norm() - 5) < 1E-6, true);
	}

	@Test
	public void normalize_AllGood() {
		IVector v1 = Vector.parseSimple("1 1 1 1");
		assertEquals(
				Vector.parseSimple("0.5 0.5 0.5 0.5").equals(v1.normalize()),
				true);
	}

	@Test
	public void nNormalize_AllGood() {
		IVector v1 = Vector.parseSimple("1 1 1 1");
		assertEquals(
				Vector.parseSimple("0.5 0.5 0.5 0.5").equals(v1.nNormalize()),
				true);
	}

	@Test
	public void cosineVector_AllGood() {
		IVector v1 = Vector.parseSimple("1 0 0");
		IVector v2 = Vector.parseSimple("0 0 1");
		assertEquals(Math.abs(v1.cosine(v2)) < 1E-6, true);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void cosine_Incompatible() {
		IVector v1 = Vector.parseSimple("1  0");
		IVector v2 = Vector.parseSimple("0 0 1");
		v1.cosine(v2);
	}

	@Test
	public void scalarProduct_AllGood() {
		IVector v1 = Vector.parseSimple("5 2 4");
		IVector v2 = Vector.parseSimple("2 5 2.5");
		assertEquals(Math.abs(v1.scalarProduct(v2) - 30) < 1E-6, true);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void scalarProduct_Incompatible() {
		IVector v1 = Vector.parseSimple("5 ");
		IVector v2 = Vector.parseSimple("2 5 2.5");
		v1.scalarProduct(v2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void scalarProduct_OtherIsNull() {
		IVector v1 = Vector.parseSimple("5 ");
		v1.scalarProduct(null);
	}

	@Test
	public void nVectorProduct_AllGood() {
		IVector v1 = Vector.parseSimple("5 2 4");
		IVector v2 = Vector.parseSimple("3.5 8.75 9.16");
		assertEquals(
				v1.nVectorProduct(v2).equals(
						Vector.parseSimple("-16.68  -31.8   36.75")), true);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void nVectorProduct_Incompatible() {
		IVector v1 = Vector.parseSimple("5 ");
		IVector v2 = Vector.parseSimple("2 5 2.5");
		v1.nVectorProduct(v2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nVectorProduct_OtherIsNull() {
		IVector v1 = Vector.parseSimple("5 ");
		v1.nVectorProduct(null);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void nFromHomogeneus_OneDimension_Incompatible() {
		Vector.parseSimple("2").nFromHomogeneus();
	}

	@Test
	public void nFromHomogeneus_AllGood() {
		assertEquals(
				Vector.parseSimple("2 4 6 8 2").nFromHomogeneus()
						.equals(Vector.parseSimple("1 2 3 4")), true);
	}

	@Test
	public void toArray_AllGood() {
		IVector v1 = Vector.parseSimple("5 6.8 7.258");
		double[] elements = v1.toArray();
		elements[2] = 10;
		assertEquals(Math.abs(elements[2] - 10) < 1E-6, true);
		assertEquals(Math.abs(v1.get(2) - 7.258) < 1E-6, true);
	}
}
