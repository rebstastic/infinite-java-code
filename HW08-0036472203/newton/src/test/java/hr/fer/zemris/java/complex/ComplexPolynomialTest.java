package hr.fer.zemris.java.complex;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class ComplexPolynomialTest {

	private static ComplexPolynomial cp = null;
	
	@BeforeClass
	public static void before() {
		Complex[] factors = new Complex[5];
		factors[0] = new Complex(7, 2);
		factors[1] = new Complex(2,0);
		factors[2] = Complex.ZERO;
		factors[3] = new Complex(5,1);
		factors[4] = Complex.ONE;
		cp = new ComplexPolynomial(factors);
	}
	
	@Test
	public void orderTest() {
		assertEquals(4, cp.order());
	}

	@Test
	public void multiplyTest() {
		Complex[] factors = new Complex[2];
		factors[0] = new Complex(1,1);
		factors[1] = new Complex(2,0);
		ComplexPolynomial other = new ComplexPolynomial(factors);
		Complex[] factorsResult = new Complex[6];
		factorsResult[0] = new Complex(5,9);
		factorsResult[1] = new Complex(16,6);
		factorsResult[2] = new Complex(4,0);
		factorsResult[3] = new Complex(4,6);
		factorsResult[4] = new Complex(11,3);
		factorsResult[5] = new Complex(2,0);
		ComplexPolynomial result = new ComplexPolynomial(factorsResult);
		assertEquals(result, cp.multiply(other));
	}
	
	@Test
	public void deriveTest() {
		Complex[] factors = new Complex[4];
		factors[0] = new Complex(28,8);
		factors[1] = new Complex(6,0);
		factors[2] = Complex.ZERO;
		factors[3] = new Complex(5,1);
		ComplexPolynomial result = new ComplexPolynomial(factors);
		assertEquals(result, cp.derive());
	}
	
	@Test
	public void applyTest() {
		Complex z = new Complex(1, 2);
		Complex result = new Complex(-19, -175);
		assertEquals(result, cp.apply(z));
	}
	
}
