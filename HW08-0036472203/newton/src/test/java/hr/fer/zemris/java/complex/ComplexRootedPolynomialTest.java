package hr.fer.zemris.java.complex;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class ComplexRootedPolynomialTest {

	private static ComplexRootedPolynomial crp = null;
	
	@BeforeClass
	public static void before() {
		Complex[] roots = new Complex[4];
		roots[0] = Complex.ONE;
		roots[1] = Complex.ONE_NEG;
		roots[2] = Complex.IM;
		roots[3] = Complex.IM_NEG;
		crp = new ComplexRootedPolynomial(roots);
	}
	
	@Test
	public void applyTest() {
		Complex z = new Complex(2,0);
		Complex result = new Complex(15,0);
		assertEquals(result, crp.apply(z));		
	}
	
	@Test
	public void toComplexPolynomialTest() {
		Complex[] factors = new Complex[5];
		factors[0] = Complex.ONE;
		factors[1] = Complex.ZERO;
		factors[2] = Complex.ZERO;
		factors[3] = Complex.ZERO;
		factors[4] = Complex.ONE_NEG;
		ComplexPolynomial result = new ComplexPolynomial(factors);
		assertEquals(result, crp.toComplexPolynomial());
	}
	
	@Test
	public void indexOfClosestRootForTest_RootFound() {
		int result = 1;
		Complex z = new Complex(-0.9995,0);
		double treshold = 0.001;
		assertEquals(result, crp.indexOfClosestRootFor(z, treshold));
	}
	
	@Test
	public void indexOfClosestRootForTest_RootNotFound() {
		int result = -1;
		Complex z = new Complex(2,0);
		double treshold = 0.001;
		assertEquals(result, crp.indexOfClosestRootFor(z, treshold));
	}
}
