package org.onesy.Util;

public class SPSDebugHelper {
	
	public static void IsNull(Object object){
		System.err.println("object = " + object);
		if(object == null) {
			System.err.println("快速判断结果为空");
		}
	}
	
	public static void Speaker(String msg, int level){
		if(level == 1){
			System.out.println(msg);
		}else if (level == 2) {
			System.err.println(msg);
		}
	}

}
