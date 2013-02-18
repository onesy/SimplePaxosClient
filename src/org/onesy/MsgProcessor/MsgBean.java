package org.onesy.MsgProcessor;

import java.io.Serializable;

import org.onesy.ConfigureProcess.CfgBean;
import org.onesy.NodesManage.NodeDictionary;

public class MsgBean implements Serializable {
	
	/**
	 * auto generate
	 */
	private static final long serialVersionUID = 400827963463881022L;

	public Long voteSerialNo = 0l;
	
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
		this.voteSerialNo = voteSerialNo + 1;
		this.msgKind = msgkind;
	}
	
	public MsgBean(String voteSerialNo, String sign, String msgKind, String Msg){
		this.voteSerialNo = Long.parseLong(voteSerialNo);
		this.sign = sign;
		this.Msg = Msg;
	}
	
	public static CfgBean getCfgBean(String sign){
		return NodeDictionary.NodesDictionary.get(sign);
	}
	
	public static MsgBean getMsgBean(String receivedMsg){
		
		String[] beansInfo = receivedMsg.split(":");
		
		return new MsgBean(beansInfo[0], beansInfo[1],beansInfo[2], beansInfo[3]);
	}
	
}
