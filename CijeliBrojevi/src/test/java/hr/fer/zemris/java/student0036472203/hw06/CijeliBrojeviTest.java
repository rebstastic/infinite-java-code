package hr.fer.zemris.java.student0036472203.hw06;

import org.junit.Test;
import org.junit.Assert;

public class CijeliBrojeviTest {

	@Test
    public void jeNeparan_PredanNeparan_True() {
        Assert.assertTrue(new CijeliBrojevi().jeNeparan(3));
    }
	
	@Test
    public void jeNeparan_PredanParan_False() {
        Assert.assertFalse(new CijeliBrojevi().jeNeparan(2));
    }
	
	@Test
    public void jeParan_PredanParan_True() {
        Assert.assertTrue(new CijeliBrojevi().jeParan(4));
    }
	
	@Test
    public void jeParan_PredanNeparan_False() {
        Assert.assertFalse(new CijeliBrojevi().jeParan(3));
    }
	
	@Test
    public void jeProst_PredanProst_True() {
        Assert.assertTrue(new CijeliBrojevi().jeProst(7));
    }
	
	@Test
    public void jeProst_PredanDijeljiv_True() {
        Assert.assertFalse(new CijeliBrojevi().jeProst(24));
    }
}