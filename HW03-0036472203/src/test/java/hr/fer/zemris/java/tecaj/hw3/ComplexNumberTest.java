package hr.fer.zemris.java.tecaj.hw3;

import hr.fer.zemris.java.tecaj.hw3.ComplexNumber;

import org.junit.Assert;
import org.junit.Test;

public class ComplexNumberTest {

	@Test
	public void getReal_expectedReal() {
		double real = 5.777;
		ComplexNumber num = new ComplexNumber(real, -2);
		Assert.assertEquals(real, num.getReal(), 1e-7);
	}

	@Test
	public void getImaginary_expectedImaginary() {
		double imaginary = 1.5;
		ComplexNumber num = new ComplexNumber(12, imaginary);
		Assert.assertEquals(imaginary, num.getImaginary(), 1e-7);
	}

	@Test
	public void getMagnitude_expectedMagnitude() {
		double real = 23.1;
		double imaginary = -1;
		ComplexNumber num = new ComplexNumber(real, imaginary);

		double magnitude = Math.sqrt(real * real + imaginary * imaginary);

		Assert.assertEquals(magnitude, num.getMagnitude(), 1e-7);
	}

	@Test
	public void getAngle_expectedAngleBetweenMinusPIAndPI() {
		ComplexNumber num = new ComplexNumber(15, -12.1);

		Assert.assertTrue(Math.abs(num.getAngle()) <= Math.PI);
	}

	@Test
	public void fromReal_ExpectedImaginaryPartZero() {
		Assert.assertTrue(ComplexNumber.fromReal(2).getImaginary() == 0);
	}

	@Test
	public void fromImaginary_ExpectedRealPartZero() {
		Assert.assertTrue(ComplexNumber.fromImaginary(2).getReal() == 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void fromMagnitudeAndAngle_NegativeMagnitude_ExceptionThrown() {
		ComplexNumber.fromMagnitudeAndAngle(-1, 45);
	}

	@Test(expected = IllegalArgumentException.class)
	public void div_DivisionByZero_ExceptionThrown() {
		new ComplexNumber(4, 9).div(new ComplexNumber(0, 0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void power_NegativePower_ExceptionThrown() {
		new ComplexNumber(2, -4).power(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void root_ZeroRoot_ExceptionThrown() {
		new ComplexNumber(7, -5).root(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void root_NegativeRoot_ExceptionThrown() {
		new ComplexNumber(7, -5).root(-9);
	}

	@Test
	public void parse_BothPositive() {
		double real = 12;
		double imaginary = 2.1;
		String string = real + " + " + imaginary + "i";
		ComplexNumber num = ComplexNumber.parse(string);
		Assert.assertTrue(Math.abs(num.getReal() - real) < 1e-7
				&& Math.abs(num.getImaginary() - imaginary) < 1e-7);
	}

	@Test
	public void parse_BothNegative() {
		double real = -12;
		double imaginary = -2.1;
		String string = real + " + " + imaginary + "i";
		ComplexNumber num = ComplexNumber.parse(string);
		Assert.assertTrue(Math.abs(num.getReal() - real) < 1e-7
				&& Math.abs(num.getImaginary() - imaginary) < 1e-7);
	}

	@Test
	public void parse_PositiveNegative() {
		double real = 12;
		double imaginary = -2.1;
		String string = real + " + " + imaginary + "i";
		ComplexNumber num = ComplexNumber.parse(string);
		Assert.assertTrue(Math.abs(num.getReal() - real) < 1e-7
				&& Math.abs(num.getImaginary() - imaginary) < 1e-7);
	}

	@Test
	public void parse_NegativePositive() {
		double real = -12;
		double imaginary = 2.1;
		String string = real + " + " + imaginary + "i";
		ComplexNumber num = ComplexNumber.parse(string);
		Assert.assertTrue(Math.abs(num.getReal() - real) < 1e-7
				&& Math.abs(num.getImaginary() - imaginary) < 1e-7);
	}

	@Test
	public void parse_RealOnly() {
		ComplexNumber num = ComplexNumber.parse("15");
		Assert.assertTrue(Math.abs(num.getReal() - 15) < 1e-7
				&& num.getImaginary() == 0);
	}

	@Test
	public void parse_ImaginaryOnly() {
		ComplexNumber num = ComplexNumber.parse("7i");
		Assert.assertTrue(Math.abs(num.getImaginary() - 7) < 1e-7
				&& num.getReal() == 0);
	}

	@Test (expected = IllegalArgumentException.class)
	public void parse_InvalidImaginaryUnit_ExceptionThrown() {
		ComplexNumber.parse("2+4k");
	}

	@Test (expected = IllegalArgumentException.class)
	public void parse_InvalidExpresion_ExceptionThrown() {
		ComplexNumber.parse("2d-g5+s5i");
	}
}
