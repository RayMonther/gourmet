package com.cakegourmet.security;

import java.math.BigInteger;
import java.security.MessageDigest;

public class SecurePassword {

    public static String getSHA512(String input){

		String toReturn = null;
		try {
		    MessageDigest digest = MessageDigest.getInstance("SHA-512");
		    digest.reset();
		    digest.update(input.getBytes("utf8"));
		    toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		return toReturn;
    }
    
    public static void main(String[] argv) {

		String inputValue = "this is an example";
	
		// With the java libraries
		String sha512 = getSHA512( inputValue );
	
		System.out.println("The SHA-512 of \"" + inputValue + "\" is:");
		System.out.println(sha512);
		System.out.println();

    }
}