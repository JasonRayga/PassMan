package com.example.david.passman.encryption;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class RandomGenerator {
	private static RandomGenerator ourInstance = new RandomGenerator();

	public static RandomGenerator getInstance() {
		return ourInstance;
	}

	private RandomGenerator() {

	}

	private static char[] VALID_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456879".toCharArray();

	// cs = cryptographically secure
	public String get(int numChars) {
		SecureRandom srand = new SecureRandom();
		Random rand = new Random();
		char[] buff = new char[numChars];

		for (int i = 0; i < numChars; ++i) {
			// reseed random once you've used up all available entropy bits
			if ((i % 10) == 0) {
				rand.setSeed(srand.nextLong()); // 64 bits of random!
			}
			buff[i] = VALID_CHARACTERS[rand.nextInt(VALID_CHARACTERS.length)];
		}
		return new String(buff);
	}
}
