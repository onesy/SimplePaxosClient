package org.onesy.Communication;

import org.onesy.MsgProcessor.MsgAsile;
import org.onesy.MsgProcessor.MsgBean;

import redis.clients.jedis.JedisPubSub;
/**
 * @deprecated
 * @author onesy
 *
 */
public class Subscripter extends JedisPubSub {

	@Override
	public void onMessage(String Channel, String Msg) {
		// TODO Auto-generated method stub
		MsgAsile.addReceivedBean(MsgBean.getMsgBean(Msg));
	}

	@Override
	public void onPMessage(String pattern, String Channel, String msg ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPSubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPUnsubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnsubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

}
