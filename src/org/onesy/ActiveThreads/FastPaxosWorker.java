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
			/*
			 * TODO 这里需要插入步骤，如果可以在窗口中查找到正在进行的事件，则直接接着进行处理
			 * 否则，创建事件，并进行处理，处理完毕后更新事件状态，放入窗口
			 */
			OrderBase orderObj = ProcessClassSwitcher.getProcessObject(receivedMsg);
			String feedBack = orderObj.ProcessMsg(receivedMsg.Msg);
			if(feedBack == null)
				continue;
			MsgBean sendbean = new MsgBean(CfgCenter.selfbean, receivedMsg.VoteSerialNo + 1, orderObj.msgkind, feedBack);
			MsgAsile.addSendBean(sendbean);
		}
	}

}
