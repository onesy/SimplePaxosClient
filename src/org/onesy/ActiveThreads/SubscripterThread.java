package org.onesy.ActiveThreads;

import org.onesy.Communication.Subscripter;
import org.onesy.ConfigureProcess.CfgCenter;

import redis.clients.jedis.Jedis;

public class SubscripterThread implements Runnable {

	public static final Jedis JEDIS = new Jedis(CfgCenter.selfbean.host, CfgCenter.selfbean.port, CfgCenter.selfbean.timeout);
	
	public static final Subscripter SUBSCRIPTER = new Subscripter();
	@Override
	public void run() {
		// TODO Auto-generated method stub
		/*
		 * 进行监听，进行sub操作
		 */
		JEDIS.psubscribe(SUBSCRIPTER, CfgCenter.selfbean.subchannel);
	}

}
