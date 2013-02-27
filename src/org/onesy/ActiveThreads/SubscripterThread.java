package org.onesy.ActiveThreads;

import java.io.IOException;

import org.onesy.Communication.NIOSocketServer;

public class SubscripterThread implements Runnable {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		/*
		 * 进行监听，进行sub操作
		 */
		NIOSocketServer nioSocketServer = new NIOSocketServer();
		try {
			nioSocketServer.listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
