package org.onesy.ActiveThreads;

import org.onesy.InstructionFlows.InstructionFlowTypeMapper;
import org.onesy.MsgProcessor.InProcessFrame;
import org.onesy.MsgProcessor.MsgAsile;
import org.onesy.MsgProcessor.MsgBean;
import org.onesy.MsgProcessor.ProcessWindow;
import org.onesy.Orders.OrderBase;
import org.onesy.Switcher.ProcessClassSwitcher;
import org.onesy.Util.SPSDebugHelper;

public class FastPaxosWorker implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(;;){
			MsgBean receivedMsg = MsgAsile.getReceivedBean();
			OrderBase orderObj = null;
			String feedBack = null;
			/*
			 * TODO 这里需要插入步骤，如果可以在窗口中查找到正在进行的事件，则直接接着进行处理
			 * 否则，创建事件，并进行处理，处理完毕后更新事件状态，放入窗口
			 */
			InProcessFrame frame = null;
			if((frame = ProcessWindow.getInProcessFrame(receivedMsg)) != null){
				// 刷新帧的信息
				ProcessWindow.FlushFrameInfo(frame, receivedMsg);
				// 获得流程类名称
//				System.err.println(frame.ThisMsgBean);
				String IName = InstructionFlowTypeMapper.getInstructionNameByCode(frame.TrasanctionCode);
				// 获得流程对象
				orderObj = ProcessClassSwitcher.getInstructionByName(IName);
				feedBack = orderObj.ProcessMsg(frame,receivedMsg);
			}
			if(frame == null){
				SPSDebugHelper.Speaker("事务已经被处理过", 3);
			}
			
			if(feedBack != null){
				SPSDebugHelper.Speaker(feedBack, 1);
			}
		}
	}

}
