package org.onesy.ActiveThreads;

public class LoggerThread implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(;;){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
