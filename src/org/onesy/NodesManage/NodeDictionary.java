package org.onesy.NodesManage;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;

import org.onesy.ConfigureProcess.CfgBean;
import org.onesy.MsgProcessor.MsgBean;
import org.onesy.Util.CommonAlgorithm;

public class NodeDictionary {

	public static HashMap<String, CfgBean> NodesDictionary = new HashMap<String, CfgBean>();

	public static LinkedList<CfgBean> NodesLinkedList = new LinkedList<CfgBean>();

	public static CfgBean GetCfgBean(String Key) {
		return NodeDictionary.NodesDictionary.get(Key);
	}

	public static synchronized void PutCfgBean(String sign, CfgBean cfgBean) {
		NodeDictionary.NodesDictionary.put(sign, cfgBean);
		NodeDictionary.NodesLinkedList.add(PositionFinder(cfgBean.sign),
				cfgBean);
	}

	/**
	 * 返回裁决者节点的信息
	 * 
	 * @param msgBean
	 * @return CfgBean
	 */
	public static synchronized CfgBean GetArbiter(MsgBean msgBean) {
		return null;
	}

	public static synchronized CfgBean GetResponser(String msg) {
		return null;
	}

	private static int PositionFinder(String value) {
		int count = 0;
		if (NodesLinkedList.size() < 1) {
			return count;
		} else {
			for (CfgBean nodeInfoBean : NodesLinkedList) {
				if (new BigInteger(CommonAlgorithm.Md5Al(nodeInfoBean.sign))
						.compareTo(new BigInteger(CommonAlgorithm.Md5Al(value))) == 1) {
					//找到插入点
					return ++count;
				}
				count ++;
			}
		}
		return count;
	}

}