package org.onesy.MsgProcessor;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.onesy.ConfigureProcess.CfgCenter;
import org.onesy.InstructionFlows.InstructionFlowTypeMapper;
import org.onesy.Util.CommonAlgorithm;
import org.onesy.Util.SPSDebugHelper;

public class ProcessWindow implements Runnable {
	
	/**
	 * transactionNo和frame的映射
	 */
	public static ConcurrentHashMap<String, InProcessFrame> TransactionPointFrame = new ConcurrentHashMap<String, InProcessFrame>();

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
	public static synchronized InProcessFrame getInProcessFrame(MsgBean msgBean) {
		InProcessFrame rtnFrame = null;
		if (ProcessWindow.InProcessFrameWindowCache
				.containsKey(GetKeyFromMsgBean(msgBean))) {
			SPSDebugHelper.Speaker("可以在正在处理的事务窗口中找到", 3);
			// 可以在正在处理的事务窗口中找到
			rtnFrame = ProcessWindow.InProcessFrameWindowCache
					.get(GetKeyFromMsgBean(msgBean));
			// 刷新，并且增加frame的补偿时间和生存时间
			rtnFrame = FrameTimeFlusher(rtnFrame);
		} else if (ProcessWindow.TimeOutFrameWindowCache
				.containsKey(GetKeyFromMsgBean(msgBean))) {
			SPSDebugHelper.Speaker("虽然已经过期但是处理时间并未耗尽", 3);
			// 虽然已经过期但是处理时间并未耗尽
			// 成功被找到就会刷新生存时间和过期时间并且从过期缓冲区中移除并加入到正在处理的缓冲区中
			rtnFrame = ProcessWindow.TimeOutFrameWindowCache
					.get(GetKeyFromMsgBean(msgBean));
			rtnFrame = FrameTimeFlusher(rtnFrame);
			// 移动栈帧到正在处理的缓冲区中
			FrameMove(rtnFrame, TimeOutFrameWindowCache, InProcessFrameWindowCache);
			
		} else if (DeathFrameWindowCache
				.containsKey(GetKeyFromMsgBean(msgBean))) {
			SPSDebugHelper.Speaker("如果事务已经死亡，就直接可以返回null", 3);
			//如果事务已经死亡，就直接可以返回null
		} else {
			// 创建新的ProcessFrame 并且将其存入正在处理事务窗口，和事务号映射窗口
			rtnFrame = InProcessFrame.getInProcessFrame(msgBean);
			// 刷新时间
			rtnFrame = FrameTimeFlusher(rtnFrame);
			// 放入正在处理事务窗口
			ProcessWindow.InProcessFrameWindowCache.put(GetKeyFromFrame(rtnFrame), rtnFrame);
			// 放入事务号-frame映射窗口
			ProcessWindow.TransactionPointFrame.put(CfgCenter.TransanctionNo.toString(), rtnFrame);
		}
		return rtnFrame;
	}
	

	public static String GetKeyFromMsgBean(MsgBean msgBean) {
		String rtn = msgBean.sign + "_" + msgBean.TransactionSerialNo;
		return rtn;
	}
	public static String GetKeyFromFrame(InProcessFrame frame){
		return frame.FrameSign;
	}
	
	/**
	 * 通过msgbean去刷新对应的frame刷新frame生命时间
	 * @param frame
	 * @param msgBean
	 */
	public static synchronized void FlushFrameInfo(InProcessFrame frame, MsgBean msgBean){
		frame.FrontMsg = frame.ThisMsgBean;
		frame.ThisMsgBean = msgBean;
		frame.FronProcessType = msgBean.msgType;
		frame.TrasanctionCode = InstructionFlowTypeMapper.getCodeByInstructionName(msgBean.msgType + "Order");
		FrameTimeFlusher(frame);
	}
	
	/**
	 * 检查栈帧是否已经被处理过
	 * @param frame
	 * @return
	 */
	public static synchronized boolean IsFrameProcessed(InProcessFrame frame){
		return !ProcessedFrameWindowCache.containsKey(GetKeyFromFrame(frame));
	}
	
