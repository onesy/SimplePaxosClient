package org.onesy.MsgProcessor;

import java.util.concurrent.ConcurrentHashMap;

public class ProcessWindow {

	public static ConcurrentHashMap<String, InProcessFrame> InProcessFrameWindowCache = new ConcurrentHashMap<String, InProcessFrame>();

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
			rtnFrame = ProcessWindow.InProcessFrameWindowCache.get(GetKeyFromMsgBean(msgBean));
			return rtnFrame;
		}else{
			//创建新的ProcessFrame并返回
			
		}
		return rtnFrame;
	}
	
	public static String GetKeyFromMsgBean(MsgBean msgBean){
		String rtn = msgBean.sign + "_" + msgBean.TransactionSerialNo;
		return rtn;
	}

}
