package org.onesy.Orders;

import org.onesy.MsgProcessor.InProcessFrame;
import org.onesy.MsgProcessor.MsgBean;
import org.onesy.MsgProcessor.ProcessWindow;

public class ConfirmOrder extends OrderBase {

	@Override
	public String ProcessMsg(InProcessFrame frame, MsgBean msgBean) {
		/*
		 * 确认信息收到的消息
		 */
		// TODO 形成状态机的条件
		/*
		 * 将处理流程和线程分离，并且放入缓冲区中，这个缓冲区必须能够快速找到未完成的流程
		 * 
		 * 第一步，根据本流程的需要提取frame中的需求的数据
		 * 
		 * 第二步，执行相应的操作
		 * 
		 * 第三步，更新frame
		 * 
		 * 第四步，返回消息
		 */
		
//		System.out.println("framsign = "+frame.FrameSign + "\n Msg:" + msgBean.Msg + " transaction = " + msgBean.TransactionSerialNo + " 已经得到确认");
//		SPSDebugHelper.Speaker("正在销毁frame对象", 1);
		ProcessWindow.FrameFinish(frame);
		
		return null;
	}

}
