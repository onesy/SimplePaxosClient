package org.onesy.mainpower;

import org.onesy.ActiveThreads.FastPaxosWorker;
import org.onesy.ActiveThreads.PublisherThread;
import org.onesy.ActiveThreads.SubscripterThread;
import org.onesy.ConfigureProcess.CfgCenter;
import org.onesy.MsgProcessor.ProcessWindow;
import org.onesy.Util.SPSDebugHelper;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 初始化参数
		CfgCenter cfgCenter = CfgCenter.getInstance();
		SPSDebugHelper.Speaker("初始化参数完毕", 1);
		// 开启出通道
		PublisherThread PublisherThread = new PublisherThread();
		Thread pulbisherD = new Thread(PublisherThread);
		pulbisherD.start();
		SPSDebugHelper.Speaker("开启出通道完毕", 1);
		// 开启监听器
		SubscripterThread subscripterThread = new SubscripterThread();
		Thread subscripterD = new Thread(subscripterThread);
		subscripterD.start();
		SPSDebugHelper.Speaker("开启监听器完毕", 1);
		// 开启ProcessWindow
		ProcessWindow ProcessWindow = new ProcessWindow();
		Thread ProcessWindowD = new Thread(ProcessWindow);
		ProcessWindowD.start();
		SPSDebugHelper.Speaker("开启ProcessWindow完毕", 1);
		//  开启FastPaxosWorker
		FastPaxosWorker fastPaxosWorker = new FastPaxosWorker();
		Thread fastPaxosWorkerD = new Thread(fastPaxosWorker);
		fastPaxosWorkerD.start();
		SPSDebugHelper.Speaker("开启FastPaxosWorker完毕", 1);
	}

}
