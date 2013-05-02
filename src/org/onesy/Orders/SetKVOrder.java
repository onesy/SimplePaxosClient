package org.onesy.Orders;

import org.onesy.Beans.KVContainer;
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
		KVContainer kvContainer = ConfirmOrder.GetKeyFromMsg(msgBean.Msg);
		CfgBean cfgbean = NodeDictionary.GetResponser((String) kvContainer
				.getKey());
		// 如果本届点就是存储该数据的节点的话
		System.err.println("target sign：" + cfgbean.sign + "---------->now get:" + CfgCenter.selfbean.sign);
//		System.exit(0);
		if (cfgbean.sign.equals(CfgCenter.selfbean.sign)
				|| SINGLEDEBUG) {
			// 设置值
			this.SetKVToStore((String) kvContainer.getKey(),
					(String) kvContainer.getValue(), CfgCenter.selfbean);
			// 更新 frame
			// CfgBean cfgOpposite = frame.OppositeSide; //
			// 发起确认相应
			if (msgBean.isOrigin.equals("0")) {//判断是否是需要像client回复的msg
				/*
				 * TODO 客户端也必须将客户端自己的transactionNo和处理事务关联起来，否则无法确认
				 */
				MsgAsile.addSendBean(new MsgBean(CfgCenter.selfbean,
						msgBean.TransactionSerialNo.toString(),
						CfgCenter.selfbean.voteSeriNo, "Confirm",
						msgBean.Msg));
			}
			// 本次处理已经结束
			ProcessWindow.FrameFinish(frame);
		} else {
			SPSDebugHelper.Speaker("非本节点处理该数据，该请求将被转发", 1);
			/*
			 * 规划 1,sign被替换 2,transactionNo被替换为本届点的TransactioinNo 3,放入发送队列
			 * 
			 * TODO 要想建立transactionNo和frame之间的关系，还需要对processwindow进行更改，
			 * 必须存在一个缓冲区从transactionNo映射到frame 为了达到这个目的缓冲区的删除等逻辑需要更改
			 */
			synchronized (CfgCenter.TransanctionNo) {

				MsgAsile.addSendBean(super.ChangeToThisNode(msgBean));
			}

		}
		return null;
	}

	private void SetKVToStore(String key, String value, CfgBean cfgBean) {
		RedisOp.SetKV(key, value, cfgBean);
	}

	

	@Override
	public String GetMsgKey(MsgBean msgBean) {
		// TODO Auto-generated method stub
		KVContainer kvEntry = ConfirmOrder.GetKeyFromMsg(msgBean.Msg);
		return (String) kvEntry.getKey();
	}

	@Override
	public String GetMsgVal(MsgBean msgBean) {
		// TODO Auto-generated method stub
		KVContainer kvEntry = ConfirmOrder.GetKeyFromMsg(msgBean.Msg);
		return (String)kvEntry.getValue();
	}

}
