package org.wmfc.utils.security.crypto.secret;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
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
}
