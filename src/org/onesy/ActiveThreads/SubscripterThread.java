package org.onesy.ActiveThreads;

import org.onesy.Communication.Subscripter;
import org.onesy.ConfigureProcess.CfgCenter;
import redis.clients.jedis.Jedis;

public class SubscripterThread implements Runnable {

	/**
	 * DEBUG SEG
	 */
	
	/**
	 * DEBUG SEG
	 */
	public static Jedis JEDIS = null ; 
	
	public static final Subscripter SUBSCRIPTER = new Subscripter();
	
	public SubscripterThread(){
		SubscripterThread.JEDIS = new Jedis(CfgCenter.selfbean.host, CfgCenter.selfbean.port, CfgCenter.selfbean.timeout);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		/*
		 * 进行监听，进行sub操作
		 */
		JEDIS.psubscribe(SUBSCRIPTER, CfgCenter.selfbean.subchannel);
	}

}
