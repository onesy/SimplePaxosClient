package org.onesy.Orders;

import org.onesy.ConfigureProcess.CfgBean;
import org.onesy.ConfigureProcess.CfgCenter;
import org.onesy.MsgProcessor.InProcessFrame;
import org.onesy.MsgProcessor.MsgAsile;
import org.onesy.MsgProcessor.MsgBean;
import org.onesy.MsgProcessor.ProcessWindow;
import org.onesy.NodesManage.NodeDictionary;
import org.onesy.Util.RedisOp;
import org.onesy.Util.SPSDebugHelper;

public class SetKVOrder extends OrderBase {

	@Override
	public String ProcessMsg(InProcessFrame frame, MsgBean msgBean) {
		// TODO Auto-generated method stub
		String[] msgSplite = msgBean.Msg.split(OrderBase.SetPhase);
		String key = msgSplite[0];
		String value = msgSplite[1];
		CfgBean cfgbean = NodeDictionary.GetResponser(key);
		// 如果本届点就是存储该数据的节点的话
		if (cfgbean.sign.equals(CfgCenter.selfbean.sign)) {
			// 设置值
			this.SetKVToStore(key, value, CfgCenter.selfbean);
			// 更新 frame
			CfgBean cfgOpposite = frame.OppositeSide;
			// 发起确认相应
			MsgAsile.addSendBean(new MsgBean(CfgCenter.selfbean,
					msgBean.TransactionSerialNo.toString(),
					CfgCenter.selfbean.voteSeriNo, "ConfirmOrder",
					msgBean.TransactionSerialNo + OrderBase.SetPhase + OrderBase.ORDER_SUCCESS));
			// 本次处理已经结束
			ProcessWindow.FrameFinish(frame);
		} else {
			SPSDebugHelper.Speaker("应该选取其他节点", 1);
		}
		return null;
	}

	private void SetKVToStore(String key, String value, CfgBean cfgBean) {
		RedisOp.SetKV(key, value, cfgBean);
	}

}
