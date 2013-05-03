package org.onesy.NodesManage;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.onesy.ConfigureProcess.CfgBean;
import org.onesy.ConfigureProcess.CfgCenter;
import org.onesy.Util.CommonAlgorithm;

public class NodeDictionary {

	public static HashMap<String, CfgBean> NodesDictionary = new HashMap<String, CfgBean>();

	public static LinkedList<CfgBean> NodesLinkedList = new LinkedList<CfgBean>();

	public static void LoadNodes() {
			PutCfgBean(CfgCenter.cfgBeansList);
	}

	public static CfgBean GetCfgBean(String Key) {
		return NodeDictionary.NodesDictionary.get(Key);
	}

	private static synchronized void PutCfgBean(
			ArrayList<CfgBean> cfgbeanlist) {
		for(CfgBean cfgbean : cfgbeanlist){
			NodeDictionary.NodesDictionary.put(cfgbean.sign, cfgbean);
		}
		// NodeDictionary.NodesLinkedList.add(PositionFinder(cfgBean.sign),
		// cfgBean);
		cfgSort(cfgbeanlist);

	}

	private static synchronized CfgBean GetCfgFromList(int positoin) {
		if (positoin == 0) {
			return NodesLinkedList.get(0);
		}
		return NodesLinkedList.get(positoin - 1);
	}

	/**
	 * 返回裁决者节点的信息
	 * 
	 * @param msgBean
	 * @return CfgBean
	 */
	public static synchronized CfgBean GetRandomArbiter() {
		int position = (int) Math.floor(Math.random() * NodesLinkedList.size()
				* 8)
				% NodesLinkedList.size();
		return GetCfgFromList(position);
	}

	/**
	 * 返回响应者信息 选择响应者和消息内容有关和消息bean无关,仅仅在被请求保存或者读取的事后可以起到作用，其他情况下，尤其是已经开始的事务，
	 * 不能使用该方法否则会出错
	 * 
	 * @param msgkey
	 * @return
	 */
	public static synchronized CfgBean GetResponser(String msgkey) {
//		System.err.println("第一个Nodelinked：" + new BigInteger(CommonAlgorithm.Md5Al(NodesLinkedList.get(0).sign))
//		.abs());
		for (CfgBean nodeInfoBean : NodesLinkedList) {
			if (new BigInteger(CommonAlgorithm.Md5Al(nodeInfoBean.sign))
					.abs()
					.compareTo(
							new BigInteger(CommonAlgorithm.Md5Al(msgkey)).abs()) == 1) {
				// 找到节点
				System.err.println("选择的是：" + nodeInfoBean.sign + "=>" + msgkey + "=>" + new BigInteger(CommonAlgorithm.Md5Al(msgkey)).abs());
				return nodeInfoBean;
			}
		}
		return NodesLinkedList.get(0);
	}

	/**
	 * 通过计算MD5找出值所归属的节点在链表中的位置
	 * 
	 * @param value
	 * @return
	 */
	@Deprecated
	private static int PositionFinder(String value) {
		int count = 0;
		if (NodesLinkedList.size() == 1) {
			return count;
		} else {
			for (CfgBean nodeInfoBean : NodesLinkedList) {

				if (new BigInteger(CommonAlgorithm.Md5Al(nodeInfoBean.sign))
						.abs().compareTo(
								new BigInteger(CommonAlgorithm.Md5Al(value))
										.abs()) > 1) {
					// 找到插入点
					return count;
				}
				count++;
			}
		}
		return count;
	}

	private static void cfgSort(ArrayList<CfgBean> cfgbeanlist) {
		ArrayList<BigInteger> albi = new ArrayList<BigInteger>();
		HashMap<BigInteger, CfgBean> hMap = new HashMap<BigInteger, CfgBean>();
		LinkedList<CfgBean> tmp = new LinkedList<CfgBean>();

		for (CfgBean cfgBean : cfgbeanlist) {
			albi.add(new BigInteger(CommonAlgorithm.Md5Al(cfgBean.sign)).abs());
			hMap.put(new BigInteger(CommonAlgorithm.Md5Al(cfgBean.sign)).abs(),
					cfgBean);
		}
		for (;;) {
			BigInteger bigInteger = new BigInteger("0");
			for (int i = 0; i < albi.size(); i++) {
				if (albi.get(i).compareTo(bigInteger) == 1) {
					bigInteger = albi.get(i);
				}
			}
			tmp.add(hMap.get(bigInteger));
			albi.remove(bigInteger);
			//System.out.println(bigInteger);
			if (albi.size() == 0) {
				break;
			}
		}
		for (int i = 0 ; i < tmp.size(); i ++) {
			NodesLinkedList.add(tmp.get(tmp.size() - i - 1));
		}
		for(CfgBean cfgb : NodesLinkedList){
			System.out.println(cfgb.sign + "====>" + new BigInteger(CommonAlgorithm.Md5Al(cfgb.sign)).abs());
		}
	}

}