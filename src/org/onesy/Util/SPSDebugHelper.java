package org.onesy.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SPSDebugHelper {
	//-------------------------------------------
	public static final boolean DEBUG = true;
	//-------------------------------------------
	public static final String MSG_SPLITE="->";
	
	public static final String[] MSG_LEVEL = {"|MESSAGE","|ERROR","|WARNING"};
	
	public static void IsNull(Object object){
		System.err.println("object = " + object);
		if(object == null) {
			System.err.println("快速判断结果为空");
		}
	}
	
	public static void Speaker(String msg, int level){
		
		
		if(level == 1){
			System.out.println(SpkFmt(msg,1));
		} else if (level == 2) {
			System.err.println("ERROR:\r"+SpkFmt(msg,2));
		} else if (level == 3) {
			System.err.println("WARNING:\r" + SpkFmt(msg,3));
		}
	}
	
	public static String SpkFmt(String msg,int level){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		return "TIME:" + sdf.format(new Date()) + MSG_LEVEL[level - 1 ] + MSG_SPLITE +msg;
	}

}
