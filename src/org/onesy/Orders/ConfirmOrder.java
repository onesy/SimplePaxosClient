package org.onesy.Orders;

public class ConfirmOrder extends OrderBase {

	@Override
	public String ProcessMsg(String Msg) {
		/*
		 * 确认信息收到的消息
		 */
		// TODO 形成状态机的条件
		/*
		 * 将处理流程和线程分离，并且放入缓冲区中，这个缓冲区必须能够快速找到未完成的流程
		 */
		
		return null;
	}

}
