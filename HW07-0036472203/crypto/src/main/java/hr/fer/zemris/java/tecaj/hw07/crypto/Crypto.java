package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class allows the user to encrypt/decrypt given file using the AES
 * crypto-algorithm and the 128-bit encryption key or calculate and check the
 * SHA-256 file digest.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class Crypto {

	/**
	 * The buffer size.
	 */
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Digest algorithm.
	 */
	private static final String DIGEST_ALGORITHM = "SHA-256";

	/**
	 * Algorithm used for crypting.
	 */
	private static final String CRYPT_ALGORITHM = "AES";

	/**
	 * Transformation.
	 */
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

	/**
	 * This method is called once the program is run.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {

		// Number of arguments must be at least two.
		if (args.length < 2) {
			System.err.println("Invalid number of arguments.");
			System.exit(1);
		}

		try (Scanner scanner = new Scanner(System.in)) {

			String action = args[0];
			if (action.equalsIgnoreCase("checksha")) {
				try {

					// Get key from the user.
					System.out.println("Please provide expected "
							+ DIGEST_ALGORITHM + " digest for " + args[1]
							+ ": ");
					System.out.print("> ");
					String keyText = scanner.nextLine();

					// Find file digest.
					try {
						fileDigest(args[1], keyText);
					} catch (IOException e) {
						System.err
								.println("IOException occured while opening file: "
										+ args[1]);
						System.exit(1);
					}

				} catch (NoSuchAlgorithmException e) {
					System.err.println("Algorithm not found: "
							+ DIGEST_ALGORITHM);
					System.exit(1);
				}
			} else {

				// Encrypt and decrypt actions use three arguments.
				if (args.length < 3) {
					System.err.println("Invalid number of arguments.");
					System.exit(1);
				}

				if (action.equalsIgnoreCase("encrypt")
						|| action.equalsIgnoreCase("decrypt")) {
					try {

						// Get password from the user.
						System.out
								.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
						System.out.print("> ");
						String password = scanner.nextLine();

						// Get initialization vector from the user.
						System.out
								.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
						System.out.print("> ");
						String iv = scanner.nextLine();

						// Perform crypting.
						try {
							crypt(args[1], args[2], password, iv,
									action.equalsIgnoreCase("encrypt") ? true
											: false);
						} catch (GeneralSecurityException e) {
							System.err
									.println("General security exception occured while crypting.");
						}

					} catch (IOException e) {
						System.err
								.println("IOException occured while opening files: "
										+ args[1] + " and " + args[2]);
						System.exit(1);
					}
				} else {
					System.err.println("Unsupported program call.");
					System.exit(1);
				}
			}
		}
	}

	/**
	 * File digest calculation.
	 * 
	 * @param path
	 *            The file to calculate the digest of.
	 * @param keyText
	 *            Key text.
	 * @throws NoSuchAlgorithmException
	 *             - if algorithm does not exists
	 * @throws IOException
	 *             - if IOException occurs.
	 */
	private static void fileDigest(String path, String keyText)
			throws NoSuchAlgorithmException, IOException {

		MessageDigest sha = MessageDigest.getInstance(DIGEST_ALGORITHM);

		try (InputStream reader = new BufferedInputStream(new FileInputStream(
				path), BUFFER_SIZE)) {

			byte[] in = new byte[BUFFER_SIZE];

			while (reader.available() > 0) {
				int size = reader.read(in);
				sha.update(in, 0, size);
			}

			byte[] original = hexToByte(keyText);
			byte[] generated = sha.digest();

			if (Arrays.equals(original, generated)) {
				System.out.println("Digesting completed. Digest of " + path
						+ " matches expected digest.");
			} else {
				System.out.println("Digesting completed. Digest of " + path
						+ " does not match the expected digest. Digest was: "
						+ byteToHex(generated));
			}
		}
	}

	/**
	 * File encryption or decryption.
	 * 
	 * @param pathFrom
	 *            The file to encrypt or decrypt.
	 * @param pathTo
	 *            The file to store encrypted or decrypted data.
	 * @param password
	 *            The password.
	 * @param iv
	 *            The initialization vector.
	 * @param isEncrypt
	 *            {@code true} if file needs to be encrypted, {@code false} if
	 *            file needs to be decrypted.
	 * @throws IOException
	 *             - if IOException occurs
	 * @throws GeneralSecurityException
	 *             - if GeneralSecurityException occurs
	 */
	private static void crypt(String pathFrom, String pathTo, String password,
			String iv, boolean isEncrypt) throws IOException,
			GeneralSecurityException {

		// Create cipher.
		SecretKeySpec keySpec = new SecretKeySpec(hexToByte(password),
				CRYPT_ALGORITHM);
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(hexToByte(iv));
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE,
				keySpec, paramSpec);

		try (InputStream reader = new BufferedInputStream(new FileInputStream(
				pathFrom), BUFFER_SIZE);
				OutputStream writer = new BufferedOutputStream(
						new FileOutputStream(pathTo), BUFFER_SIZE)) {

			byte[] in = new byte[BUFFER_SIZE];
			byte[] out = new byte[BUFFER_SIZE];

			while (reader.available() > 0) {
				int inputSize = reader.read(in);
				int outputSize = cipher.update(in, 0, inputSize, out);
				writer.write(out, 0, outputSize);
				writer.flush();
			}
			int outputSize = cipher.doFinal(out, 0);
			writer.write(out, 0, outputSize);
		}

		String action = isEncrypt ? "Encryption" : "Decryption";
		System.out.println(action + " completed. Generated file " + pathTo
				+ " based on file " + pathFrom + ".");
	}

	/**
	 * Returns appropriate {@code byte} array from hex-encoded {@code String}.
	 * Two sequential characters in given {@code String} argument represent one
	 * hexadecimal value.
	 * 
	 * @param keyText
	 *            Hex-encoded {@code String}.
	 * @return Appropriate {@code byte} array.
	 */
	public static byte[] hexToByte(String keyText) {

		// Add zero padding if length is odd.
		if (keyText.length() % 2 != 0) {
			keyText = "0" + keyText;
		}

		byte[] keyByte = new byte[keyText.length() / 2];

		for (int i = 0, len = keyByte.length; i < len; i++) {
			int hexValue = (Character.digit(keyText.charAt(2 * i), 16) << 4)
					+ Character.digit(keyText.charAt(2 * i + 1), 16);
			keyByte[i] = (byte) hexValue;
		}

		return keyByte;
	}

	/**
	 * Returns hexidecimal representation of a given {@code keyByte}.
	 * 
	 * @param keyByte
	 *            The key byte.
	 * @return Hexidecimal representation of given {@code keyByte}.
	 */
	private static String byteToHex(byte[] keyByte) {
		StringBuilder sb = new StringBuilder(keyByte.length * 2);
		for (byte b : keyByte)
			sb.append(String.format("%02x", b & 0xff));
		return sb.toString();
	}

}
