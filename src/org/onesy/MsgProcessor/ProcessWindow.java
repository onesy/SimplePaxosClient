package org.onesy.MsgProcessor;

import java.util.concurrent.ConcurrentHashMap;

import org.onesy.ConfigureProcess.CfgCenter;

public class ProcessWindow {

	/**
	 * 正在处理中的事务窗口
	 */
	public static ConcurrentHashMap<String, InProcessFrame> InProcessFrameWindowCache = new ConcurrentHashMap<String, InProcessFrame>();
	/**
	 * 过期事务窗口
	 */
	public static ConcurrentHashMap<String, InProcessFrame> TimeOutFrameWindowCache = new ConcurrentHashMap<String, InProcessFrame>();
	/**
	 * 已经处理过的事务的窗口
	 */
	public static ConcurrentHashMap<String, InProcessFrame> ProcessedFrameWindowCache = new ConcurrentHashMap<String, InProcessFrame>();
	/**
	 * 已经“死亡”的事务的窗口
	 */
	public static ConcurrentHashMap<String, InProcessFrame> DeathFrameWindowCache = new ConcurrentHashMap<String, InProcessFrame>();

	/**
	 * 通过MsgBean来取得栈帧，如果不存在，就创建一个新的栈帧，并返回，如果存在，就直接返回
	 * 
	 * @param msgBean
	 * @return
	 */
	public static InProcessFrame getInProcessFrame(MsgBean msgBean) {
		InProcessFrame rtnFrame = null;
		if(ProcessWindow.InProcessFrameWindowCache
				.containsKey(GetKeyFromMsgBean(msgBean))){
			//可以在正在处理的事务窗口中找到
			rtnFrame = ProcessWindow.InProcessFrameWindowCache.get(GetKeyFromMsgBean(msgBean));
			//成功处理一次即可获得500ms的补偿时间
			rtnFrame.CompensateTime += CfgCenter.FRAME_COMPENSATE_TIME;
		}else if(ProcessWindow.TimeOutFrameWindowCache.containsKey(GetKeyFromMsgBean(msgBean))){
			//虽然已经过期但是处理时间并未消亡
			//成功被找到就会立即获得2倍补偿时间并且从
			TODO
		}else
		{
			//创建新的ProcessFrame并返回
			rtnFrame =  getInProcessFrame(msgBean);
		}
		return rtnFrame;
	}
	
	public static String GetKeyFromMsgBean(MsgBean msgBean){
		String rtn = msgBean.sign + "_" + msgBean.TransactionSerialNo;
		return rtn;
	}
	/**
	 * 从InProcessFrameWindowCache中清除已经过期的帧，将其放入TimeOutFrameWindowCache
	 * 从TimeOutFrameWindowCache中清除LIVE_TIME已经超过的
	 */
	public static synchronized void ClearTimeOutFrame(){
		
	}

}
