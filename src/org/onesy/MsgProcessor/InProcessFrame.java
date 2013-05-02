package org.onesy.MsgProcessor;

import org.onesy.ConfigureProcess.CfgBean;
import org.onesy.ConfigureProcess.CfgCenter;
/**
 * 处理中的事务的窗口，未完成，差不多完成了
 * @author onesy
 *
 */
public class InProcessFrame {
	/**
	 * 事件栈帧标识符
	 */
	public String FrameSign = null;
	/**
	 * 事件处理流程代号,状态机至少要知道流程是怎样的对吧？
	 * TODO 需要建立数字和流程的映射
	 */
	public int TrasanctionCode = 0;
	/**
	 * 事件处理流程的过程编号，状态机至少要知道流程到哪里了对吧？
	 * TODO 需要建立数字流程的相互映射,因为很可能需要从流程名字找到代号
	 * TODO 难点：如何构造那个该死的流程树
	 */
	public int StepCode = 0;
	/**
	 * 上一次发送给本节点信息的节点，主动新建事件则为空
	 */
	public CfgBean OppositeSide = null;
	/**
	 * 是否在等待对方节点的回应
	 */
	public boolean IsWaiting4 = false;
	/**
	 * 记录时间戳，每当被操作就应当更新
	 */
	public long TimeStamp = 0l;
	/**
	 * 处理补偿时间
	 */
	public long CompensateTime = 0l;
	/**
	 * 存活时间，该存活时间的变量高于全局变量FRAME_LIVE_TIME
	 */
	public long FramePrivateLiveTime = CfgCenter.FRAME_LIVE_TIME;
	/**
	 * 冲突仲裁节点
	 */
	public CfgBean Arbiter = null;
	/**
	 * 本节点是否担任仲裁节点的职责
	 */
	public boolean IsArbiter = false;
	/**
	 * 本次进行的是何种行为，值应该是某一个OrderBase子类的类名
	 */
	public String InProcessType = null;
	/**
	 * 上次进行的是何种行为，新建应该是为空，值应该是某一个OrderBase子类的类名
	 */
	public String FronProcessType = null;
	/**
	 *  之前发送出的消息的Bean,以方便重试
	 */
	public MsgBean FrontMsg = null;
	/**
	 * 这次收到的消息
	 */
	public MsgBean ThisMsgBean = null;
	
	/**
	 * 创建新的InProcessFrame
	 * 通过MsgBean
	 */
	
	private InProcessFrame(MsgBean msgBean){
		this.TimeStamp = System.currentTimeMillis();
		this.FrameSign = ProcessWindow.GetKeyFromMsgBean(msgBean);
		this.OppositeSide = MsgBean.getCfgBean(msgBean.sign);
		this.ThisMsgBean = msgBean;
		this.InProcessType = msgBean.msgType;
	}
	
	public static InProcessFrame getInProcessFrame(MsgBean msgBean){
		InProcessFrame rtnFrame = new InProcessFrame(msgBean);
		return rtnFrame;
	}
}
