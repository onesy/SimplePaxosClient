package org.onesy.Util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CommonAlgorithm {
	
	public static byte[] Md5Al(String srcstr){
		return calculateMD5(srcstr);
	}
	
	private static <T> byte[] calculateMD5(String src){
		/**
		String SRCStr = src.toString();
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			try {
				md5.update(SRCStr.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		**/
		//src to string
		String strsrc = src;
		
		MessageDigest md = null;
		byte[] b = null;
		try {
			md = MessageDigest.getInstance("MD5");
			b = strsrc.getBytes("UTF8");  
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
        byte[] hash = md.digest(b);   
		
//		byte[] result = md5.digest();
		return hash;
	}
	
	public static Iterator HashMapToIterator(Map hashMap){
		return hashMap.entrySet().iterator();
	}

}
