package hr.fer.zemris.java.tecaj.hw07.crypto;

import static org.junit.Assert.*;

import org.junit.Test;

public class CryptoTest {

	@Test
	public void hexobyte_OddLength() {
		String hexString = "30b11";
		assertArrayEquals(new byte[] { (byte) 3, (byte) 11, (byte) 17 },
				Crypto.hexToByte(hexString));
	}
	
	@Test
	public void hexobyte_EvenLength() {
		String hexString = "0012a5";
		assertArrayEquals(new byte[] { (byte) 0, (byte) 18, (byte) 165 },
				Crypto.hexToByte(hexString));
	}

}
