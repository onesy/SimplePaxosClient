package org.onesy.Orders;

import org.onesy.ConfigureProcess.CfgBean;
import org.onesy.ConfigureProcess.CfgCenter;
import org.onesy.MsgProcessor.InProcessFrame;
import org.onesy.MsgProcessor.MsgAsile;
import org.onesy.MsgProcessor.MsgBean;
import org.onesy.MsgProcessor.ProcessWindow;
import org.onesy.NodesManage.NodeDictionary;
import org.onesy.Util.RedisOp;

public class SetKVOrder extends OrderBase {
	
	public static final String SetPhase = "\r:\r";

	@Override
	public String ProcessMsg(InProcessFrame frame, MsgBean msgBean) {
		// TODO Auto-generated method stub
		String[] msgSplite = msgBean.Msg.split("\r:\r");
		String key = msgSplite[0];
		String value = msgSplite[1];
		CfgBean cfgbean = NodeDictionary.GetResponser(key);
		// 如果本届点就是存储该数据的节点的话
		if(cfgbean.equals(CfgCenter.selfbean)){
			 // 设置值
			 this.SetKVToStore(key, value, CfgCenter.selfbean);
			 // 更新 frame 
			 CfgBean cfgOpposite = frame.OppositeSide;
			 // 发起确认相应
			 MsgAsile.addSendBean(new MsgBean(CfgCenter.selfbean, CfgCenter.selfbean.voteSeriNo, "ConfirmOrder", msgBean.TransactionSerialNo + ""));
			 // 本次处理已经结束
			 ProcessWindow.FrameFinish(frame);
		}
		return null;
	}
	
	private void SetKVToStore(String key,String value,CfgBean cfgBean){
		RedisOp.SetKV(key, value, cfgBean);
	}

}
