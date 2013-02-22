package org.onesy.MsgProcessor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.onesy.ConfigureProcess.CfgCenter;

public class MsgAsile {
	
	public static BlockingQueue<MsgBean> receivedAisle = new LinkedBlockingQueue<MsgBean>(CfgCenter.PUBLISHER_QUEUE_LEN);
	
	public static BlockingQueue<MsgBean> sendAisle = new LinkedBlockingQueue<MsgBean>(CfgCenter.SUBSCRIPTER_QUEUE_LEN);
	
	
	public static MsgBean getReceivedBean(){
		try {
			return receivedAisle.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static MsgBean getSendBean(){
		try {
			return sendAisle.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static void addReceivedBean(MsgBean msgBean){
		MsgAsile.receivedAisle.add(msgBean);
	}
	
	public static void addSendBean(MsgBean msgBean){
		MsgAsile.sendAisle.add(msgBean);
	}
}
