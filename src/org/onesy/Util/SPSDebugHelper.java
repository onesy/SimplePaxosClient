package org.onesy.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SPSDebugHelper {
	//-------------------------------------------
	public static final boolean DEBUG = true;
	//-------------------------------------------
	public static final String MSG_SPLITE="->";
	
	public static void IsNull(Object object){
		System.err.println("object = " + object);
		if(object == null) {
			System.err.println("快速判断结果为空");
		}
	}
	
	public static void Speaker(String msg, int level){
		
		
		if(level == 1){
			System.out.println(SpkFmt(msg));
		} else if (level == 2) {
			System.err.println(msg);
		} else if (level == 3) {
			System.err.println("WARNING:\r" + SpkFmt(msg));
		}
	}
	
	public static String SpkFmt(String msg){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		return "TIME:" + sdf.format(new Date()) + "|EVENT" + MSG_SPLITE +msg;
	}

}
