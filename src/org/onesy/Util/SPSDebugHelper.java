package org.onesy.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SPSDebugHelper {
	
	public static final String MSG_SPLITE="->";
	
	public static void IsNull(Object object){
		System.err.println("object = " + object);
		if(object == null) {
			System.err.println("快速判断结果为空");
		}
	}
	
	public static void Speaker(String msg, int level){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(level == 1){
			System.out.println("EVENT:" + sdf.format(new Date()) + MSG_SPLITE +msg);
		}else if (level == 2) {
			System.err.println(msg);
		}
	}

}
