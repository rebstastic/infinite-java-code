package hr.fer.zemris.java.complex;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComplexTest {

	@Test
	public void moduleTest() {
		Complex c = new Complex(4, 3);
		assertTrue("Module should be 5.", Math.abs(c.module()-5) < 1e-6);
	}
	
	@Test
	public void multiplyTest() {
		Complex c1 = new Complex(4, 3);
		Complex c2 = new Complex(2, 1);
		Complex result = new Complex(5, 10);
		assertEquals(result, c1.multiply(c2));
	}
	
	@Test(expected= IllegalArgumentException.class)
	public void divideTest_DivideByZero_ExceptionThrown() {
		(new Complex(4, 3)).divide(Complex.ZERO);
	}
	
	@Test
	public void divideTest() {
		Complex c1 = new Complex(4, 3);
		Complex c2 = new Complex(2, 1);
		Complex result = new Complex(11/(double)5, 2/(double)5);
		assertEquals(result, c1.divide(c2));
	}
	
	@Test
	public void addTest() {
		Complex c1 = new Complex(4, 3);
		Complex c2 = new Complex(2, 1);
		Complex result = new Complex(6, 4);
		assertEquals(result, c1.add(c2));
	}
	
	@Test
	public void subTest() {
		Complex c1 = new Complex(4, 3);
		Complex c2 = new Complex(2, 1);
		Complex result = new Complex(2, 2);
		assertEquals(result, c1.sub(c2));
	}
	
	@Test
	public void negateTest() {
		Complex c = new Complex(4, 3);
		Complex result = new Complex(-4, -3);
		assertEquals(result, c.negate());
	}
	
	@Test
	public void parseTest_OnlyPositiveReal() {
		Complex c = Complex.parse("2");
		assertEquals(new Complex(2, 0),  c);
	}
	
	@Test
	public void parseTest_OnlyNegativeReal() {
		Complex c = Complex.parse("-12");
		assertEquals(new Complex(-12, 0),  c);
	}
	
	@Test
	public void parseTest_OnlyPositiveImaginary() {
		Complex c = Complex.parse("2i");
		assertEquals(new Complex(0, 2),  c);
	}
	
	@Test
	public void parseTest_OnlyNegativeImaginary() {
		Complex c = Complex.parse("-12i");
		assertEquals(new Complex(0, -12),  c);
	}
	
	@Test
	public void parseTest_PositiveRealAndImaginary() {
		Complex c = Complex.parse("2+3i");
		assertEquals(new Complex(2, 3),  c);
	}
	
	@Test
	public void parseTest_NegativeRealAndImaginary() {
		Complex c = Complex.parse("-12-15i");
		assertEquals(new Complex(-12, -15),  c);
	}
	
	@Test
	public void parseTest_PositiveRealNegativeImaginary() {
		Complex c = Complex.parse("2-15i");
		assertEquals(new Complex(2, -15),  c);
	}
	
	@Test
	public void parseTest_NegativeRealPositiveImaginary() {
		Complex c = Complex.parse("-12+3i");
		assertEquals(new Complex(-12, 3),  c);
	}
	
	@Test
	public void parseTest_1i() {
		Complex c = Complex.parse("i");
		assertEquals(new Complex(0, 1),  c);
	}
	
	@Test
	public void parseTest_Spaces() {
		Complex c = Complex.parse("-  12	+ i	");
		assertEquals(new Complex(-12, 1),  c);
	}
	
	@Test(expected=NumberFormatException.class)
	public void parseTest_RealUnparsable_ExceptionThrown() {
		Complex.parse("l3fk + i");
	}
	
	@Test(expected=NumberFormatException.class)
	public void parseTest_ImaginaryUnparsable_ExceptionThrown() {
		Complex.parse("13 + ijkj8374");
	}

}
