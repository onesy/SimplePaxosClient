package org.onesy.Util;

import org.onesy.ActiveThreads.FastPaxosWorker;
import org.onesy.ActiveThreads.PublisherThread;
import org.onesy.ActiveThreads.SubscripterThread;
import org.onesy.MsgProcessor.ProcessWindow;

public class ThreadOp implements Runnable {

	public static Thread Self = null;

	public static Thread SendD = null;

	public static Thread ReceiveD = null;

	public static Thread ProcessWindowD = null;

	public static Thread FastPaxosWorkD = null;

	/**
	 * 启动线程管理工具
	 */
	public ThreadOp() {
		Thread[] threads = InitThreadD();
		SendD = threads[0];
		ReceiveD = threads[1];
		ProcessWindowD = threads[2];
		FastPaxosWorkD = threads[3];
	}

	public void StartUp() {
		Thread Self = new Thread(this);
		SendD.start();
		ReceiveD.start();
		ProcessWindowD.start();
		FastPaxosWorkD.start();
		Self.start();
	}
	
	public static Thread[] InitThreadD() {

		Runnable pulbisherD, subscripterD, ProcessWindowD, fastPaxosWorkerD;
		pulbisherD = new PublisherThread();
		subscripterD = new SubscripterThread();
		ProcessWindowD = new ProcessWindow();
		fastPaxosWorkerD =  new FastPaxosWorker();
		Thread[] threadDs = new Thread[4];
		// 开启出通道
		threadDs[0] = ThreadOp.SetAsDaemon(pulbisherD, "pulbisherD",
				false);
		SPSDebugHelper.Speaker("开启出通道完毕", 1);
		// 开启监听器
		threadDs[1] = ThreadOp.SetAsDaemon(subscripterD, "subscripterD",
				false);
		SPSDebugHelper.Speaker("开启监听器完毕", 1);
		// 开启ProcessWindow
		threadDs[2] = ThreadOp.SetAsDaemon(ProcessWindowD, "ProcessWindowD",
				false);
		SPSDebugHelper.Speaker("开启ProcessWindow完毕", 1);
		// 开启FastPaxosWorker
		threadDs[3] = ThreadOp.SetAsDaemon(fastPaxosWorkerD,
				"fastPaxosWorkerD", false);
		SPSDebugHelper.Speaker("开启FastPaxosWorker完毕", 1);
		
		return threadDs;
	}

	public static Thread SetAsDaemon(Runnable runnable, String threadName,
			boolean daemon) {
		Thread threadD = new Thread(runnable);
		if (threadName != null) {
			threadD.setName(threadName);
		}
		if (daemon) {
			threadD.setDaemon(daemon);
		}
		return threadD;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (;;) {
			// 检查各个线程的工作状态\
			// TODO
		}
	}
}
