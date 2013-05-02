package org.onesy.Orders;

import java.util.HashMap;
import org.onesy.Beans.ClientBeans;
import org.onesy.ConfigureProcess.CfgBean;
import org.onesy.ConfigureProcess.CfgCenter;
import org.onesy.MsgProcessor.InProcessFrame;
import org.onesy.MsgProcessor.MsgAsile;
import org.onesy.MsgProcessor.MsgBean;
import org.onesy.MsgProcessor.ProcessWindow;
import org.onesy.NodesManage.NodeDictionary;
import org.onesy.Util.RedisOp;

public class GetVOrder extends OrderBase {
	
	@Override
	public String ProcessMsg(InProcessFrame frame, MsgBean msgBean) {
		// TODO Auto-generated method stub
		// 分割出信息
		HashMap<String, String> MsgInfo = this.getMsgInfo(msgBean.Msg);
		// 查看key是否是这个节点的
		if (CfgCenter.selfbean.sign.equals(NodeDictionary.GetResponser(MsgInfo
				.get("key")).sign) || SINGLEDEBUG) {
			System.err.println("本节点处理数据");
			String value = null;
			String content = null;
			String clientHost = null;
			int clientListenPort = 0;
			int clientSendPort = 0;
			// 获取key的值以及客户端内容
			value = this.GetVToTrans(MsgInfo.get("key"), CfgCenter.selfbean);
			clientHost = MsgInfo.get("clientHost");
			clientListenPort = Integer
					.parseInt(MsgInfo.get("clientListenPort"));
			clientSendPort = Integer.parseInt(MsgInfo.get("clientSendPort"));
			content = "key" + CfgCenter.EQUALSEPERATOR + MsgInfo.get("key")
					+ CfgCenter.PAIRSEPERATOR + "value"
					+ CfgCenter.EQUALSEPERATOR + value
					+ CfgCenter.PAIRSEPERATOR + "transactionNo"
					+ CfgCenter.EQUALSEPERATOR + MsgInfo.get("transactionNo");
			// 根据消息的来源构造回发的内容
			MsgBean rtnMsgBean = new MsgBean(
					CfgCenter.selfbean.voteSeriNo + "",
					CfgCenter.selfbean.sign, "GETVRTN",
					CfgCenter.TransanctionNo.toString(), "0", content);
			rtnMsgBean.isRtn = true;
			rtnMsgBean.clientInfo = new ClientBeans(MsgInfo.get("Client_Info"));
			// 插入到缓冲区中
			MsgAsile.addSendBean(rtnMsgBean);
			// 处理完成，清除缓冲区中的这个frame
			ProcessWindow.FrameFinish(frame);
		} else {
			System.err.println("非本节点处理数据");
			// 根据查找道的节点信息进行转发转发最好直接使用msgbean进行构造
			CfgBean transBean = NodeDictionary.GetResponser(MsgInfo.get("key"));
			synchronized (CfgCenter.TransanctionNo) {

				MsgAsile.addSendBean(super.ChangeToThisNode(msgBean));
			}
			// 通过窗口的方法消除frame
			ProcessWindow.FrameFinish(frame);
		}
		return null;
	}

	private String GetVToTrans(String key, CfgBean cfgBean) {
		return RedisOp.getV(key, cfgBean);
	}

	@Override
	public String GetMsgKey(MsgBean msgBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String GetMsgVal(MsgBean msgBean) {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap<String, String> getMsgInfo(String msg) {
		HashMap<String, String> MsgInfo = new HashMap<String, String>();
		String[] pairs = msg.split(CfgCenter.PAIRSEPERATOR);
		for (String pair : pairs) {
			String key = new String(pair.split(CfgCenter.EQUALSEPERATOR)[0]);
			String value = new String(pair.split(CfgCenter.EQUALSEPERATOR)[1]);
			MsgInfo.put(key,value);
		}

		String[] Client_Info = MsgInfo.get("Client_Info").split("_");

		MsgInfo.put("clientHost", Client_Info[0]);
		MsgInfo.put("clientListenPort", Client_Info[1]);
		MsgInfo.put("clientSendPort", Client_Info[2]);

		return MsgInfo;
	}

}
