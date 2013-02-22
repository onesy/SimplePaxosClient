package org.onesy.ActiveThreads;

import org.onesy.Communication.Publisher;
import org.onesy.MsgProcessor.MsgAsile;
import org.onesy.MsgProcessor.MsgBean;
import org.onesy.MsgProcessor.MsgBuildFactory;

public class PublisherThread implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		for(;;){
			MsgBean msgBean = MsgAsile.getSendBean();
			if(msgBean == null){
				System.err.println("msg null");
				System.exit(0);
			}
			boolean pubInfo = new Publisher().PubMsg(MsgBean.getCfgBean(msgBean.sign), MsgBuildFactory.MsgBuilder(msgBean));
		}

	}

}
