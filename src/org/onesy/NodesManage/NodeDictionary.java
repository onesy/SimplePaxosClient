package org.onesy.NodesManage;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import org.onesy.ConfigureProcess.CfgBean;
import org.onesy.MsgProcessor.MsgBean;

public class NodeDictionary {
	
	public static ConcurrentHashMap<String, CfgBean> NodesDictionary = new ConcurrentHashMap<String, CfgBean>();
	
	public static LinkedList<CfgBean> NodesLinkedList = new LinkedList<CfgBean>();
	
	public static CfgBean getCfgBean(String Key){
		return NodeDictionary.NodesDictionary.get(Key);
	}
	
	public static synchronized void putCfgBean(String sign, CfgBean cfgBean){
		NodeDictionary.NodesDictionary.put(sign, cfgBean);
		NodeDictionary.NodesLinkedList.add(InsertPointFinder(cfgBean), cfgBean);
	}
	/**
	 * 返回裁决者节点的信息
	 * @param msgBean
	 * @return CfgBean
	 */
	public static synchronized CfgBean GetArbiter(MsgBean msgBean){
		return null;
	}
	
	public static synchronized CfgBean GetResponser(String msg){
		return null;
	}
	
	private static int InsertPointFinder(CfgBean cfgBean){
		return 0;
	}

}