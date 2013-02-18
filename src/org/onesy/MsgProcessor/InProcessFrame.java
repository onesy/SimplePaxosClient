package org.onesy.MsgProcessor;

import org.onesy.ConfigureProcess.CfgBean;

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
	
}
