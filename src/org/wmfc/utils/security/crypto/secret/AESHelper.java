package org.wmfc.utils.security.crypto.secret;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class AESHelper {
	public String decrypt(String valor, String chave, String vetor) throws Exception 
	{
		byte[] v = Hex.decodeHex(valor.toCharArray());
		byte[] c = Hex.decodeHex(chave.toCharArray());
		byte[] iv = Hex.decodeHex(vetor.toCharArray());
		SecretKeySpec k = new SecretKeySpec(c, "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		Cipher dcipher = Cipher.getInstance("AES/CBC/NoPadding");
		dcipher.init(Cipher.DECRYPT_MODE, k, paramSpec);
		return new String(dcipher.doFinal(v)).replaceAll("\0", "");
	}
	
	public String[] encrypt(String value) throws Exception 
	{
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom sran = new SecureRandom();

			kgen.init(128, sran);
			
			SecretKey skey = kgen.generateKey();
			byte[] raw = skey.getEncoded();
			
			String key = asHex(raw);

			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");

			IvParameterSpec ivSpec = createCtrIvForAES(1, sran);

			cipher.init(Cipher.ENCRYPT_MODE, skey, ivSpec);

			byte[] encrypted = cipher.doFinal(padWithZeros(value.getBytes()));
			
			String vector = asHex(cipher.getIV());
			
			String encryptedValue = asHex(encrypted);
			
			return new String[] {encryptedValue, key, vector};

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			
			throw new Exception("Encrypt error!", e);
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			
			throw new Exception("Encrypt error!", e);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			
			throw new Exception("Encrypt error!", e);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			
			throw new Exception("Encrypt error!", e);
		} catch (BadPaddingException e) {
			e.printStackTrace();
			
			throw new Exception("Encrypt error!", e);
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			
			throw new Exception("Encrypt error!", e);
		}
	}
	
	public static String asHex (byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;
		
		for (i = 0; i < buf.length; i++) {
			 if (((int) buf[i] & 0xff) < 0x10)
				 strbuf.append("0");
			
			 strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}
		
		return strbuf.toString();
	}
	
	public static IvParameterSpec createCtrIvForAES(int messageNumber, SecureRandom random) {
		byte[] ivBytes = new byte[16];
		
		random.nextBytes(ivBytes);
		
		ivBytes[0] = (byte) (messageNumber >> 24);
		ivBytes[1] = (byte) (messageNumber >> 16);
		ivBytes[2] = (byte) (messageNumber >> 8);
		ivBytes[3] = (byte) (messageNumber >> 0);
		
		for (int i = 0; i != 7; i++) {
			ivBytes[8 + i] = 0;
		}
		
		ivBytes[15] = 1;
		
		return new IvParameterSpec(ivBytes);
	}

	private static byte[] padWithZeros(byte[] input) {
	
		int rest = input.length % 16;
		
		if (rest > 0) {
			byte[] result = new byte[input.length + (16 - rest)];
			System.arraycopy(input, 0, result, 0, input.length);
			return result;
		}
		
		return input;
	}
}
