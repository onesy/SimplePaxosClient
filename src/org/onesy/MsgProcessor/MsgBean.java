package org.onesy.MsgProcessor;

import java.io.Serializable;

import org.onesy.ConfigureProcess.CfgBean;
import org.onesy.ConfigureProcess.CfgCenter;
import org.onesy.NodesManage.NodeDictionary;

public class MsgBean implements Serializable {
	
	/**
	 * auto generate
	 */
	private static final long serialVersionUID = 400827963463881022L;

	/**
	 * 针对冲突解决的序列号，越小，优先级越高
	 */
	public Long VoteSerialNo = 0l;
	/**
	 * 针对处理事件跟踪的序列号，在一个事物被处理的过程中，该序列号不变，但是不同事物之间呈自增.
	 */
	public Long TransactionSerialNo = 0l;
	
	//host_port_pubchannel_subchannel_db
	//for locate the cfgbean in nodeDictionary
	public String sign = null;
	
	//msg kind
	public String msgKind = null;
	
	//msg content
	public String Msg = null;
	
	public MsgBean(CfgBean cfgBean, long voteSerialNo, String msgkind ,String msg){
		this.sign = cfgBean.host + "_" + cfgBean.port + "_" + cfgBean.pubchannel + "_" + cfgBean.subchannel +"_" + cfgBean.db;
		this.Msg = msg;
		this.VoteSerialNo = voteSerialNo + 1;
		this.msgKind = msgkind;
	}
	
	public MsgBean(String voteSerialNo, String sign, String msgKind, String Msg){
		this.VoteSerialNo = Long.parseLong(voteSerialNo);
		this.sign = sign;
		this.Msg = Msg;
	}
	
	public static CfgBean getCfgBean(String sign){
		return NodeDictionary.NodesDictionary.get(sign);
	}
	
	public static MsgBean getMsgBean(String receivedMsg){
		
		String[] beansInfo = receivedMsg.split(CfgCenter.SEPERATOR);
		
		return new MsgBean(beansInfo[0], beansInfo[1],beansInfo[2], beansInfo[3]);
	}
	
}