	/**
	 * 结束时候检查，同时结束frame的生命
	 * @param frame
	 * @return
	 */
	public static synchronized boolean FrameFinish(InProcessFrame frame){
		boolean  frameCheckflg = true;
		if(InProcessFrameWindowCache.containsKey(GetKeyFromFrame(frame))){
			FrameMove(frame, InProcessFrameWindowCache, ProcessedFrameWindowCache);
		}else if(TimeOutFrameWindowCache.containsKey(GetKeyFromFrame(frame))) {
			FrameMove(frame, TimeOutFrameWindowCache, ProcessedFrameWindowCache);
		}else if (DeathFrameWindowCache.containsKey(GetKeyFromFrame(frame))) {
			FrameMove(frame, DeathFrameWindowCache, ProcessedFrameWindowCache);
		}else if(ProcessedFrameWindowCache.containsKey(GetKeyFromFrame(frame))){
			//说明信息出现了重复
			frameCheckflg = false;
		}
		SPSDebugHelper.Speaker("正在从TransactionPointFrame中删除key = " + GetKeyFromFrame(frame), 1);
		TransactionPointFrame.remove(GetKeyFromFrame(frame));
		return frameCheckflg;
	}
	
	/**
	 * 刷新frame的时间
	 * @param frame
	 * @return
	 */
	public static InProcessFrame FrameTimeFlusher(InProcessFrame frame){
			//过期栈帧刷新补偿时间和生存时间
			frame.CompensateTime = System.currentTimeMillis() - frame.TimeStamp + CfgCenter.FRAME_COMPENSATE_TIME;
			frame.FramePrivateLiveTime = System.currentTimeMillis() - frame.TimeStamp + CfgCenter.FRAME_LIVE_TIME;
		return frame;
	}

	/**
	 * 从InProcessFrameWindowCache中清除已经过期的帧，将其放入TimeOutFrameWindowCache
	 * 从TimeOutFrameWindowCache中清除LIVE_TIME已经超过的
	 * 注意！这个函数暂时不会清理在TransactionPointFrame中的过期帧
	 */
	@SuppressWarnings("rawtypes")
	public static synchronized void ClearTimeOutFrame() {
		long InProcExpiredTime = CfgCenter.FRAME_EXPIRE_TIME;
		long FrameLiveTime = CfgCenter.FRAME_LIVE_TIME;
		Iterator iter = null ;
		// 清理InProcessFrameWindowCache
		iter = CommonAlgorithm.HashMapToIterator(InProcessFrameWindowCache);
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			String key = (String)entry.getKey();
			if(InProcessFrameWindowCache.containsKey(key)){
				InProcessFrame tmp = InProcessFrameWindowCache.get(key);
				if(IsFrameExpired(tmp, InProcExpiredTime)){
					FrameMove(tmp, InProcessFrameWindowCache, TimeOutFrameWindowCache);
				}
			}
		}
		// 清理TimeOutFrameWindowCache中已经耗尽了生存时间的帧
		iter = CommonAlgorithm.HashMapToIterator(TimeOutFrameWindowCache);
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry)iter.next();
			String key = (String)entry.getKey();
			if(TimeOutFrameWindowCache.containsKey(key)){
				InProcessFrame tmp = TimeOutFrameWindowCache.get(key);
				if(IsFrameExpired(tmp, FrameLiveTime)){
					FrameMove(tmp, TimeOutFrameWindowCache, DeathFrameWindowCache);
				}
			}
		}
	}
	
	/**
	 * 从srcMap中将指定的frame移除，并且放入到targMap
	 * @param frame
	 * @param srcMap
	 * @param targMap
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void FrameMove(InProcessFrame frame, Map srcMap, Map targMap){
		targMap.put(frame.FrameSign, frame);
		srcMap.remove(frame.FrameSign);
	}
	
	/**
	 * 判断帧是否在指定的时间差内过期
	 * @param frame
	 * @param TimeDiff
	 * @return
	 */
	public static boolean IsFrameExpired(InProcessFrame frame, long TimeDiff){
		if(System.currentTimeMillis() - (frame.TimeStamp + frame.CompensateTime) > TimeDiff)
			return true;
		return false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(;;){
			ClearTimeOutFrame();
			
			try {
				Thread.sleep(CfgCenter.FRAME_EXPIRE_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
