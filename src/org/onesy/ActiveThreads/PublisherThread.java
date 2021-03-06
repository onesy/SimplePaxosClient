package org.onesy.ActiveThreads;

import java.io.IOException;
import org.onesy.Communication.NIOSocketClient;
import org.onesy.MsgProcessor.MsgAsile;
import org.onesy.MsgProcessor.MsgBean;
import org.onesy.Util.SPSDebugHelper;

public class PublisherThread implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		NIOSocketClient nioSocketClient = new NIOSocketClient();
		for(;;){
			MsgBean msgBean = MsgAsile.getSendBean();
			if(msgBean == null){
//				System.err.println("msg null");
				SPSDebugHelper.Speaker("Msg Content is null, this process will be drop", 3);
				continue;
			}
			try {
				nioSocketClient.pub(msgBean);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
