package com.example.david.passman.encryption;

import com.example.david.passman.data.UserData;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import javax.crypto.Cipher;

public class AES {
	static String IV = "ABCDEFGIJKLMNOPQRSTUVWXYZ012345";

	public static void main(String [] args) {
		/*try {
			byte[] cipher = encrypt(plaintext);

			for (byte aCipher : cipher)
				System.out.print(Integer.valueOf(aCipher) + " ");

			String decrypted = decrypt(cipher);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	public static byte[] encrypt(String plainText) throws Exception {
		// set encryption key
		UserData userData = UserData.getInstance();
		String encryptionKey = userData.settings.get_enctyptionKey(); //userData.settings.get_password();

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
		return cipher.doFinal(plainText.getBytes("UTF-8"));
	}

	public static String decrypt(byte[] cipherText) throws Exception {
		// set encryption key
		UserData userData = UserData.getInstance();
		String encryptionKey = userData.settings.get_enctyptionKey(); //userData.settings.get_password();

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
		cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
		return new String(cipher.doFinal(cipherText),"UTF-8");
	}
}