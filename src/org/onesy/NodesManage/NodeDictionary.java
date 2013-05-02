package org.onesy.NodesManage;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;

import org.onesy.ConfigureProcess.CfgBean;
import org.onesy.ConfigureProcess.CfgCenter;
import org.onesy.Util.CommonAlgorithm;

public class NodeDictionary {

	public static HashMap<String, CfgBean> NodesDictionary = new HashMap<String, CfgBean>();

	public static LinkedList<CfgBean> NodesLinkedList = new LinkedList<CfgBean>();

	public static void LoadNodes(){
		for(CfgBean cb : CfgCenter.cfgBeansList){
			PutCfgBean(cb.sign, cb);
		}
	}
	
	public static CfgBean GetCfgBean(String Key) {
		return NodeDictionary.NodesDictionary.get(Key);
	}

	public static synchronized void PutCfgBean(String sign, CfgBean cfgBean) {
		NodeDictionary.NodesDictionary.put(sign, cfgBean);
		NodeDictionary.NodesLinkedList.add(PositionFinder(cfgBean.sign),
				cfgBean);
	}
	
	private static synchronized CfgBean GetCfgFromList(int positoin){
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
		int position =(int) Math.floor(Math.random() * NodesLinkedList.size() * 8)% NodesLinkedList.size();
		return GetCfgFromList(position);
	}

	/**
	 * 返回响应者信息
	 * 选择响应者和消息内容有关和消息bean无关,仅仅在被请求保存或者读取的事后可以起到作用，其他情况下，尤其是已经开始的事务，不能使用该方法否则会出错
	 * @param msgkey
	 * @return
	 */
	public static synchronized CfgBean GetResponser(String msgkey) {
		return GetCfgFromList(PositionFinder(msgkey));
	}

	/**
	 * 通过计算MD5找出值所归属的节点在链表中的位置
	 * @param value
	 * @return
	 */
	private static int PositionFinder(String value) {
		int count = 0;
		if (NodesLinkedList.size() == 1) {
			return count;
		} else {
			for (CfgBean nodeInfoBean : NodesLinkedList) {
				count ++;
				if (new BigInteger(CommonAlgorithm.Md5Al(nodeInfoBean.sign)).abs()
						.compareTo(new BigInteger(CommonAlgorithm.Md5Al(value)).abs()) > 1) {
					//找到插入点
					return count;
				}
				
			}
		}
		return 0;
	}
	

}