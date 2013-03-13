package org.onesy.Util;

import org.onesy.ActiveThreads.FastPaxosWorker;
import org.onesy.ActiveThreads.PublisherThread;
import org.onesy.ActiveThreads.SubscripterThread;
import org.onesy.MsgProcessor.ProcessWindow;

import com.sun.xml.internal.bind.v2.model.core.ID;

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
		SPSDebugHelper.Speaker("ReceiveD 线程 started", 1);
		ReceiveD.start();
		SPSDebugHelper.Speaker("ReceiveD 线程 started", 1);
		ProcessWindowD.start();
		SPSDebugHelper.Speaker("ProcessWindowD 线程 started", 1);
		FastPaxosWorkD.start();
		SPSDebugHelper.Speaker("FastPaxosWorkD 线程 started", 1);
		Self.start();
		SPSDebugHelper.Speaker("AdminD线程 started", 1);
	}

	public static Thread[] InitThreadD() {

		Runnable pulbisherD, subscripterD, ProcessWindowD, fastPaxosWorkerD;
		pulbisherD = new PublisherThread();
		subscripterD = new SubscripterThread();
		ProcessWindowD = new ProcessWindow();
		fastPaxosWorkerD = new FastPaxosWorker();
		Thread[] threadDs = new Thread[4];
		// 初始化发送线程完毕
		threadDs[0] = ThreadOp.SetAsDaemon(pulbisherD, "SendD", false);
		SPSDebugHelper.Speaker("初始化SendD线程完毕", 1);
		// 初始化接收线程完毕
		threadDs[1] = ThreadOp.SetAsDaemon(subscripterD, "ReceiveD", false);
		SPSDebugHelper.Speaker("初始化ReceiveD线程完毕", 1);
		// 初始化ProcessWindow线程完毕
		threadDs[2] = ThreadOp.SetAsDaemon(ProcessWindowD, "ProcessWindowD",
				false);
		SPSDebugHelper.Speaker("初始化ProcessWindow线程完毕", 1);
		// 初始化FastPaxosWorker线程完毕
		threadDs[3] = ThreadOp.SetAsDaemon(fastPaxosWorkerD,
				"fastPaxosWorkerD", false);
		SPSDebugHelper.Speaker("初始化FastPaxosWorker线程完毕", 1);
		Self = SetAsDaemon(Self, "AdminD", false);
		SPSDebugHelper.Speaker("初始化AdminD线程完毕", 1);

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
		Runnable pulbisherD, subscripterD, processWindowD, fastPaxosWorkeD;
		// TODO Auto-generated method stub
		for (;;) {
			// 检查各个线程的工作状态
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO
			// 检查发送线程
			if (!SendD.isAlive() || SendD.isInterrupted()) {
				Thread tmpThread = SendD;
				SendD = null;
				tmpThread.interrupt();
				SPSDebugHelper.Speaker("pulbisherD线程已经死亡", 3);
				SPSDebugHelper.Speaker("pulbisherD线程重启中......", 2);
				pulbisherD = new PublisherThread();
				SendD = ThreadOp.SetAsDaemon(pulbisherD, "pulbisherD", false);
				SendD.start();
				SPSDebugHelper.Speaker("pulbisherD线程重启完毕", 2);
			}
			// 检查接收线程
			if (!ReceiveD.isAlive() || ReceiveD.isInterrupted()) {
				Thread tmpThread = ReceiveD;
				ReceiveD = null;
				tmpThread.interrupt();
				SPSDebugHelper.Speaker("ReceiveD线程已经死亡", 3);
				SPSDebugHelper.Speaker("ReceiveD线程重启中......", 2);
				subscripterD = new SubscripterThread();
				ReceiveD = ThreadOp
						.SetAsDaemon(subscripterD, "ReceiveD", false);
				ReceiveD.start();
				SPSDebugHelper.Speaker("ReceiveD线程重启完毕", 2);
			}
			// 检查ProcessWindow线程
			if (!ProcessWindowD.isAlive() || ProcessWindowD.isInterrupted()) {
				Thread tmpThread = ProcessWindowD;
				ProcessWindowD = null;
				tmpThread.interrupt();
				SPSDebugHelper.Speaker("ProcessWindowD线程已经死亡", 3);
				SPSDebugHelper.Speaker("ProcessWindowD线程重启中......", 2);
				processWindowD = new ProcessWindow();
				ProcessWindowD = ThreadOp.SetAsDaemon(processWindowD,
						"ProcessWindowD", false);
				ProcessWindowD.start();
				SPSDebugHelper.Speaker("ProcessWindowD线程重启完毕", 2);
			}
			// 检查FastPaxosWorkerD线程
			if (!FastPaxosWorkD.isAlive() || FastPaxosWorkD.isInterrupted()) {
				Thread tmpThread = FastPaxosWorkD;
				FastPaxosWorkD = null;
				tmpThread.interrupt();
				SPSDebugHelper.Speaker("FastPaxosWorkD线程已经死亡", 3);
				SPSDebugHelper.Speaker("FastPaxosWorkD线程重启中......", 2);
				fastPaxosWorkeD = new FastPaxosWorker();
				ProcessWindowD = ThreadOp.SetAsDaemon(fastPaxosWorkeD,
						"FastPaxosWorkD", false);
				ProcessWindowD.start();
				SPSDebugHelper.Speaker("FastPaxosWorkD线程重启完毕", 2);
			}
		}
	}
}
