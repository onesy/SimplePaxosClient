package org.onesy.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CommonAlgorithm {
	
	public static String Md5Al(Object object){
		return new String(calculateMD5(object));
	}
	
	private static <T> byte[] calculateMD5(T src){
		String SRCStr = src.toString();
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md5.update(SRCStr.getBytes());
		byte[] result = md5.digest();
		return result;
	}
	
	public static Iterator HashMapToIterator(Map hashMap){
		return hashMap.entrySet().iterator();
	}

}
