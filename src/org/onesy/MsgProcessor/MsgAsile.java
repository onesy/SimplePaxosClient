package org.onesy.MsgProcessor;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MsgAsile {
	
	public static ConcurrentLinkedQueue<MsgBean> receivedAisle = new ConcurrentLinkedQueue<MsgBean>();
	
	public static ConcurrentLinkedQueue<MsgBean> sendAisle = new ConcurrentLinkedQueue<MsgBean>();
	
	
	public static MsgBean getReceivedBean(){
		return receivedAisle.poll();
	}
	
	public static MsgBean getSendBean(){
		return sendAisle.poll();
	}
	
	public static void addReceivedBean(MsgBean msgBean){
		MsgAsile.receivedAisle.add(msgBean);
	}
	
	public static void addSendBean(MsgBean msgBean){
		MsgAsile.sendAisle.add(msgBean);
	}
}
