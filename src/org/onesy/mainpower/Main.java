package org.onesy.mainpower;

import org.onesy.ConfigureProcess.CfgCenter;
import org.onesy.Util.SPSDebugHelper;
import org.onesy.Util.ThreadOp;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 初始化参数
		CfgCenter cfgCenter = CfgCenter.getInstance();
		SPSDebugHelper.Speaker("初始化参数完毕", 1);
		ThreadOp threadOp = new ThreadOp();
		threadOp.StartUp();
	}
}
