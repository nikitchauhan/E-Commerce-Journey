package com.onlineSeller.component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Security {

	private  static SecretKeySpec secretkey;
	
	private static byte[] key;
	
	
	public static void setKey(String myKey)
	{
		MessageDigest sha= null;
		try
		{
			key=myKey.getBytes("UTF-8");
			sha=MessageDigest.getInstance("SHA-1");
			key=sha.digest();
			key=Arrays.copyOf(key, 16);
			secretkey = new SecretKeySpec(key,"AES");
			
			
			
		}catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();		}
	}
	
	
	
	
	
	public static String decrypt(String strToDecrypt,String secret)
	{
		try {
			
			setKey(secret);
			Cipher cipher= Cipher.getInstance("AES/ECB/PJCD5PADDING");
			cipher.init(Cipher.DECRYPT_MODE,secretkey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
			
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
