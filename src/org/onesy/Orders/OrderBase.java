package org.onesy.Orders;

import org.onesy.ConfigureProcess.CfgCenter;
import org.onesy.MsgProcessor.InProcessFrame;
import org.onesy.MsgProcessor.MsgBean;

public abstract class OrderBase {
	/**
	 * 每个orderBase的子类必须在调用ProcessMsg的时候更改
	 */
	public String msgkind = null;
	public static final String ORDER_SUCCESS = "ok";
	public static final String SetPhase = "\r:\r";

	public abstract String ProcessMsg(InProcessFrame frame,MsgBean msgBean);
	
	public static MsgBean ChangeToThisNode(MsgBean msgBean){
		msgBean.isOrigin = "0";
		
		msgBean.sign = CfgCenter.selfbean.sign;
		
		msgBean.VoteSerialNo = CfgCenter.selfbean.voteSeriNo;
		
		msgBean.TransactionSerialNo = CfgCenter.TransanctionNo;
		
		++CfgCenter.TransanctionNo;
		return msgBean;
	}
} 
