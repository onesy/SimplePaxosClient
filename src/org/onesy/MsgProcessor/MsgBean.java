package org.onesy.MsgProcessor;

import java.io.Serializable;

import org.onesy.ConfigureProcess.CfgBean;
import org.onesy.ConfigureProcess.CfgCenter;
import org.onesy.NodesManage.NodeDictionary;
import org.onesy.Util.SPSDebugHelper;

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

	/**
	 * host_port_pubchannel_subchannel_db for locate the cfgbean in
	 * nodeDictionary
	 */
	public String sign = null;

	/**
	 * 消息处理的种类，应该送至哪一个指令类进行处理
	 */
	public String msgType = null;

	/**
	 * 是否是源发种类
	 */
	public String isOrigin = "0";

	/**
	 * 消息的内容，外层协议封装到此为止，更多需要封装的信息通过Msg正文进行再次封装
	 */
	public String Msg = null;

	public MsgBean(CfgBean cfgBean, String transactionSerialNo,
			long voteSerialNo, String msgkind, String msg) {
		this.sign = cfgBean.host + "_" + cfgBean.port + "_"
				+ cfgBean.pubchannel + "_" + cfgBean.subchannel + "_"
				+ cfgBean.db;
		this.Msg = msg;
		this.VoteSerialNo = voteSerialNo;
		this.msgType = msgkind;
		this.TransactionSerialNo = Long.parseLong(transactionSerialNo);
	}

	// voteSerialNo\r\r\n\nsign\r\r\n\nmsgKing\r\r\n\nMsg
	public MsgBean(String voteSerialNo, String sign, String msgKind,
			String TransactionSerialNo, String isOrigin, String Msg) {
		this.VoteSerialNo = Long.parseLong(voteSerialNo);
		this.sign = sign;
		this.msgType = msgKind;
		this.Msg = Msg;
		this.isOrigin = isOrigin;
		this.TransactionSerialNo = Long.parseLong(TransactionSerialNo);
	}

	public static CfgBean getCfgBean(String sign) {
		return NodeDictionary.GetCfgBean(sign);
	}

	public static MsgBean getMsgBean(String receivedMsg) {
		// System.err.println(receivedMsg);
		// System.exit(0);
		String[] beansInfo = receivedMsg.split(CfgCenter.SEPERATOR);
		/*
		SPSDebugHelper.Speaker("beansInfo[0]:" + beansInfo[0], 1);
		SPSDebugHelper.Speaker("beansInfo[1]:" + beansInfo[1], 1);
		SPSDebugHelper.Speaker("beansInfo[2]:" + beansInfo[2], 1);
		SPSDebugHelper.Speaker("beansInfo[3]:" + beansInfo[3], 1);
		SPSDebugHelper.Speaker("beansInfo[4]:" + beansInfo[4], 1);
		SPSDebugHelper.Speaker("beansInfo[5]:" + beansInfo[5], 1);
		*/
		
		return new MsgBean(beansInfo[0], beansInfo[1], beansInfo[2],
				beansInfo[3], beansInfo[4], beansInfo[5]);
	}

	// 数据结构
	// voteSerialNo\r\r\n\nsign\r\r\r\rmsgKing\r\r\rTransactionnNo\r\r\risOrigin\r\r\rMsg
	// 127.0.0.1_6379_pub_sub_0
	// orders.add(0,"0\r\r\r127.0.0.1_6379_pub_sub_0\r\r\rConfirmOrder\r\r\r0\r\r\r1\r\r\rmyname\r:\rbinp");
	public static String DecodeToStr(MsgBean msgBean) {
		String msg = new String(msgBean.VoteSerialNo + CfgCenter.SEPERATOR
				+ msgBean.sign + CfgCenter.SEPERATOR + msgBean.msgType + CfgCenter.SEPERATOR + msgBean.TransactionSerialNo + CfgCenter.SEPERATOR + msgBean.isOrigin 
				+ CfgCenter.SEPERATOR + msgBean.Msg);
		return msg;
	}

	/**
	 * 
	 * @param cfgbean
	 * @param msg
	 * @param type
	 *            需要转变的消息类型
	 * @return
	 */
	public static String TransToThisNode(CfgBean cfgbean, MsgBean msg,
			String type) {
		if (type != null) {
			MsgBean thisMsgBean = new MsgBean(cfgbean,
					msg.TransactionSerialNo.toString(), msg.VoteSerialNo, type,
					msg.Msg);
		} else {
			MsgBean thisMsgBean = new MsgBean(cfgbean,
					msg.TransactionSerialNo.toString(), msg.VoteSerialNo,
					msg.msgType, msg.Msg);
		}
		return null;
	}

}
