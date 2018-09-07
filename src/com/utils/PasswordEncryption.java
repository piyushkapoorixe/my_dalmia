package com.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class PasswordEncryption {
	public static String  encryptPasword(String password) 
	{
		String hashtext=null;
		try{
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(password.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			hashtext = bigInt.toString(16);
			while(hashtext.length() < 32 ){
				hashtext = "0"+hashtext;
			}	
			return hashtext;
		}catch(Exception e)
		{

		}
		return hashtext;
	}
	public static String  encryptBase64(String id) 
	{
		byte[] encodedBytes = Base64.getEncoder().encode(id.getBytes());
		return new String(encodedBytes);
	}
	public static String  dycriptBase64(byte[] id) 
	{
		byte[] decodedBytes = Base64.getDecoder().decode(id);
		return new String(decodedBytes);
	}
	
	


	
}
