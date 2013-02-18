package org.onesy.ActiveThreads;

import org.onesy.ConfigureProcess.CfgCenter;
import org.onesy.MsgProcessor.MsgAsile;
import org.onesy.MsgProcessor.MsgBean;
import org.onesy.Orders.OrderBase;
import org.onesy.Switcher.ProcessClassSwitcher;

public class FastPaxosWorker implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(;;){
			MsgBean receivedMsg = MsgAsile.getReceivedBean();
			OrderBase orderObj = ProcessClassSwitcher.getProcessObject(receivedMsg);
			String feedBack = orderObj.ProcessMsg(receivedMsg.Msg);
			if(feedBack == null)
				continue;
			MsgBean sendbean = new MsgBean(CfgCenter.selfbean, receivedMsg.voteSerialNo + 1, orderObj.msgkind, feedBack);
			MsgAsile.addSendBean(sendbean);
		}
	}

}
